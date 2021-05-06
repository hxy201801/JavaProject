package com.hxy.array;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Thread)
public class ArrayOptimizeTest {

    private static final int maxSize = 1000; // 测试循环次数
    private static final int operationSize = 100; // 操作次数

    private static ArrayList<Integer> arrayList;
    private static LinkedList<Integer> linkedList;

    public static void main(String[] args) throws RunnerException {
        // 启动基准测试
        Options opt = new OptionsBuilder().include(ArrayOptimizeTest.class.getSimpleName()).build();
        new Runner(opt).run();
    }

    @Setup
    public void init() {
        // 启动执⾏事件
        arrayList = new ArrayList<Integer>();
        linkedList = new LinkedList<Integer>();
        for(int i = 0; i < maxSize; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
    }

    @Benchmark
    public void addArrayByFirst(Blackhole blackhole) {
        for(int i = 0; i < operationSize; i++) {
            arrayList.add(i, i);
        }
        // 为了避免 JIT 忽略未被使⽤的结果计算
        blackhole.consume(arrayList);
    }

    @Benchmark
    public void addLinkedByFirst(Blackhole blackhole) {
        for(int i =0; i < operationSize; i++) {
            linkedList.add(i, i);
        }
        blackhole.consume(linkedList);
    }
}
