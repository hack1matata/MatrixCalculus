package com.matrix.action.conditional;

import com.matrix.action.BinaryPredicate;

public class SmallerDouble extends BinaryPredicate<Double> {
    @Override
    public boolean invoke(Double v1, Double v2) {
        return v1 < v2;
    }
}
