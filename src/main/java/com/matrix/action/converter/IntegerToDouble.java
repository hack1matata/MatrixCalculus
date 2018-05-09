package com.matrix.action.converter;

public class IntegerToDouble extends TypeToType<Integer, Double> {

    @Override
    public Double invoke(Integer value) {
        return value.doubleValue();
    }
}
