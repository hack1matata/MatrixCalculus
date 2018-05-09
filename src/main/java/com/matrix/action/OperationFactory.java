package com.matrix.action;

import com.matrix.action.add.AddDouble;
import com.matrix.action.add.AddInteger;
import com.matrix.action.conditional.BiggerDouble;
import com.matrix.action.conditional.BiggerInteger;
import com.matrix.action.conditional.SmallerDouble;
import com.matrix.action.conditional.SmallerInteger;
import com.matrix.action.converter.DoubleToDouble;
import com.matrix.action.converter.DoubleToInteger;
import com.matrix.action.converter.IntegerToDouble;
import com.matrix.action.converter.TypeToType;
import com.matrix.action.divide.DivideDouble;
import com.matrix.action.divide.DivideInteger;
import com.matrix.action.equals.EqualsDouble;
import com.matrix.action.equals.EqualsInteger;
import com.matrix.action.prod.ProdDouble;
import com.matrix.action.prod.ProdInteger;
import com.matrix.action.substract.SubstractDouble;
import com.matrix.action.substract.SubstractInteger;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

import static com.matrix.action.ActionType.*;

public class OperationFactory {

    Map<Pair<ActionType, Class<?>>, Action> actions = new HashMap<>();

    public OperationFactory() {
        addAllActions();
    }

    public <T> BinaryOperation<T> add(Class<T> clazz) {
        return (BinaryOperation<T>) actions.get(new Pair(ADD, clazz));
    }

    public <T> BinaryOperation<T> substract(Class<T> clazz) {
        return (BinaryOperation<T>) actions.get(new Pair(SUBSTRACT, clazz));
    }

    public <T> BinaryOperation<T> prod(Class<T> clazz) {
        return (BinaryOperation<T>) actions.get(new Pair(PROD, clazz));
    }

    public <T> BinaryOperation<T> divide(Class<T> clazz) {
        return (BinaryOperation<T>) actions.get(new Pair(DIVIDE, clazz));
    }

    public <U, T> TypeToType<U, T> convert(Class<U> clazzU, Class<T> clazzT) {
        ActionType action = null;

        if(clazzU.equals(Integer.class)) action = CONVERT_INTEGER;
        else if(clazzU.equals(Double.class)) action = CONVERT_DOUBLE;

        return (TypeToType<U, T>) actions.get(new Pair(action, clazzT));
    }

    public <T> BinaryPredicate<T> equals(Class<T> clazz) {
        return (BinaryPredicate<T>) actions.get(new Pair(EQUALS, clazz));
    }

    public <T> BinaryPredicate<T> bigger(Class<T> clazz) {
        return (BinaryPredicate<T>) actions.get(new Pair(BIGGER, clazz));
    }

    public <T> BinaryPredicate<T> smaller(Class<T> clazz) {
        return (BinaryPredicate<T>) actions.get(new Pair(SMALLER, clazz));
    }

    private void addAllActions() {
        actions.put(new Pair(ADD, Double.class), new AddDouble());
        actions.put(new Pair(ADD, Integer.class), new AddInteger());
        actions.put(new Pair(SUBSTRACT, Double.class), new SubstractDouble());
        actions.put(new Pair(SUBSTRACT, Integer.class), new SubstractInteger());
        actions.put(new Pair(PROD, Double.class), new ProdDouble());
        actions.put(new Pair(PROD, Integer.class), new ProdInteger());
        actions.put(new Pair(DIVIDE, Double.class), new DivideDouble());
        actions.put(new Pair(DIVIDE, Integer.class), new DivideInteger());
        actions.put(new Pair(CONVERT_INTEGER, Double.class), new IntegerToDouble());
        actions.put(new Pair(CONVERT_DOUBLE, Integer.class), new DoubleToInteger());
        actions.put(new Pair(EQUALS, Integer.class), new EqualsInteger());
        actions.put(new Pair(EQUALS, Double.class), new EqualsDouble());
        actions.put(new Pair(BIGGER, Double.class), new BiggerDouble());
        actions.put(new Pair(BIGGER, Integer.class), new BiggerInteger());
        actions.put(new Pair(BIGGER, Double.class), new SmallerDouble());
        actions.put(new Pair(BIGGER, Integer.class), new SmallerInteger());

    }
}

enum ActionType {
    ADD,
    SUBSTRACT,
    PROD,
    DIVIDE,
    EQUALS,
    BIGGER,
    SMALLER,
    CONVERT_INTEGER,
    CONVERT_DOUBLE;
}