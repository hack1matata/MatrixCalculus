package com.matrix.action.conditional;

import com.matrix.action.BinaryPredicate;

public class BiggerInteger extends BinaryPredicate<Integer> {
    @Override
    public boolean invoke(Integer v1, Integer v2) {
        return v1 > v2;
    }
}
