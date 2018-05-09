package com.matrix.action;

public abstract class BinaryPredicate<T> implements Action {
    public abstract boolean invoke(T v1, T v2);
}
