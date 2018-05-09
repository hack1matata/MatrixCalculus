package com.converter;

public class ToDoubleConverter extends PropertyConverter<Double> {
    @Override
    public Double convert(String value) {
        return Double.parseDouble(value);
    }
}
