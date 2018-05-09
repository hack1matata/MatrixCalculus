package com.matrix.action.divide;

import com.matrix.action.BinaryOperation;

public class DivideDouble extends BinaryOperation<Double> {
    @Override
    public Double invoke(Double v1, Double v2) {
        return v1/v2;
    }
}
