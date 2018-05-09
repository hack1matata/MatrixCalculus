package com.matrix.action.add;

import com.matrix.action.BinaryOperation;

public class AddInteger extends BinaryOperation<Integer> {

    @Override
    public Integer invoke(Integer v1, Integer v2) {
        return v1+v2;
    }
}
