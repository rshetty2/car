package com.gic.car;

import com.gic.car.domain.CommandParams;
import com.gic.car.domain.Direction;
import com.gic.car.view.InputValidator;
import com.gic.car.view.MessageState;
import com.gic.car.view.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValidationTests {

    private InputValidator inputValidator;


    @BeforeEach
    public void setUp() {
        inputValidator = InputValidator.getInstance();
    }

    @Test
    public void givenWidthAndHeightStringForField_WhenValid_ThenExtractWidthAndHeight() {
        CommandParams commandParams = CommandParams.builder().commandID(MessageState.WELCOME).userInput(new UserInput("10 20")) .build();
        CommandParams commandParamsAfterValidation = inputValidator.validate(commandParams);
        assertEquals(10, commandParamsAfterValidation.getFieldWidth());
        assertEquals(20, commandParamsAfterValidation.getFieldHeight());
    }

    @Test
    public void givenCarNameString_WhenValid_ThenExtractName() {
        CommandParams commandParams = CommandParams.builder().commandID(MessageState.CAR_NAME_CREATION).userInput(new UserInput("Z999")).build();
        CommandParams commandParamsAfterValidation = inputValidator.validate(commandParams);
        assertEquals("Z999", commandParamsAfterValidation.getCarName());
    }

    @Test
    public void givenCarPositionString_WhenValid_ThenExtractPositions() {
        CommandParams commandParams = CommandParams.builder().commandID(MessageState.CAR_POSITION_CREATION).fieldWidth(10).fieldHeight(10).userInput(new UserInput("3 4 N")).build();
        CommandParams commandParamsAfterValidation = inputValidator.validate(commandParams);

        assertEquals(3, commandParamsAfterValidation.getCarPositionX());
        assertEquals(4, commandParamsAfterValidation.getCarPositionY());
        assertEquals(Direction.N, commandParamsAfterValidation.getCarDirection());
    }

    @Test
    public void givenCarCommandString_WhenValid_ThenExtractCommand() {
        CommandParams commandParams = CommandParams.builder().commandID(MessageState.CAR_COMMAND_CREATION).userInput(new UserInput(" LRF")).build();
        CommandParams commandParamsAfterValidation = inputValidator.validate(commandParams);
        assertEquals("LRF", commandParamsAfterValidation.getCarCommands());
    }


}
