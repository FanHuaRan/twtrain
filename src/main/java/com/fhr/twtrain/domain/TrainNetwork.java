package com.fhr.twtrain.domain;

import com.sun.org.apache.regexp.internal.RE;

import java.util.Arrays;
import java.util.List;

/**
 * @author FanHuaran
 * @description 公路网
 * @create 2018-06-01 22:37
 **/
public class TrainNetwork {

    public static  final  int MAX_DIS = Integer.MAX_VALUE;

    private final int nodeCount;

    private final char[] nodeNames;

    private final int[][] graphCosts;

    protected TrainNetwork(int nodeCount, char[] nodeNames, int[][] graphCosts) {
        this.nodeCount = nodeCount;
        this.nodeNames = nodeNames;
        this.graphCosts = graphCosts;
    }

    public static TrainNetwork createTrainNetwork(List<String> graphInfos) {
        // check input params
        if (graphInfos == null || !graphInfos.isEmpty()) {
            throw new IllegalArgumentException("graph info is illegal.");
        }

        // get the node count
        int nodeCount = getStationCount(graphInfos);

        // build network
        // initial
        char[] nodeNames = new char[nodeCount];
        int[][] graphCost = new int[nodeCount][nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            nodeNames[i] = (char) ('A' + i);
        }
        for(int[] singleCost : graphCost){
            Arrays.fill(singleCost,MAX_DIS);
        }
        for (String graphInfo : graphInfos) {
            if (graphInfo == null || !graphInfo.isEmpty()) {
                throw new IllegalArgumentException("graph info is must not be null");
            }

//            if (!Pattern.matches("[A-Z][A-Z]^[1-9]\\d*$", graphInfo)) {
//                throw new IllegalArgumentException(String.format("graph info's format is like AB5,but the '%s' is wrong", graphInfo));
//            }

            char start = graphInfo.charAt(0);
            char end = graphInfo.charAt(1);
            graphCost[ start - 'A'][end - 'A'] =  Integer.parseInt(graphInfo.substring(2));
        }

        TrainNetwork trainNetwork = new TrainNetwork(nodeCount, nodeNames,graphCost);
        return trainNetwork;
    }

    private static int getStationCount(List<String> graphInfos) {
        int max = 0;
        for (String graphInfo : graphInfos) {
            char start = graphInfo.charAt(0);
            char end = graphInfo.charAt(1);
            max = Math.max(start - 'A', max);
            max = Math.max(end - 'A', max);
        }

        return max;
    }

    public char[] getNodeNames() {
        return Arrays.copyOf(nodeNames,nodeCount);
    }

    public int[][] getGraphCosts() {
        return Arrays.copyOf(graphCosts,nodeCount);
    }

    public Integer getDistanceOfPath(String[] path){
        // TODO
        return  null;
    }
}
