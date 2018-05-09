package com.matrix.action;

public abstract class UnaryOperation<T> implements Action {

    public abstract T invoke(T value);
}
