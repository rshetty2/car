package com.gic.car.view;


public final class MessageConstants {
    private MessageConstants() {

    }

    public static final String WELCOME = "Welcome to Auto Driving Car Simulation!\nPlease enter the width and height of the simulation field in x y format. Range for each being 1-999:\n";


    public static final String POST_FIELD_CREATION_MSG = "You have created a field of %d x %d\n\nPlease choose from the following options:\n[1] Add a car to field\n[2] Run simulation\n";

    public static final String CAR_NAME_MSG = "Please enter the name of the car. First one is character followed by zero to three numbers, e.g A999 or A:\n";

    public static final String CAR_POSITION_MSG = "Please enter initial position of car %s in x y Direction format, e.g 2 2 N. Valid values:  x < FieldDimensionX, y < FieldDimensionY, Direction N,E,W,S:\n";

    public static final String CAR_COMMAND_MSG = "Please enter the commands for car %s. e.g LFRFF. Valid values for each command: L,R and F (Case sensitive), max length 100 chars:";

    public static final String LIST_CAR_MSG_HEADER = "Your current list of cars are:\n";

    public static final String LIST_CAR_BODY = "%s,(%d,%d) %s, %s";

    public static final String LIST_CAR_FOOTER = "Please choose from the following options:\n[1] Add a car to field\n[2] Run simulation\n";

    public static final String LIST_SIMULATION_MSG_HEADER = "After simulation, the result is:\n";
    public static final String LIST_SIMULATION_NO_COLLISION_BODY = "%s,(%d,%d) %s";
    public static final String LIST_SIMULATION_COLLISION_BODY = "%s, collides with %s at (%d,%d) at step %d";

    public static final String LIST_SIMULATION_FOOTER = "Please choose from the following options:\n[1] Start over\n[2] Exit\n";

    public static final String EXIT = "Thank you for running the simulation. Goodbye!";

}
