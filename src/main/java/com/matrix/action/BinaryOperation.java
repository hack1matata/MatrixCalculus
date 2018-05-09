package com.matrix.action;

public abstract class BinaryOperation<T> implements Action {
    public abstract T invoke(T v1, T v2);
}
