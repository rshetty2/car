package com.gic.car.behavior;

import com.gic.car.behavior.command.CommandFactory;
import com.gic.car.behavior.proxy.FieldManager;
import com.gic.car.domain.CommandParams;
import com.gic.car.domain.Field;
import com.gic.car.view.InputValidator;
import com.gic.car.view.MessageState;
import com.gic.car.view.ViewRenderer;
import lombok.Setter;


public class CommandExecutor {

    private CommandFactory commandFactory;

    private InputValidator inputValidator;
    private ViewRenderer viewRenderer;

    @Setter
    private FieldManager fieldManager;

    public void setCommandFactory(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }


    public void setInputValidator(InputValidator validateInput) {
        this.inputValidator = validateInput;
    }


    public void setViewRenderer(ViewRenderer viewRenderer) {
        this.viewRenderer = viewRenderer;
    }



    public CommandParams executeCommand(CommandParams commandParams) {

        if (commandParams.getCommandID().equals(MessageState.FIELD_CREATION)) {
            Field field = commandFactory.getCreateFieldCommand(fieldManager).execute(commandParams);
            commandParams.setField(field);
            return commandParams;
        }

        if (commandParams.getCommandID().equals(MessageState.CAR_SELECTION)) {
            return commandParams;
        }

        if (commandParams.getCommandID().equals(MessageState.CAR_POSITION_CREATION)) {
            return commandParams;
         }

        if (commandParams.getCommandID().equals(MessageState.CAR_COMMAND_CREATION)) {
            return commandParams;
        }

        if (commandParams.getCommandID().equals(MessageState.LIST_CAR)) {
            Field field = commandFactory.getAddCarCommand(fieldManager).execute(commandParams);
            commandParams.setField(field);
            return commandParams;
           }

        if(commandParams.getCommandID().equals(MessageState.RUN_SIMULATION)) {
            Field field = commandFactory.getSimulationCommand(fieldManager).execute(commandParams);
            commandParams.setField(field);
            return commandParams;
        }

        if(commandParams.getCommandID().equals(MessageState.START_OVER)) {
            Field field = commandFactory.getStartOverCommand(fieldManager).execute(commandParams);
            commandParams.setField(field);
            commandParams.setCommandID(MessageState.START_OVER);
            return commandParams;
        }

        if(commandParams.getCommandID().equals(MessageState.EXIT)) {
            return commandParams;
        }

        return null;
    }
}
