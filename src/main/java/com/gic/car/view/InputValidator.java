package com.gic.car.view;


import com.gic.car.domain.CommandParams;
import com.gic.car.domain.Direction;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InputValidator {

    private static InputValidator inputValidator;
    private InputValidator() {

    }

    public static InputValidator getInstance() {
        if(inputValidator == null) {
            inputValidator =  new InputValidator();
        }
        return inputValidator;
    }

    private final static Pattern fieldPattern = Pattern.compile("\\b([0-9]{1,3})\\b");
    private final static Pattern carOrSimulationPattern = Pattern.compile("\\b([1-2]{1})\\b");
    private final static Pattern carNamePattern = Pattern.compile("\\b([A-Za-z]{1,3}[0-9]{0,3})\\b");
    private final static Pattern carPositionDirectionPattern = Pattern.compile("^(\\d{1,3})\\s+(\\d{1,3})\\s+([NEWS])$");

    private final static Pattern carCommandPattern = Pattern.compile("\s*\\b([LRF]{1,100})\\b\s*$");
    private final static Pattern startOverExitPattern = Pattern.compile("\\b([1-2]{1})\\b");

    public CommandParams parseFieldDimension(CommandParams commandParams) {
        Matcher matcher = fieldPattern.matcher(commandParams.getUserInput().input());
        String width = null, height = null;

        if(matcher.find()) width = matcher.group();
        if(matcher.find()) height = matcher.group();


        if(width != null && height !=null) {
            commandParams.setFieldWidth(Integer.parseInt(width));
            commandParams.setFieldHeight(Integer.parseInt(height));
            commandParams.setCommandID(MessageState.FIELD_CREATION);
        } else {
            commandParams.setCommandID(MessageState.START_OVER);
        }
        return commandParams;
    }

    public CommandParams parseCarOrSimulation(CommandParams commandParams) {
        Matcher matcher = carOrSimulationPattern.matcher(commandParams.getUserInput().input());
        String option = null;
        boolean parseError=false;

        if(matcher.find()) {
            option = matcher.group();
        } else {
            parseError=true;
        }

        if(!parseError && option.equals("1")) {
            commandParams.setCommandID(MessageState.CAR_SELECTION);
        }

        if(!parseError && option.equals("2"))
            commandParams.setCommandID(MessageState.RUN_SIMULATION);

        return commandParams;
    }

    private CommandParams parseCarName(CommandParams commandParams) {
        Matcher matcher = carNamePattern.matcher(commandParams.getUserInput().input());

        if(matcher.find()) {
            commandParams.setCarName(matcher.group());
            commandParams.setCommandID(MessageState.CAR_POSITION_CREATION);
        }
        return commandParams;
    }

    private CommandParams parseCarPosition(CommandParams commandParams) {
        Matcher matcher = carPositionDirectionPattern.matcher(commandParams.getUserInput().input());
        if (matcher.matches()) {
            if ((Integer.parseInt(matcher.group(1)) >= commandParams.getFieldWidth())
                || (Integer.parseInt(matcher.group(2)) >= commandParams.getFieldHeight())
                || (Arrays.stream(Direction.values()).noneMatch(d -> d.toString().equals(matcher.group(3))))
            ) return commandParams;

            commandParams.setCarPositionX(Integer.parseInt(matcher.group(1)));
            commandParams.setCarPositionY(Integer.parseInt(matcher.group(2)));
            commandParams.setCarDirection(Direction.valueOf(matcher.group(3)));
            commandParams.setCommandID(MessageState.CAR_COMMAND_CREATION);
        }
        return commandParams;
    }

    private CommandParams parseCarCommand(CommandParams commandParams) {
        Matcher matcher = carCommandPattern.matcher(commandParams.getUserInput().input());
        if (matcher.matches()) {
            commandParams.setCommandID(MessageState.LIST_CAR);
            commandParams.setCarCommands(matcher.group(1));
        }
        return commandParams;
    }

    private CommandParams parseStartOverExitCommand(CommandParams commandParams) {
        Matcher matcher = startOverExitPattern.matcher(commandParams.getUserInput().input());
        String option;

        if (matcher.find()) {
            option = matcher.group();

            if (option.equals("1")) {
                commandParams.setCommandID(MessageState.START_OVER);
            }

            if (option.equals("2")) {
                commandParams.setCommandID(MessageState.EXIT);
            }
        }
        return commandParams;
    }


        public CommandParams validate(CommandParams commandParams) {

        if(commandParams.getCommandID().equals(MessageState.WELCOME))
            commandParams.setCommandID(MessageState.FIELD_CREATION);

        MessageState messageState = commandParams.getCommandID();

        if(messageState.equals(MessageState.FIELD_CREATION)) {
            return parseFieldDimension(commandParams);
        }
        if(messageState.equals(MessageState.INITIAL_CAR_SIMULATION_SELECTION)) {
            return parseCarOrSimulation(commandParams);
        }
        if(messageState.equals(MessageState.CAR_NAME_CREATION)) {
            return parseCarName(commandParams);
        }
        if(messageState.equals(MessageState.CAR_POSITION_CREATION)) {
            return parseCarPosition(commandParams);
        }

        if(messageState.equals(MessageState.CAR_COMMAND_CREATION)) {
            return parseCarCommand(commandParams);
        }
        if(messageState.equals(MessageState.CAR_SIMULATION_SELECTION)) {
            return parseCarOrSimulation(commandParams);
        }

        if(messageState.equals(MessageState.STARTOVER_EXIT_SELECTION)) {
            return parseStartOverExitCommand(commandParams);
        }
        return commandParams;
    }

}
