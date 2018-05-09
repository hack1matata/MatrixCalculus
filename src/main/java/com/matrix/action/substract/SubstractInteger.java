package com.matrix.action.substract;

import com.matrix.action.BinaryOperation;

public class SubstractInteger extends BinaryOperation<Double> {

    @Override
    public Double invoke(Double v1, Double v2) {
        return v1-v2;
    }
}
