package com.gic.car.behavior.command;

import com.gic.car.behavior.proxy.FieldManager;


public class CommandFactory {

    private static CommandFactory commandFactory;

    private CommandFactory() {

    }

    public static CommandFactory getInstance() {
        if(commandFactory == null) {
            synchronized (CommandFactory.class) {
                commandFactory = new CommandFactory();
            }
        }
        return commandFactory;
    }



    private CreateFieldCommand createFieldCommand;

    private AddCarCommand addCarCommand;

    private SimulationCommand simulationCommand;


    private StartOverCommand startOverCommand;


    public CreateFieldCommand getCreateFieldCommand(FieldManager fieldManager) {
        if(createFieldCommand == null) {
            createFieldCommand = new CreateFieldCommand();
            createFieldCommand.setFieldManager(fieldManager);
        }
        return createFieldCommand;
    }


    public AddCarCommand getAddCarCommand(FieldManager fieldManager) {
        if (addCarCommand == null) {
            addCarCommand = new AddCarCommand();
            addCarCommand.setFieldManager(fieldManager);
         }
        return addCarCommand;
    }

    public SimulationCommand getSimulationCommand(FieldManager fieldManager) {
        if(simulationCommand == null) {
            simulationCommand = new SimulationCommand();
            simulationCommand.setFieldManager(fieldManager);
        }
        return simulationCommand;
    }

    public StartOverCommand getStartOverCommand(FieldManager fieldManager) {
        if(startOverCommand == null) {
            startOverCommand = new StartOverCommand();
            startOverCommand.setFieldManager(fieldManager);
        }
        return startOverCommand;
    }

}
