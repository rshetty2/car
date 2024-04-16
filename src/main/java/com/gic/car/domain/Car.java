package com.gic.car.domain;

import lombok.*;

import java.io.Serializable;


@Getter
@AllArgsConstructor
@ToString
@Setter
@Builder
public class Car implements Serializable {
    private String name;
    private Position originalPositionOnField;
    private Position currentPositionOnField;

    private Direction originalDirection;
    private Direction currentDirection;

    private String commands;
    private boolean isInCollision;
    private int currentCommandIndex;

    public Car() {

    }

    public Position processNextCommand(int fieldWidth, int fieldHeight) {
        if(!isInCollision && currentCommandIndex < commands.length()) {
            char command = Character.toUpperCase(commands.charAt(currentCommandIndex));
            if(command == 'L') turnLeft();
            if(command == 'R') turnRight();
            if(command == 'F') moveForward(fieldWidth,fieldHeight);
            ++currentCommandIndex;
        }
        return getCurrentPositionOnField();
    }

    public void turnLeft() {
        if(currentDirection.equals(Direction.N)) setDirectionOfCar(Direction.W);
        else if(currentDirection.equals(Direction.E)) currentDirection = Direction.N;
        else if(currentDirection.equals(Direction.W)) setDirectionOfCar(Direction.S);
        else if(currentDirection.equals(Direction.S)) currentDirection = Direction.E;
    }

    public void turnRight() {
        if(currentDirection.equals(Direction.N)) setCurrentDirection(Direction.E);
        else if(currentDirection.equals(Direction.E)) setCurrentDirection(Direction.S);
        else if(currentDirection.equals(Direction.W)) setCurrentDirection(Direction.N);
        else if(currentDirection.equals(Direction.S)) setCurrentDirection(Direction.W);
    }

    public void moveForward(int fieldWidth, int fieldHeight) {

        int currentX = getCurrentPositionOnField().getX();
        int currentY = getCurrentPositionOnField().getY();
        if((currentDirection.equals(Direction.N)) && (fieldHeight > currentY+1)) setPosition(currentX,currentY+1);
        else if((currentDirection.equals(Direction.E)) && (fieldWidth > currentX+1)) setPosition(currentX+1,currentY);
        else if((currentDirection.equals(Direction.W)) && (currentX-1 >= 0)) setPosition(currentX-1,currentY);
        else if((currentDirection.equals(Direction.S)) && (currentY-1 >= 0)) setPosition(currentX,currentY-1);
    }

    private void setPosition(int x, int y) {
        this.currentPositionOnField.x = x;
        this.currentPositionOnField.y = y;
    }

    private void setDirectionOfCar(Direction newDirection) {
        this.currentDirection = newDirection;
    }
    public void setIsInCollision() {
        this.isInCollision = true;
    }

}
