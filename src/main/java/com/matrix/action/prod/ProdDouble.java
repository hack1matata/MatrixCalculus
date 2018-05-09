package com.matrix.action.prod;

import com.matrix.action.BinaryOperation;

public class ProdDouble extends BinaryOperation<Double> {
    @Override
    public Double invoke(Double v1, Double v2) {
        return v1*v2;
    }
}
