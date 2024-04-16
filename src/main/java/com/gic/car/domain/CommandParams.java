package com.gic.car.domain;

import com.gic.car.view.MessageState;
import com.gic.car.view.SystemResponse;
import com.gic.car.view.UserInput;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class CommandParams {
    int carPositionX;
    int carPositionY;
    String carName;
    Direction carDirection;
    String carCommands;
    int fieldWidth;
    int fieldHeight;

    Field field;
    MessageState commandID;
    UserInput userInput;
    SystemResponse systemResponse;
    boolean parsingError;

    public void resetCommandParams () {
        fieldWidth = fieldHeight = carPositionX = carPositionY = 0;
        carName = null;
        carDirection = null;
        carCommands = null;
        field = null;
        commandID = null;
        userInput = null;
        systemResponse = null;
        parsingError = false;
    }

}
