package com.gic.car.behavior.command;


import com.gic.car.domain.CommandParams;
import com.gic.car.domain.Field;


@FunctionalInterface
public interface Command<T> {
    Field execute(CommandParams commandParams);
}
