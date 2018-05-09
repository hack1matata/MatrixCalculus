package com.matrix;

import com.matrix.action.BinaryOperation;
import com.matrix.action.BinaryPredicate;
import com.matrix.action.OperationFactory;
import com.matrix.action.converter.TypeToType;
import com.matrix.function.TriFunction;
import com.permutation.PermutationFast;

import static org.assertj.core.api.Assertions.assertThat;

public class Matrix<T> {

    private OperationFactory operationFactory = new OperationFactory();

    private T[][] rawMatrix;
    private int height;
    private int width;
    private Class<T> clazz;

    BinaryOperation<T> prodOp;
    BinaryOperation<T> addOp;
    BinaryOperation<T> substractOp;
    BinaryOperation<T> divideOp;
    TypeToType<Integer, T> integerToT;
    TypeToType<Double, T> doubleToT;
    BinaryPredicate<T> equals;
    BinaryPredicate<T> bigger;
    BinaryPredicate<T> smaller;

    TriFunction<Integer, Integer, Matrix, T> calculateAdjointValue = (Integer heightIndex, Integer widthIndex, Matrix obj) -> {
        assertThat(height >= 2);
        assertThat(width >= 2);
        T[][] rawMatrixAdjoint = (T[][]) new Object[width - 1][height - 1];
        int adjointJ, adjointI = 0;
        for (int i = 0; i < height; i++) {
            if (i == heightIndex) continue;
            adjointJ = 0;
            for (int j = 0; j < width; j++) {
                if (j == widthIndex) continue;
                rawMatrixAdjoint[adjointI][adjointJ] = rawMatrix[i][j];
                adjointJ++;
            }
            adjointI++;
        }
        Matrix<T> matrixAdjoint = new Matrix<>(rawMatrixAdjoint, clazz);
        return prodOp.invoke(doubleToT.invoke(Math.pow(-1d, heightIndex + widthIndex)), matrixAdjoint.determinant());
    };

    String separator = System.getProperty("line.separator");

    public Matrix(T[][] rawMatrix, Class<T> clazz) {
        this.rawMatrix = rawMatrix;
        this.height = rawMatrix.length;
        this.width = rawMatrix[0].length;
        this.clazz = clazz;

        prodOp = operationFactory.prod(clazz);
        addOp = operationFactory.add(clazz);
        substractOp = operationFactory.substract(clazz);
        divideOp = operationFactory.divide(clazz);
        equals = operationFactory.equals(clazz);
        bigger = operationFactory.bigger(clazz);
        smaller = operationFactory.smaller(clazz);
        integerToT = operationFactory.convert(Integer.class, clazz);
        doubleToT = operationFactory.convert(Double.class, clazz);
    }

    public Matrix<T> adjoint() {
        T[][] rawMatrixCalculated = (T[][]) new Object[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rawMatrixCalculated[j][i] = calculateAdjointValue.apply(i, j, this);
            }
        }

