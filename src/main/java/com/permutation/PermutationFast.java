package com.permutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermutationFast {
    int n;
    int [] perm;
    //    List<int[]> templates = new ArrayList<>();
    int[] jValues;
    int currentIValue=0;

    public PermutationFast(int n){
        this.n = n;
        perm = new int[n];
        for(int i=0;i<n;i++){
            perm[i] = 0;
        }
        jValues =  createJValuesArray();
    }

    private int[] createJValuesArray(){
        int[] arr = new int[n];
        for(int i=0;i<n;i++){
            arr[i] = 0;
        }
        return arr;
    }

    public int [] next(){
        boolean changed = false;
        for(int i=currentIValue;i>=0 && i<n;){
            for (int j=jValues[i];j<n;j++) {
                jValues[i]++;
                if (!checkArrayContainsValue(jValues[i], i)) {
                    perm[i] = jValues[i];
                    changed = true;
                    break;
                }
            }
            if(changed){
                if(i==n-1){
                    resetJValues(i);
//                    changed=false;
                    i--;
                    currentIValue = i;
//                    templates.add(copy());
                    return copy();
//                    continue;
                }
                i++;
                currentIValue = i;
            } else {
                resetJValues(i);
                i--;
                currentIValue = i;
                continue;
            }
        }
        return null;
    }

    private void resetJValues(int startIndex){
        for(int i=startIndex;i<n;i++){
            jValues[i] = 0;
        }
    }

    private boolean checkArrayContainsValue(int value, int limit){
        for(int i=0;i<limit;i++){
            if(perm[i] == value) return true;
        }
        return false;
    }

    public void print(int [] array){
        System.out.println(Arrays.toString(array));
    }

    public void print(int [] array, int sign){
        System.out.println(Arrays.toString(array) + ",(" + sign + ")" );
    }

    private int [] copy(){
        return Arrays.copyOf(perm, perm.length);
    }

    public static Integer getSign(int [] permutation){
        int numberOfInversions=0;
        int size = permutation.length;
        for(int i=0;i<size;i++){
            for(int j=i+1;j<size;j++){
                if(permutation[i]>permutation[j]) numberOfInversions++;
            }
        }
        return (int)Math.pow(-1d, numberOfInversions);
    }
}
