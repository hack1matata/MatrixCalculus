package com.matrix.action.converter;

public class DoubleToInteger extends TypeToType<Double, Integer> {
    
    @Override
    public Integer invoke(Double value) {
        return value.intValue();
    }
}
