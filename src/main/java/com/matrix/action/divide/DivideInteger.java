package com.matrix.action.divide;

import com.matrix.action.BinaryOperation;

public class DivideInteger extends BinaryOperation<Integer> {
    @Override
    public Integer invoke(Integer v1, Integer v2) {
        return v1/v2;
    }
}