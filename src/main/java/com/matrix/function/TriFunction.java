package com.matrix.function;

@FunctionalInterface
public interface TriFunction<A,B,C,R> {
    public R apply(A p1, B p2, C p3);
}
