package com.fhr.twtrain.core;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GraphAlgorithmTest {

    public int[][] cost = {
            {0X7FFFFFFF, 5, 0X7FFFFFFF, 5, 7},
            {0X7FFFFFFF, 0X7FFFFFFF, 4, 0X7FFFFFFF, 0X7FFFFFFF},
            {0X7FFFFFFF, 0X7FFFFFFF, 0X7FFFFFFF, 8, 2},
            {0X7FFFFFFF, 0X7FFFFFFF, 8, 0X7FFFFFFF, 6},
            {0X7FFFFFFF, 3, 0X7FFFFFFF, 0X7FFFFFFF, 0X7FFFFFFF},
    };


    @Test
    public void shortPath() {
        List<Integer> shortPath = GraphAlgorithm.shortPath(cost, 0, 2);

    }

    @Test
    public void getPathWeight() {
        Assert.assertEquals(9,(int)GraphAlgorithm.getPathWeight(cost, new int[]{0, 1, 2}));
        Assert.assertEquals(5,(int)GraphAlgorithm.getPathWeight(cost, new int[]{0, 3}));

        Assert.assertTrue(null == GraphAlgorithm.getPathWeight(cost, new int[]{0, 4, 3}));

    }

    @Test
    public void getWithingMaxStopsPaths() {
        List<List<Integer>> paths = GraphAlgorithm.getWithingMaxStopsPaths(cost, 2, 2, 3);
        Assert.assertEquals(2, paths.size());
    }

    @Test
    public void getWithinMaxWeightPaths() {
        List<List<Integer>> paths = GraphAlgorithm.getWithinMaxWeightPaths(cost, 2, 2, 30);
        Assert.assertEquals(7, paths.size());
    }

    @Test
    public void getEqualsStopsPaths() {
        List<List<Integer>> paths = GraphAlgorithm.getEqualsStopsPaths(cost, 0, 2, 4);
        Assert.assertEquals(3, paths.size());
    }
}