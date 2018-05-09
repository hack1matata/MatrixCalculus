package com.matrix.action.converter;

public class DoubleToDouble extends TypeToType<Double, Double> {
    
    @Override
    public Double invoke(Double value) {
        return value;
    }
}
