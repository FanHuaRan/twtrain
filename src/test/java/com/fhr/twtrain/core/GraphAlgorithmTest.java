package com.fhr.twtrain.core;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 *
 */
public class GraphAlgorithmTest {

    public static final int[][] cost = {
            {0X7FFFFFFF, 5, 0X7FFFFFFF, 5, 7},
            {0X7FFFFFFF, 0X7FFFFFFF, 4, 0X7FFFFFFF, 0X7FFFFFFF},
            {0X7FFFFFFF, 0X7FFFFFFF, 0X7FFFFFFF, 8, 2},
            {0X7FFFFFFF, 0X7FFFFFFF, 8, 0X7FFFFFFF, 6},
            {0X7FFFFFFF, 3, 0X7FFFFFFF, 0X7FFFFFFF, 0X7FFFFFFF},
    };

    /**
     * @see GraphAlgorithm#shortPath(int[][], int, int)
     */
    @Test
    public void shortPath() {
        Integer result1 = GraphAlgorithm.shortPath(cost, 0, 2);
        Assert.assertEquals(9, (int) result1);

        Integer result2 = GraphAlgorithm.shortPath(cost, 1, 1);
        Assert.assertEquals(9, (int) result2);

        try {
            GraphAlgorithm.shortPath(null, 1, 1);
            Assert.fail("cost is null must throw IllegalArgumentException ");
        } catch (IllegalArgumentException er) {
        }

        try {
            GraphAlgorithm.shortPath(cost, -1, 1);
            Assert.fail("start|| end  is wrong must throw IllegalArgumentException ");
        } catch (IllegalArgumentException er) {
        }
    }

    /**
     * @see GraphAlgorithm#getPathWeight(int[][], int[])
     */
    @Test
    public void getPathWeight() {
        Assert.assertEquals(9, (int) GraphAlgorithm.getPathWeight(cost, new int[]{0, 1, 2}));
        Assert.assertEquals(5, (int) GraphAlgorithm.getPathWeight(cost, new int[]{0, 3}));

        Assert.assertTrue(null == GraphAlgorithm.getPathWeight(cost, new int[]{0, 4, 3}));

        try {
            GraphAlgorithm.getPathWeight(null, new int[]{0, 1, 3});
            Assert.fail("cost is null must throw IllegalArgumentException");
        } catch (IllegalArgumentException er) {
        }

        try {
            GraphAlgorithm.getPathWeight(cost, null);
            Assert.fail("path is null must throw IllegalArgumentException");
        } catch (IllegalArgumentException er) {
        }
    }

    /**
     * @see GraphAlgorithm#getWithingMaxStopsPaths(int[][], int, int, int)
     */
    @Test
    public void getWithingMaxStopsPaths() {
        List<List<Integer>> paths1 = GraphAlgorithm.getWithingMaxStopsPaths(cost, 2, 2, 3);
        Assert.assertEquals(2, paths1.size());

        List<List<Integer>> paths2 = GraphAlgorithm.getWithingMaxStopsPaths(cost, 0, 1, 2);
        Assert.assertEquals(2, paths2.size());

        try {
            GraphAlgorithm.getWithingMaxStopsPaths(null, 0, 1, 2);
            Assert.fail("cost is null must throw IllegalArgumentException");
        } catch (IllegalArgumentException er) {
        }

        try {
            GraphAlgorithm.getWithingMaxStopsPaths(cost, -1, 1, 2);
            Assert.fail("start or end or maxStops is wrong must throw IllegalArgumentException ");
        } catch (IllegalArgumentException er) {
        }
    }

    /**
     * @see GraphAlgorithm#getWithinMaxWeightPaths(int[][], int, int, int)
     */
    @Test
    public void getWithinMaxWeightPaths() {
        List<List<Integer>> paths1 = GraphAlgorithm.getWithinMaxWeightPaths(cost, 2, 2, 30);
        Assert.assertEquals(7, paths1.size());

        List<List<Integer>> paths2 = GraphAlgorithm.getWithinMaxWeightPaths(cost, 0, 1, 12);
        Assert.assertEquals(2, paths2.size());

        try {
            GraphAlgorithm.getWithinMaxWeightPaths(null, 0, 1, 20);
            Assert.fail("cost is null must throw IllegalArgumentException");
        } catch (IllegalArgumentException er) {
        }

        try {
            GraphAlgorithm.getWithinMaxWeightPaths(cost, -1, 1, 2);
            Assert.fail("start or end or maxWeight is wrong must throw IllegalArgumentException ");
        } catch (IllegalArgumentException er) {
        }
    }

    /**
     * @see GraphAlgorithm#getEqualsStopsPaths(int[][], int, int, int)
     */
    @Test
    public void getEqualsStopsPaths() {
        List<List<Integer>> paths1 = GraphAlgorithm.getEqualsStopsPaths(cost, 0, 2, 4);
        Assert.assertEquals(3, paths1.size());

        List<List<Integer>> paths2 = GraphAlgorithm.getEqualsStopsPaths(cost, 0, 4, 2);
        Assert.assertEquals(1, paths2.size());

        try {
            GraphAlgorithm.getEqualsStopsPaths(null, 0, 1, 2);
            Assert.fail("cost is null must throw IllegalArgumentException");
        } catch (IllegalArgumentException er) {
        }

        try {
            GraphAlgorithm.getEqualsStopsPaths(cost, -1, 1, 2);
            Assert.fail("start or end or equalsStops is wrong must throw IllegalArgumentException ");
        } catch (IllegalArgumentException er) {
        }
    }
}