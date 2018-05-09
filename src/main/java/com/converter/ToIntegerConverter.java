package com.converter;

public class ToIntegerConverter extends PropertyConverter<Integer> {
    @Override
    public Integer convert(String value) {
        return Integer.parseInt(value);
    }
}
