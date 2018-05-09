package com.matrix;

import java.util.List;

public class Converter {

    public static <T> Matrix<T> convertToMatrix(List<List<T>> rawMatrix, Class<T> clazz){
        T [][] matrixArray = (T[][]) new Object [rawMatrix.size()][rawMatrix.get(0).size()];
        for(int i=0;i<matrixArray.length;i++){
            List<T> line = rawMatrix.get(i);
            matrixArray[i] = (T[]) line.toArray();
        }
        return new Matrix<>(matrixArray, clazz);
    }
}
