package com.converter;

import java.util.HashMap;
import java.util.Map;

public class PropertyConverterFactory {

    Map<Class<?>, PropertyConverter<?>> converters = new HashMap<>();

    public PropertyConverterFactory(){
        addAllConverters();
    }

    public <T> PropertyConverter<T> getConverter(Class<T> clazz){
        return (PropertyConverter<T>) converters.get(clazz);
    }

    private void addAllConverters(){
        converters.put(Integer.class, new ToIntegerConverter());
        converters.put(Double.class, new ToDoubleConverter());
    }
}