        return new Matrix<>(rawMatrixCalculated, clazz);
    }

    public Matrix<T> prod(T value) {
        T[][] rawMatrixCalculated = (T[][]) new Object[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rawMatrixCalculated[i][j] = prodOp.invoke(rawMatrix[i][j], value);
            }
        }

        return new Matrix<>(rawMatrixCalculated, clazz);
    }

    public Matrix<T> prod(Matrix<T> value) {
        assertThat(width == value.getHeight());
        assertThat(height == value.getWidth());

        T[][] rawMatrixCalculated = (T[][]) new Object[height][value.getWidth()];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < value.getWidth(); j++) {
                T sum = integerToT.invoke(0);
                for (int k = 0; k < width; k++) {
                    sum = addOp.invoke(sum, prodOp.invoke(rawMatrix[i][k], value.getElement(k, j)));
                }
                rawMatrixCalculated[i][j] = sum;
            }
        }

        return new Matrix<>(rawMatrixCalculated, clazz);
    }

    public Matrix<T> inverse() {
        T det = determinant();
        T coeficient = divideOp.invoke(integerToT.invoke(1), det);

        return adjoint().prod(coeficient);
    }

    public T determinant() {
        assertThat(height == width).isTrue();
        assertThat(height > 0).isTrue();

        PermutationFast permutation = new PermutationFast(height);
        int[] perm;

        T sum = integerToT.invoke(0);
        int matrixSize = height;
        while ((perm = permutation.next()) != null) {
            T sign = integerToT.invoke(PermutationFast.getSign(perm));
            T product = integerToT.invoke(1);
            for (int i = 0; i < matrixSize; i++) {
                product = prodOp.invoke(product, getElement(i, perm[i] - 1));
            }
            sum = addOp.invoke(sum, prodOp.invoke(sign, product));
        }

        return sum;
    }

    public Matrix<T> transpose() {
        T[][] rawMatrixTranspose = (T[][]) new Object[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rawMatrixTranspose[j][i] = rawMatrix[i][j];
            }
        }

        return new Matrix<>(rawMatrixTranspose, clazz);
    }

    public void rank() {
        Pivot pivot = new Pivot(0,0);

        Matrix<T> echalonMatrix = null;

        while(pivot.getI() != height && pivot.getJ() != width){
            findPivot(pivot);
            echalonMatrix = divideRow(pivot, pivot.getElement(this));
            echalonMatrix.makeEchelonByColumnDownwards(pivot);
        }   
    }

    public void makeEchelonByColumnDownwards(Pivot pivot){
        T zero = integerToT.invoke(0);
        int iIndex = pivot.getI();

        for(int i=iIndex+1;i<height;i++){
            if(equals.invoke(pivot.getElement(this), zero)) return;
            else modifyRows(pivot, i);
        }
    }

    private Matrix<T> modifyRows(Pivot pivot, int rowIndex){
        T v1 = pivot.getElement(this);
        T v2 = getElement(rowIndex, pivot.getJ());
        T coeficient = integerToT.invoke(1);

        int iIndex=pivot.getI();
        int jIndex=pivot.getJ();
        T[][] rawMatrixCalculated = (T[][]) new Object[height][width];

        if(bigger.invoke(v1, v2)){
            coeficient = divideOp.invoke(v1, v2);
        } else if (smaller.invoke(v1, v2)){
            coeficient = divideOp.invoke(v2, v1);
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(i==rowIndex && j>=jIndex) {
                    rawMatrixCalculated[i][j] = substractOp
                            .invoke(rawMatrix[i][j]
                                  , prodOp.invoke(rawMatrix[iIndex][j], coeficient));
                } else {
                    rawMatrixCalculated[i][j] = rawMatrix[i][j];
                }
            }
        }
        return new Matrix<>(rawMatrixCalculated, clazz);
    }

    private Matrix<T> multiplyRow(Pivot pivot, T value){
        int iIndex=pivot.getI();
        int jIndex=pivot.getJ();

        T[][] rawMatrixCalculated = (T[][]) new Object[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(i==iIndex && j>=jIndex) {
                    rawMatrixCalculated[i][j] = prodOp.invoke(rawMatrix[i][j], value);
                } else {
                    rawMatrixCalculated[i][j] = rawMatrix[i][j];
                }
            }
        }
        return new Matrix<>(rawMatrixCalculated, clazz);
    }

    private Matrix<T> divideRow(Pivot pivot, T value){
        int iIndex=pivot.getI();
        int jIndex=pivot.getJ();

        T[][] rawMatrixCalculated = (T[][]) new Object[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(i==iIndex && j>=jIndex) {
                    rawMatrixCalculated[i][j] = divideOp.invoke(rawMatrix[i][j], value);
                } else {
                    rawMatrixCalculated[i][j] = rawMatrix[i][j];
                }
            }
        }
        return new Matrix<>(rawMatrixCalculated, clazz);
    }

    private void findPivot(Pivot pivot) {
        T zero = integerToT.invoke(0);
        int iIndex = pivot.getI();
        int switchIndex = iIndex;
        for (int i = iIndex; i < height; i++) {
            if (!pivot.isEqualTo(this, zero)) {
                if (pivot.getI() != iIndex) {
                    swapLines(switchIndex++, pivot.getI());
                }
            }
            pivot.incrementI();
        }
    }

    private void swapLines(int lineAIndex, int lineBIndex) {
        for (int j = 0; j < width; j++) {
            T aux = rawMatrix[lineBIndex][j];
            rawMatrix[lineBIndex][j] = rawMatrix[lineAIndex][j];
            rawMatrix[lineAIndex][j] = aux;
        }
    }

    private void applyFilter(Matrix raw, Matrix filter, Position pos) {
        for (int i = 0; i < filter.getHeight(); i++) {
            for (int j = 0; j < filter.getWidth(); j++) {
                int rawHeightIndex = i + pos.getHeight();
                int rawWidthIndex = j + pos.getWidth();
                T value = addOp.invoke((T) raw.getElement(rawHeightIndex, rawWidthIndex), (T) filter.getElement(i, j));
                raw.setElement(rawHeightIndex, rawWidthIndex, value);
            }
        }
    }

    public Matrix filter(Matrix<T> rawFilter) {
        assertThat(height >= rawFilter.getHeight());
        assertThat(width >= rawFilter.getWidth());

        Matrix modified = this.clone();

        for (int i = 0; i <= getFilteringHeight(rawFilter); i++) {
            for (int j = 0; j <= getFilteringWidth(rawFilter); j++) {
                applyFilter(modified, rawFilter, new Position(i, j));
            }
        }

        return modified;
    }

    public void print() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                buffer.append(rawMatrix[i][j]);
                buffer.append(",");
            }
            buffer.setLength(buffer.length() - 1);
            buffer.append(separator);
        }
        System.out.println(buffer.toString());
    }

    public Matrix<T> clone() {
        return new Matrix<>(rawMatrix.clone(), clazz);
    }

    private int getFilteringHeight(Matrix rawFilter) {
        return height - rawFilter.getHeight();
    }

    private int getFilteringWidth(Matrix rawFilter) {
        return width - rawFilter.getWidth();
    }

    public T getElement(int i, int j) {
        return rawMatrix[i][j];
    }

    public T setElement(int i, int j, T value) {
        return rawMatrix[i][j] = value;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private class Pivot {
        int i;
        int j;

        public Pivot(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public void incrementI() {
            i++;
        }

        public void incrementJ() {
            j++;
        }

        public T getElement(Matrix<T> matrix){
            return matrix.getElement(i, j);
        }

        public boolean isEqualTo(Matrix<T> matrix, T value) {
            return equals.invoke(matrix.getElement(i, j), value);
        }

        public boolean isEqualTo(Matrix<T> matrix, Pivot value) {
            return equals.invoke(matrix.getElement(i, j), matrix.getElement(value.getI(), value.getJ()));
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }
    }
}
