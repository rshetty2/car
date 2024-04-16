package com.gic.car.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@ToString
public class Field implements Serializable {
    @Getter
    @Setter
    private int width;

    @Getter
    @Setter
    private int height;

    @Getter
    private HashMap<String, Car> carNameToCarMap;

    @Getter
    private HashMap<Position, Set<String>> positionToCarsMap;

    @Getter
    @Setter
    private Map<Position, Set<String>> filteredMapOfCollisions;

    private int maxCommandLength = 0;

    public void setFieldDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Field(int width, int height) {
        this.width = width;
        this.height = height;

        carNameToCarMap = new HashMap<>();
        positionToCarsMap = new HashMap<>();
    }

    public void reset() {
        width = 0;
        height = 0;
        carNameToCarMap = new HashMap<>();
        positionToCarsMap = new HashMap<>();
    }

    public void addCarToCircuit(Car car) {
        carNameToCarMap.put(car.getName(), car);
        maxCommandLength = Math.max(maxCommandLength, car.getCommands().length());
        if (positionToCarsMap.containsKey(car.getCurrentPositionOnField())) {
            positionToCarsMap.get(car.getCurrentPositionOnField()).add(car.getName());
        } else {
            Set<String> initialSet = new HashSet<>();
            initialSet.add(car.getName());
            positionToCarsMap.put(car.getCurrentPositionOnField(), initialSet);
        }
    }

    public void simulate() {
        //check the positionToCarsMap to assess if more than two cars are at any position
        //indicating collision and terminate
        positionToCarsMap = new HashMap<>();
        for (int commandIndex = 0; commandIndex < maxCommandLength; commandIndex++) {
            for (Car car : carNameToCarMap.values()) {
            Position nextPosition = car.processNextCommand(getWidth(), getHeight());
            if (positionToCarsMap.containsKey(nextPosition)) {
                Set<String> carList = positionToCarsMap.get(nextPosition);
            if (!carList.contains(car.getName())) {
                    carList.add(car.getName());
                    positionToCarsMap.put(nextPosition, carList);
                }
                if (positionToCarsMap.get(nextPosition).size() > 1)
                    for(String carsInThePosition:carList) carNameToCarMap.get(carsInThePosition).setIsInCollision();
            } else {
                Set<String> initialSet = new HashSet<>();
                initialSet.add(car.getName());
                positionToCarsMap.put(nextPosition, initialSet);
            }
            }
        }


        setFilteredMapOfCollisions(positionToCarsMap.entrySet().stream().filter(x -> x.getValue().size() > 1).
                collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue())));
    }
}



