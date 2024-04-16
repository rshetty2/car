package com.gic.car.exception;

public enum ValidationTypes {
    NOT_UNIQUE_CAR_NAME("Car Name already exists, choose another name"),
    INVALID_COMMAND("Command is Invalid");

    ValidationTypes(String s) {
    }
}
