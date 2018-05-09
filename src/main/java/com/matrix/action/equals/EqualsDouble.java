package com.matrix.action.equals;

import com.matrix.action.Action;
import com.matrix.action.BinaryPredicate;

public class EqualsDouble extends BinaryPredicate<Double> {

    @Override
    public boolean invoke(Double v1, Double v2) {
        return v1.equals(v2);
    }
}
