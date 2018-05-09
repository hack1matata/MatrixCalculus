package com.matrix.action.add;

import com.matrix.action.BinaryOperation;

public class AddDouble extends BinaryOperation<Double> {

    @Override
    public Double invoke(Double v1, Double v2) {
        return v1+v2;
    }
}
