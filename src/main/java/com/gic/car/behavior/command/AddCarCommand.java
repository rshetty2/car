package com.gic.car.behavior.command;

import com.gic.car.behavior.proxy.FieldManager;
import com.gic.car.domain.Car;
import com.gic.car.domain.CommandParams;
import com.gic.car.domain.Field;
import com.gic.car.domain.Position;



public class AddCarCommand implements Command {
    private FieldManager fieldManager;


    public void setFieldManager(FieldManager fieldManager) {
        this.fieldManager = fieldManager;
    }

    @Override
    public Field execute(CommandParams commandParams) {
        Car car = Car.builder().name(commandParams.getCarName()).
                currentPositionOnField(new Position(commandParams.getCarPositionX(), commandParams.getCarPositionY())).
                originalPositionOnField(new Position(commandParams.getCarPositionX(), commandParams.getCarPositionY())).
                originalDirection(commandParams.getCarDirection()).
                currentDirection(commandParams.getCarDirection()).commands(commandParams.getCarCommands()).currentCommandIndex(0).build();

        fieldManager.addCarToField(car);
        return fieldManager.getField();
    }

}
