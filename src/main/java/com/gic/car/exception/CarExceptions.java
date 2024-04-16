package com.gic.car.exception;

public class CarExceptions extends RuntimeException {
    public CarExceptions() {
        super();
    }
    public CarExceptions(String validationError) {
        super(validationError);
    }

}
