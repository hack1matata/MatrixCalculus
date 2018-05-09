package com.matrix.action.equals;

import com.matrix.action.BinaryPredicate;

public class EqualsInteger extends BinaryPredicate<Integer> {

    @Override
    public boolean invoke(Integer v1, Integer v2) {
        return v1.equals(v2);
    }
}
