package com.fhr.twtrain.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author FanHuaran
 * @description
 * @create 2018-06-02 22:16
 **/
public class GraphAlgorithm {

    public static final int MAX_DIS = Integer.MAX_VALUE;

    /**
     * 求起点到终点的最短路径
     *
     * @param cost
     * @param start
     * @param end
     * @return
     */
    public static List<Integer> shortPath(int[][] cost, int start, int end) {
        if (cost == null || start < 0 || start >= cost.length || end < 0 || end > cost.length) {
            throw new IllegalArgumentException("param is illegal");
        }

        int nodeLen = cost.length;
        // 存放最短路径
        List<List<Integer>> paths = new ArrayList<>();
        for (int i = 0; i < nodeLen; i++) {
            List<Integer> path = new ArrayList<>();
            path.add(start);
            paths.add(path);
        }

        // 存放到每个节点的路径权重
        int[] dists = new int[nodeLen];
        for (int i = 0; i < nodeLen; i++) {
            dists[i] = cost[start][i];
        }

        // 是否找到最短路径的标志位
        boolean[] findFlags = new boolean[nodeLen];
        Arrays.fill(findFlags, false);

        // 循环n次
        for (int count = 0; count < nodeLen; count++) {
            // 在当前尚未确定最短路径的顶点中找到一个与起点最近的节点
            int currentShort = searchCurrentMinNodeDist(nodeLen, dists, findFlags);
            // 未找到，这种情况发生是因为出现了最后未找到最短路径的顶点，与起始点根本就不联通！
            if (currentShort < 0) {
                break;
            }
            // 设置标志位为已经找到
            findFlags[currentShort] = true;
            // 添加该节点到该节点的最短路径中
            paths.get(currentShort).add(currentShort);
            // 该点为终点 直接退出
            if (currentShort == end) {
                break;
            }

            // 遍历该节点之间联通的尚未找到最短路径的结点
            for (int i = 0; i < nodeLen; i++) {
                // 判断是否可以通过currentShort点更新i点的路径
                if (shouldUpdatePathByCurrentShort(cost,findFlags,dists,currentShort,i)) {
                    // 通过currentshort点更新该节点的权重和路径
                    dists[i] = dists[currentShort] + cost[currentShort][i];
                    paths.set(i, clonePath(paths.get(currentShort)));
                }
            }

        }
        //算法改进，考虑根本就没有路径到达的情况！
        return findFlags[end] ? paths.get(end) : new ArrayList<>(0);
    }

    public static Integer getPathWeight(int[][] cost, int[] path) {
        int totalWeight = 0;
        for (int i = 1, n = path.length; i < n; i++) {
            int weight = cost[path[i - 1]][path[i]];
            if (weight == MAX_DIS) {
                return null;
            } else {
                totalWeight += weight;
            }
        }
        return totalWeight;
    }

    public static List<List<Integer>> getWithingMaxStopsPaths(int[][] cost, int start, int end, int maxStops) {
        List<List<Integer>> totalPaths = new ArrayList<>();
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(start);
        getWithingMaxStopsPathsCore(cost, start, end, currentPath, maxStops, totalPaths);

        return totalPaths;
    }

    public static List<List<Integer>> getWithinMaxWeightPaths(int[][] cost, int start, int end, int maxWeight) {
        List<List<Integer>> totalPaths = new ArrayList<>();
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(start);

        getWithinMaxWeightPathsCore(cost, start, end, currentPath, 0, maxWeight, totalPaths);

        return totalPaths;
    }

    public static List<List<Integer>> getEqualsStopsPaths(int[][] cost, int start, int end, int equalsStops) {
        List<List<Integer>> totalPaths = new ArrayList<>();
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(start);

        getEqualsStopsPathsCore(cost, start, end, currentPath, equalsStops, totalPaths);

        return totalPaths;
    }

    private static void getWithingMaxStopsPathsCore(int[][] cost, int start, int end, List<Integer> currentPath, int maxStops, List<List<Integer>> totalPaths) {
        if (currentPath.size() > 1 && start == end) {
            totalPaths.add(clonePath(currentPath));
        }

        if ((currentPath.size() - 1) >= maxStops) {
            return;
        }

        for (int i = 0; i < cost[start].length; i++) {
            if (cost[start][i] != MAX_DIS) {
                currentPath.add(i);
                getWithingMaxStopsPathsCore(cost, i, end, currentPath, maxStops, totalPaths);
                currentPath.remove(currentPath.size() - 1);
            }
        }

    }


    private static void getWithinMaxWeightPathsCore(int[][] cost, int start, int end, List<Integer> currentPath, int currentWeight, int maxWeight, List<List<Integer>> totalPaths) {
        if (currentWeight > 0 && currentWeight < maxWeight && start == end) {
            totalPaths.add(clonePath(currentPath));
        }

        if (currentWeight >= maxWeight) {
            return;
        }

        for (int i = 0; i < cost[start].length; i++) {
            if (cost[start][i] != MAX_DIS) {
                currentPath.add(i);
                currentWeight += cost[start][i];
                getWithinMaxWeightPathsCore(cost, i, end, currentPath, currentWeight, maxWeight, totalPaths);
                currentWeight -= cost[start][i];
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }

    private static void getEqualsStopsPathsCore(int[][] cost, int start, int end, List<Integer> currentPath, int equalsStops, List<List<Integer>> totalPaths) {
        if ((currentPath.size() - 1) == equalsStops && start == end) {
            totalPaths.add(clonePath(currentPath));
        }

        if ((currentPath.size() - 1) >= equalsStops) {
            return;
        }

        for (int i = 0; i < cost[start].length; i++) {
            if (cost[start][i] != MAX_DIS) {
                currentPath.add(i);
                getEqualsStopsPathsCore(cost, i, end, currentPath, equalsStops, totalPaths);
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }

    // 在当前尚未确定最短路径的顶点中找到一个与起点最近的节点
    private static int searchCurrentMinNodeDist(int nodeLen, int[] dists, boolean[] findFlag) {
        int currentShort = -1;
        int currentMin = MAX_DIS;
        for (int i = 0; i < nodeLen; i++) {
            if (!findFlag[i] && currentMin > dists[i]) {
                currentMin = dists[i];
                currentShort = i;
            }
        }
        return currentShort;
    }


    /**
     * 判断是否可以通过currentShort点更新i点的路径
     * @param cost
     * @param findFlags
     * @param dists
     * @param currentShort
     * @param i
     * @return
     */
    private static boolean shouldUpdatePathByCurrentShort(int[][] cost, boolean[] findFlags, int[] dists, int currentShort, int i) {
        return !findFlags[i] && // i点尚未找到最短路径
                cost[currentShort][i] != MAX_DIS && // currentShort直接可达i
                dists[currentShort] + cost[currentShort][i] < dists[i];  // 当前通过currentShort到i的距离更近
    }

    private static List<Integer> clonePath(List<Integer> path) {
        List<Integer> newPath = new ArrayList<>(path.size());
        for (int node : path) {
            newPath.add(node);
        }
        return newPath;
    }
}
