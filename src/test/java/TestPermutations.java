import com.permutation.Permutation;
import com.permutation.PermutationFast;
import org.junit.jupiter.api.Test;

public class TestPermutations {
    @Test
    public void testPerm(){
        Permutation permutation = new Permutation(5);
        permutation.permute(0, permutation.getLength()-1);
        permutation.print();
    }

    @Test
    public void testPerm2(){
        PermutationFast permutation = new PermutationFast(2);
        int[] perm;
        while((perm = permutation.next())!=null){
            permutation.print(perm, PermutationFast.getSign(perm));
        }
    }
}
