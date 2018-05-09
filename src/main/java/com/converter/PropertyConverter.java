package com.converter;

public abstract class PropertyConverter<T> {
    T value;
    public abstract T convert(String value);
}
