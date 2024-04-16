package com.gic.car.behavior.command;

import com.gic.car.behavior.proxy.FieldManager;
import com.gic.car.domain.CommandParams;
import com.gic.car.domain.Field;

public class SimulationCommand implements Command {

    private FieldManager fieldManager;

    public void setFieldManager(FieldManager fieldManager) {
        this.fieldManager = fieldManager;
    }
    @Override
    public Field execute(CommandParams commandParams) {
        fieldManager.runSimulation();
        return fieldManager.getField();
    }

}
