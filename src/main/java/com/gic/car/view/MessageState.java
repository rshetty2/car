package com.gic.car.view;

import com.gic.car.domain.Car;
import com.gic.car.domain.CommandParams;
import com.gic.car.domain.Field;
import com.gic.car.domain.Position;

import java.util.*;

import static com.gic.car.view.MessageConstants.*;

public enum MessageState {

    WELCOME {

        @Override
        public List<String> getDisplayMessagePriorUserInput(Optional<CommandParams> dynamicValues) {
            return Arrays.asList(MessageConstants.WELCOME);
        }

    },
    FIELD_CREATION {
        @Override
        public List<String> getDisplayMessagePriorUserInput(Optional<CommandParams> dynamicValues) {
            return Arrays.asList(MessageConstants.POST_FIELD_CREATION_MSG);
        }

    },

    INITIAL_CAR_SIMULATION_SELECTION {
        @Override
        public List<String> getDisplayMessagePriorUserInput(Optional<CommandParams> dynamicValues) {
            Optional<CommandParams> commandParams = Optional.ofNullable(dynamicValues).orElseGet(() -> Optional.of(CommandParams.builder().build()));
            Field field = commandParams.get().getField();
            return Arrays.asList(String.format(MessageConstants.POST_FIELD_CREATION_MSG, field.getWidth() ,field.getHeight()));
        }
    },

    CAR_SELECTION {
    @Override
    public List<String> getDisplayMessagePriorUserInput(Optional<CommandParams> dynamicValues) {
        return Collections.emptyList();
    }

    },

    CAR_NAME_CREATION {
        @Override
        public List<String> getDisplayMessagePriorUserInput(Optional<CommandParams> dynamicValues) {
            return Arrays.asList(CAR_NAME_MSG);
        }
    },


    RUN_SIMULATION {
        @Override
        public List<String> getDisplayMessagePriorUserInput(Optional<CommandParams> dynamicValues) {
            Optional<CommandParams> commandParams = Optional.ofNullable(dynamicValues).orElseGet(() -> Optional.of(CommandParams.builder().build()));
            Field field = commandParams.get().getField();
            return Arrays.asList(LIST_CAR_MSG_HEADER, getListOfCars(field), LIST_SIMULATION_MSG_HEADER, getSimulationsNoCollision(field), getSimulationsCollision(field), LIST_SIMULATION_FOOTER);
        }
    },

    CAR_POSITION_CREATION {
        @Override
        public List<String> getDisplayMessagePriorUserInput(Optional<CommandParams> dynamicValues) {
            Optional<CommandParams> commandParams = Optional.ofNullable(dynamicValues).orElseGet(() -> Optional.of(CommandParams.builder().build()));
            String carName = commandParams.get().getCarName();
            return Arrays.asList(String.format(MessageConstants.CAR_POSITION_MSG,carName));
        }
    },
    CAR_COMMAND_CREATION {
        @Override
        public List<String> getDisplayMessagePriorUserInput(Optional<CommandParams> dynamicValues) {
            Optional<CommandParams> commandParams = Optional.ofNullable(dynamicValues).orElseGet(() -> Optional.of(CommandParams.builder().build()));
            String carName = commandParams.get().getCarName();
            return Arrays.asList(String.format(CAR_COMMAND_MSG,carName));
        }
    },

    CAR_SIMULATION_SELECTION {
        @Override
        public List<String> getDisplayMessagePriorUserInput(Optional<CommandParams> dynamicValues) {
            Optional<CommandParams> commandParams = Optional.ofNullable(dynamicValues).orElseGet(() -> Optional.of(CommandParams.builder().build()));
            Field field = commandParams.get().getField();
            String carList = getListOfCars(field);
            return Arrays.asList(LIST_CAR_MSG_HEADER, carList, LIST_CAR_FOOTER);
        }
    },



