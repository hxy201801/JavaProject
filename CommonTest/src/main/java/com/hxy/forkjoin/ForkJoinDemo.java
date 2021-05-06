package com.hxy.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 就是分而治之的方法。适合二叉树，矩阵降维分解等。
 * @author huangxy8
 *
 */
public class ForkJoinDemo {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ForkJoinPool fjp = new ForkJoinPool();
        double[] nums = new double[1000000];
        for(int i = 0; i < nums.length; ++i) {
            nums[i] = (double)i;
        }

        System.out.println("A portion of the original sequence:");
        for(int i = 0; i < 10; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println("\n");

        System.out.println("Start time: " + System.currentTimeMillis());
        SqrtTransform task = new SqrtTransform(nums, 0, nums.length);
        fjp.invoke(task);
        //task.invoke();
        //for (int i = 0; i < nums.length; ++i) {
        //	nums[i] = Math.sqrt(nums[i]);
        //}


        System.out.println("End   time: " + System.currentTimeMillis());
        System.out.println("A portion of transfromed sequence is: ");

        for(int i = 0; i < 10; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println("\n");
    }

}

class SqrtTransform extends RecursiveAction {

    private final int seqThreadhold = 1000;
    private double[] data;
    private int start, end;

    public SqrtTransform(double[] vals, int s, int e) {
        data = vals;
        start = s;
        end = e;
    }

    @Override
    protected void compute() {
        // TODO Auto-generated method stub
        if ((end - start) < seqThreadhold) {
            for (int i = start; i < end; ++i) {
                data[i] = Math.sqrt(data[i]);
            }
        } else {
            int middle = (start + end) / 2;
            invokeAll(new SqrtTransform(data, start,middle),
                    new SqrtTransform(data, middle, end));
        }
    }

}
