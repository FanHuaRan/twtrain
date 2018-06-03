package com.fhr.twtrain.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author FanHuaran
 * @description 关于图的核心算法组件
 * @create 2018-06-02 22:16
 **/
public class GraphAlgorithm {

    /**
     * 两点不可达时的距离常量，为了简便，这儿直接使用int的最大值
     */
    public static final int MAX_DIS = Integer.MAX_VALUE;


    /**
     * @param cost 邻接矩阵
     * @param path 路径
     * @return 距离和或者null(不可达 ）
     */
    public static Integer getPathWeight(int[][] cost, int[] path) {
        // param check
        if (cost == null || path == null || path.length < 2) {
            throw new IllegalArgumentException("cost and path must be valid");
        }

        // 按照路径记录权重和，如果中间有路径不可达，直接返回null
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

    /**
     * 求起点到终点的最短路径
     *
     * @param cost  邻接矩阵
     * @param start 起点
     * @param end   终点
     * @return 最短路径的距离或者null（路径不可达）
     */
    public static Integer shortPath(int[][] cost, int start, int end) {
        if (cost == null || start < 0 || start >= cost.length || end < 0 || end >= cost.length) {
            throw new IllegalArgumentException("param is illegal");
        }

        int nodeLen = cost.length;
        // paths存放最短路径
        List<List<Integer>> paths = new ArrayList<>();
        for (int i = 0; i < nodeLen; i++) {
            List<Integer> path = new ArrayList<>();
            path.add(start);
            paths.add(path);
        }

        // dists存放到每个节点的路径权重
        int[] dists = new int[nodeLen];
        for (int i = 0; i < nodeLen; i++) {
            dists[i] = cost[start][i];
        }

        // findFlags[i]表示i节点是否找到最短路径
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
                if (shouldUpdatePathByCurrentShort(cost, findFlags, dists, currentShort, i)) {
                    // 通过currentshort点更新该节点的权重和路径
                    dists[i] = dists[currentShort] + cost[currentShort][i];
                    paths.set(i, clonePath(paths.get(currentShort)));
                }
            }
        }

        //算法改进，考虑根本就没有路径到达的情况！
        return findFlags[end] ? dists[end] : null;
    }

    /**
     * 计算在经过的站最多为maxStops站的情况下，两点之间的所有路径
     *
     * @param cost     邻接矩阵
     * @param start    起点
     * @param end      终点
     * @param maxStops 经过的站
     * @return 满足情况的所有路径
     * @see #getWithingMaxStopsPathsCore(int[][], int, int, List, int, List)
     */
    public static List<List<Integer>> getWithingMaxStopsPaths(int[][] cost, int start, int end, int maxStops) {
        // step1 param check
        if (cost == null || start < 0 || start >= cost.length || end < 0 || end >= cost.length || maxStops <= 0) {
            throw new IllegalArgumentException("param is illegal");
        }

        // step2 param prepare
        List<List<Integer>> totalPaths = new ArrayList<>();
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(start);

        // step3 call core-method
        getWithingMaxStopsPathsCore(cost, start, end, currentPath, maxStops, totalPaths);

        return totalPaths;
    }

    /**
     * 计算在经过的距离最多为maxWeight的情况下，两点之间的所有路径
     *
     * @param cost      邻接矩阵
     * @param start     起点
     * @param end       终点
     * @param maxWeight 最大权重
     * @return 满足情况的所有路径
     * @see #getWithinMaxWeightPathsCore(int[][], int, int, List, int, int, List)
     */
    public static List<List<Integer>> getWithinMaxWeightPaths(int[][] cost, int start, int end, int maxWeight) {
        // step1 param check
        if (cost == null || start < 0 || start >= cost.length || end < 0 || end >= cost.length || maxWeight <= 0) {
            throw new IllegalArgumentException("param is illegal");
        }

        // step2 param prepare
        List<List<Integer>> totalPaths = new ArrayList<>();
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(start);

        // step3 call core-method
        getWithinMaxWeightPathsCore(cost, start, end, currentPath, 0, maxWeight, totalPaths);

        return totalPaths;
    }

    /**
     * 计算在经过的站刚好为equalsStops的情况下，两点之间的所有路径
     *
     * @param cost        邻接矩阵
     * @param start       起点
     * @param end         终点
     * @param equalsStops 经过的站数
     * @return 满足情况的所有路径
     * @see #getEqualsStopsPathsCore(int[][], int, int, List, int, List)
     */
    public static List<List<Integer>> getEqualsStopsPaths(int[][] cost, int start, int end, int equalsStops) {
        // step1 param check
        if (cost == null || start < 0 || start >= cost.length || end < 0 || end >= cost.length || equalsStops <= 0) {
            throw new IllegalArgumentException("param is illegal");
        }

        // step2 param prepare
        List<List<Integer>> totalPaths = new ArrayList<>();
        List<Integer> currentPath = new ArrayList<>();
        currentPath.add(start);

        // step3 call core-method
        getEqualsStopsPathsCore(cost, start, end, currentPath, equalsStops, totalPaths);

        return totalPaths;
    }

    /**
     * 计算在经过的站最多为maxStops站的情况下，两点之间的所有路径（核心方法，基于DFS+递归）
     *
     * @param cost
     * @param start
     * @param end
     * @param currentPath
     * @param maxStops
     * @param totalPaths
     */
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

    /**
     * 计算在经过的距离最多为maxWeight的情况下，两点之间的所有路径（核心方法，基于DFS+递归）
     *
     * @param cost
     * @param start
     * @param end
     * @param currentPath
     * @param currentWeight
     * @param maxWeight
     * @param totalPaths
     */
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

    /**
     * 计算在经过的站刚好为equalsStops的情况下，两点之间的所有路径（核心方法，基于DFS+递归）
     *
     * @param cost
     * @param start
     * @param end
     * @param currentPath
     * @param equalsStops
     * @param totalPaths
     */
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

    /**
     * 在当前尚未确定最短路径的顶点中找到一个与起点最近的节点
     *
     * @param nodeLen
     * @param dists
     * @param findFlags
     * @return
     */
    private static int searchCurrentMinNodeDist(int nodeLen, int[] dists, boolean[] findFlags) {
        int currentShort = -1;
        int currentMin = MAX_DIS;

        for (int i = 0; i < nodeLen; i++) {
            if (!findFlags[i] && currentMin > dists[i]) {
                currentMin = dists[i];
                currentShort = i;
            }
        }

        return currentShort;
    }


    /**
     * 判断是否可以通过currentShort点更新i点的路径
     *
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

    /**
     * 克隆路径
     *
     * @param path
     * @return
     */
    private static List<Integer> clonePath(List<Integer> path) {
        List<Integer> newPath = new ArrayList<>(path.size());
        for (int node : path) {
            newPath.add(node);
        }

        return newPath;
    }
}
