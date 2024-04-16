package com.gic.car.behavior.proxy;

import com.gic.car.domain.Field;
import com.gic.car.domain.Car;
import org.apache.commons.lang3.SerializationUtils;


/**
 * Manages the Field state as a wrapper on top of Field accepting commands.
 */

public class FieldManager {

    private static FieldManager fieldManager;

    private Field field;

    private FieldManager() {

    }

    private void setField(Field field) {
        this.field = field;
    }

    public static FieldManager getInstance() {
        if(fieldManager == null) {
            synchronized (FieldManager.class) {
                fieldManager = new FieldManager();
                fieldManager.setField(new Field(0,0));
            }
        }
        return fieldManager;
    }


    public Field getField() {
        return SerializationUtils.clone(field);
    }

    public void clearFieldValues() {
        field.reset();
    }

    public void createField(int width, int height) {
        initField(width, height);
    }

    private void initField(int width, int height) {
        if(this.field == null)  this.field = new Field(width, height);
        else this.field.setFieldDimensions(width, height);
    }

    public void addCarToField(Car car) {
        field.addCarToCircuit(car);
    }

    public void runSimulation() {
        field.simulate();
    }
}
