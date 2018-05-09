package com.matrix;

import com.converter.PropertyConverter;
import com.converter.PropertyConverterFactory;
import com.error.MatrixException;
import javafx.util.Pair;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MatrixReader<T> {

    PropertyConverterFactory conversionFactory = new PropertyConverterFactory();

    Pair<T,T> test;

    Class<T> clazz;

    public MatrixReader(Class<T> clazz){
        this.clazz = clazz;
    }

    public Matrix<T> readMatrix(String filePath) throws MatrixException {
        List<List<T>> rawMatrix = new ArrayList<>();

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> {
                List<T> rawLine = new ArrayList<>();
                Arrays.asList(line.split(",")).stream().forEach(elem -> {
                        rawLine.add(conversionFactory.getConverter(clazz).convert(elem.trim()));
                });
                rawMatrix.add(rawLine);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        validateAllLinesHaveSameWidth(rawMatrix);

        return Converter.convertToMatrix(rawMatrix, clazz);
    }

    private void validateAllLinesHaveSameWidth(List<List<T>> rawMatrix) throws MatrixException {
        int length = rawMatrix.get(0).size();
        for(int i=0;i<rawMatrix.size();i++){
            List<T> line = rawMatrix.get(i);
            if(line.size() != length) throw new MatrixException(String.format("Line %d length %d, expected %d", i, line.size(), length));
        }
    }


}
