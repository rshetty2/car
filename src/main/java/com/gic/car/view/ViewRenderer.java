package com.gic.car.view;

import com.gic.car.domain.CommandParams;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ViewRenderer {

    private static ViewRenderer viewRenderer;

    private ViewRenderer() {

    }
    public static ViewRenderer getInstance() {
        if(viewRenderer == null) {
            viewRenderer = new ViewRenderer();
        }
        return viewRenderer;
    }

    @Setter
    CommandParams commandParams;
    public CommandParams renderView(CommandParams commandParams) {

        if (commandParams.getCommandID().equals(MessageState.FIELD_CREATION)) {
            commandParams.setSystemResponse(new SystemResponse(MessageState.INITIAL_CAR_SIMULATION_SELECTION,
                    flattenList(MessageState.INITIAL_CAR_SIMULATION_SELECTION.getDisplayMessagePriorUserInput(Optional.of(commandParams)))));
            commandParams.setCommandID(MessageState.INITIAL_CAR_SIMULATION_SELECTION);
            return commandParams;
        }

        if (commandParams.getCommandID().equals(MessageState.CAR_SELECTION)) {
            commandParams.setSystemResponse(new SystemResponse(MessageState.CAR_NAME_CREATION, flattenList(
                    MessageState.CAR_NAME_CREATION.getDisplayMessagePriorUserInput(Optional.of(commandParams)))));
            commandParams.setCommandID(MessageState.CAR_NAME_CREATION);
            return commandParams;
        }

        if (commandParams.getCommandID().equals(MessageState.CAR_POSITION_CREATION)) {
            commandParams.setSystemResponse(new SystemResponse(MessageState.CAR_POSITION_CREATION,
                    flattenList(MessageState.CAR_POSITION_CREATION.getDisplayMessagePriorUserInput(Optional.of(commandParams)))));
            return commandParams;
        }

        if (commandParams.getCommandID().equals(MessageState.CAR_COMMAND_CREATION)) {
            commandParams.setSystemResponse(new SystemResponse(MessageState.CAR_COMMAND_CREATION,
                    flattenList(MessageState.CAR_COMMAND_CREATION.getDisplayMessagePriorUserInput(Optional.of(commandParams)))));
            return commandParams;
        }

        if (commandParams.getCommandID().equals(MessageState.LIST_CAR)) {
            commandParams.setSystemResponse(new SystemResponse(MessageState.CAR_SIMULATION_SELECTION,
                    flattenList(MessageState.CAR_SIMULATION_SELECTION.getDisplayMessagePriorUserInput(Optional.of(commandParams)))));
            return commandParams;
        }

        if (commandParams.getCommandID().equals(MessageState.RUN_SIMULATION)) {
            commandParams.setSystemResponse(new SystemResponse(MessageState.STARTOVER_EXIT_SELECTION,
                    flattenList(MessageState.STARTOVER_EXIT_SELECTION.getDisplayMessagePriorUserInput(Optional.of(commandParams)))));
            return commandParams;
        }

        if (commandParams.getCommandID().equals(MessageState.START_OVER)) {
            commandParams.setSystemResponse(new SystemResponse(MessageState.WELCOME,
                    flattenList(MessageState.WELCOME.getDisplayMessagePriorUserInput(Optional.of(commandParams)))));
        }

        if (commandParams.getCommandID().equals(MessageState.EXIT)) {
            commandParams.setSystemResponse(new SystemResponse(MessageState.EXIT, flattenList(MessageState.EXIT.getDisplayMessagePriorUserInput(Optional.of(commandParams)))));
        }

        return commandParams;
    }

    private String flattenList(List<String> displayMessagePriorUserInput) {
        return displayMessagePriorUserInput.stream().collect(Collectors.joining(""));
    }


}
