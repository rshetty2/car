package com.gic.car.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Position implements Serializable {
    int x;
    int y;
}