    LIST_CAR {
        @Override
        public List<String> getDisplayMessagePriorUserInput(Optional<CommandParams> dynamicValues) {
            Optional<CommandParams> commandParams = Optional.ofNullable(dynamicValues).orElseGet(() -> Optional.of(CommandParams.builder().build()));
            Field field = commandParams.get().getField();
            String carList = getListOfCars(field);
            return Arrays.asList(LIST_CAR_MSG_HEADER, carList, LIST_CAR_FOOTER);
        }
    },


    STARTOVER_EXIT_SELECTION {
        @Override
        public List<String> getDisplayMessagePriorUserInput(Optional<CommandParams> dynamicValues) {
            Optional<CommandParams> commandParams = Optional.ofNullable(dynamicValues).orElseGet(() -> Optional.of(CommandParams.builder().build()));
            Field field = commandParams.get().getField();
            String carList = getListOfCars(field);
            String simulationList = getSimulationsNoCollision(field);
            String simulationCollisionList = getSimulationsCollision(field);
            return Arrays.asList(LIST_CAR_MSG_HEADER, carList, LIST_SIMULATION_MSG_HEADER, simulationList, simulationCollisionList, LIST_SIMULATION_FOOTER);
        }
    },
    EXIT {
        @Override
        public List<String> getDisplayMessagePriorUserInput(Optional<CommandParams> dynamicValues) {
            return Arrays.asList(MessageConstants.EXIT);
        }
    },
    START_OVER {
        @Override
        public List<String> getDisplayMessagePriorUserInput(Optional<CommandParams> dynamicValues) {
            return Collections.emptyList();
        }
    };

    private static String getSimulationsNoCollision(Field field) {
        Map<String, Car> carNameToCar = field.getCarNameToCarMap();
        StringBuilder msgBuffer;
        msgBuffer = new StringBuilder();
        for (Map.Entry<String, Car> entry : carNameToCar.entrySet()) {
            Car car = entry.getValue();
            if (!car.isInCollision()) {
                msgBuffer.append(String.format(LIST_SIMULATION_NO_COLLISION_BODY, entry.getKey(),
                        car.getCurrentPositionOnField().getX(),
                        car.getCurrentPositionOnField().getY(),
                        car.getCurrentDirection().toString()));
                msgBuffer.append("\n");
            }
        }
        return msgBuffer.toString();
    }

    private static String getSimulationsCollision(Field field) {
        Map<Position,Set<String>> filteredMapOfCollisions = field.getFilteredMapOfCollisions();
        Map<String, Car> carNameToCar = field.getCarNameToCarMap();
        StringBuilder msgBuffer = new StringBuilder();

        for(Map.Entry<Position,Set<String>> positionsWithCollisionsMap:filteredMapOfCollisions.entrySet()) {
            Set<String> carsInCollision = positionsWithCollisionsMap.getValue();
            for(String carName : carsInCollision){
                for (String otherCarNameToCheck : carsInCollision) {
                    if(!carName.equals(otherCarNameToCheck)) {
                        msgBuffer.append(String.format(LIST_SIMULATION_COLLISION_BODY, carName, otherCarNameToCheck,
                                positionsWithCollisionsMap.getKey().getX(),
                                positionsWithCollisionsMap.getKey().getY(),
                                carNameToCar.get(carName).getCurrentCommandIndex()));
                        msgBuffer.append("\n");
                    }
                }

            }
        }
        return msgBuffer.toString();
    }


    private static String getListOfCars(Field field) {
        Map<String, Car> carNameToCar = field.getCarNameToCarMap();
        StringBuilder msgBuffer;
        msgBuffer = new StringBuilder();
        for (Map.Entry<String, Car> entry : carNameToCar.entrySet()) {
            Car car = entry.getValue();
            msgBuffer.append(String.format(LIST_CAR_BODY, entry.getKey(), car.getOriginalPositionOnField().getX(),
                    car.getOriginalPositionOnField().getY(),
                    car.getOriginalDirection(), car.getCommands()));
            msgBuffer.append("\n");
        }
        return msgBuffer.toString();
    }

    public abstract List<String> getDisplayMessagePriorUserInput(Optional<CommandParams> dynamicValues);
}


