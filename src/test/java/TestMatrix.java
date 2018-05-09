import com.error.MatrixException;
import com.matrix.Matrix;
import com.matrix.MatrixReader;
import org.junit.jupiter.api.Test;

public class TestMatrix {
    @Test
    public void testMatrix() throws MatrixException {
        MatrixReader<Double> reader = new MatrixReader<>(Double.class);
        Matrix<Double> matrix = reader.readMatrix("testMatrix.txt");
        matrix.print();
//        matrix.transpose().print();
        System.out.println(matrix.determinant());
        System.out.println();
//        System.out.println(matrix.transpose().determinant());
//        matrix.adjoint().print();
//        matrix.inverse().print();
        matrix.prod(matrix.inverse()).print();
//        matrix.inverse().prod(matrix).print();
//        System.out.println();
//        Matrix<Double> filter = reader.readMatrix("testFilter.txt");
//        Matrix<Double> updatedMatrix = matrix.filter(filter);
//        updatedMatrix.print();
//        System.out.println(updatedMatrix.determinant());
    }
}
