package com.permutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutation {
    int[] perm;
    List<int[]> permutations = new ArrayList<>();

    public Permutation(int n){
        perm = new int[n];
        for(int i=0;i<n;i++){
            perm[i] = i+1;
        }
//        permutations.add(copy());
        printOne();
    }

    public void permute(int start, int end){
        if(start >= end) return;
        int[] range = Arrays.copyOfRange(perm, start, end+1);
        for(int currentValue : range){
            if(swapNext(start, end, currentValue))
//                permutations.add(copy());
                printOne();
            permute(start+1, end);
        }
    }

    private boolean swapNext(int start, int end, int valueToSwap){
        if(perm[start] == valueToSwap) return false;
        for(int i=start;i<=end;i++){
            if(perm[i] == valueToSwap){
                swap(start, i);
                break;
            }
        }
        return true;
    }

    public int getLength(){
        return perm.length;
    }

    private void swap(int i, int j){
        if(i==j) return;
        int temp = perm[i];
        perm[i] = perm[j];
        perm[j] = temp;
    }

    private int[] copy(){
        return Arrays.copyOf(perm, perm.length);
    }

    public void print(){
        permutations.stream().forEach(it -> {
            System.out.println(Arrays.toString(it));
        });
    }

    public void printOne(){
        System.out.println(Arrays.toString(perm));
    }
}
