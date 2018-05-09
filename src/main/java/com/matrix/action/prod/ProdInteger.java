package com.matrix.action.prod;

import com.matrix.action.BinaryOperation;

public class ProdInteger extends BinaryOperation<Integer> {
    @Override
    public Integer invoke(Integer v1, Integer v2) {
        return v1*v2;
    }
}
