package com.matrix.action.converter;

import com.matrix.action.Action;

public abstract class TypeToType<U, T> implements Action {
    public abstract T invoke(U value);
}
