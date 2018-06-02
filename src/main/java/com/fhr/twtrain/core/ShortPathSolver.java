package com.fhr.twtrain.core;

import java.util.ArrayList;
import java.util.List;
import  static com.fhr.twtrain.core.GraphConstant.MAX_DIS;
/**
 * @author FanHuaran
 * @description
 * @create 2018-06-02 18:08
 **/
public class ShortPathSolver {

    /**
     * 求起点到终点的最短路径
     *
     * @param cost
     * @param start
     * @param end
     * @return
     */
    public List<Integer> shortPath(int[][] cost, int start, int end) {
        if (cost == null || start < 0 || start >= cost.length || end < 0 || end > cost.length) {
            throw new IllegalArgumentException("param is illegal");
        }
        int nodeLen = cost.length;
        // 存放最短路径
        List<List<Integer>> paths = new ArrayList<>();
        for (int i = 0; i < nodeLen; i++) {
            paths.add(new ArrayList<>());
        }
        // 存放到每个节点的路径权重
        int[] dists = new int[nodeLen];
        for (int i = 0; i < nodeLen; i++) {
            dists[i] = cost[start][i];
        }
        // 是否找到最短路径的标志位
        boolean[] findFlag = new boolean[nodeLen];
        for (int i = 0; i < nodeLen; i++) {
            findFlag[i] = false;
        }
        // 起点到起点已经找到最短路径
        findFlag[start] = true;
        // 循环n-1次
        for (int count = 1; count < nodeLen; count++) {
            // 在当前尚未确定最短路径的顶点中找到一个与起点最近的节点
            int currentShort = searchCurrentMinNodeDist(nodeLen, dists, findFlag);
            // 未找到，这种情况发生是因为出现了最后未找到最短路径的顶点，与起始点根本就不联通！
            if (currentShort < 0) {
                break;
            }
            // 设置标志位为已经找到
            findFlag[currentShort] = true;
            // 添加该节点到该节点的最短路径中
            paths.get(currentShort).add(start);
            // 该点为终点 直接退出
            if (currentShort == end) {
                break;
            }
            // 遍历该节点之间联通的尚未找到最短路径的结点
            for (int i = 0; i < nodeLen; i++) {
                if (!findFlag[i] && cost[start][i] != MAX_DIS) {
                    // 如果通过该节点到其联通节点更近
                    if (dists[start] + cost[start][i] < dists[i]) {
                        // 复制路径
                        copyTo(paths.get(start), paths.get(i));
                    }
                }
            }
        }
        //算法改进，考虑根本就没有路径到达的情况！
        return findFlag[start] ? paths.get(end) : new ArrayList<>(0);
    }

    /**
     * 求起点到所有点的最短路径
     *
     * @param cost
     * @param start
     * @return
     */
    public List<List<Integer>> shortPath(int[][] cost, int start) {
        if (cost == null || start < 0 || start >= cost.length) {
            return null;
        }
        int nodeLen = cost.length;
        // 存放最短路径
        List<List<Integer>> paths = new ArrayList<>();
        for (int i = 0; i < nodeLen; i++) {
            paths.add(new ArrayList<>());
        }
        // 存放到每个节点的路径权重
        int[] dists = new int[nodeLen];
        for (int i = 0; i < nodeLen; i++) {
            dists[i] = cost[start][i];
        }
        // 是否找到最短路径的标志位
        boolean[] findFlag = new boolean[nodeLen];
        for (int i = 0; i < nodeLen; i++) {
            findFlag[i] = false;
        }
        // 起点到起点已经找到最短路径
        findFlag[start] = true;
        // 循环n-1次
        for (int count = 1; count < nodeLen; count++) {
            // 在当前尚未确定最短路径的顶点中找到一个与起点最近的节点
            int currentShort = searchCurrentMinNodeDist(nodeLen, dists, findFlag);
            // 未找到，这种情况发生是出现了最后未找到最短路径的顶点与起始点根本就不联通
            if (currentShort < 0) {
                break;
            }
            // 设置标志位为已经找到
            findFlag[currentShort] = true;
            // 添加该节点到该节点的最短路径中
            paths.get(currentShort).add(start);
            // 遍历该节点之间联通的尚未找到最短路径的结点
            for (int i = 0; i < nodeLen; i++) {
                if (!findFlag[i] && cost[start][i] != MAX_DIS) {
                    // 如果通过该节点到其联通节点更近
                    if (dists[start] + cost[start][i] < dists[i]) {
                        // 复制路径
                        copyTo(paths.get(start), paths.get(i));
                    }
                }
            }
        }
        return paths;
    }

    // 在当前尚未确定最短路径的顶点中找到一个与起点最近的节点
    private int searchCurrentMinNodeDist(int nodeLen, int[] dists, boolean[] findFlag) {
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

    // 将des数据元素复制且覆盖到src中
    private void copyTo(List<Integer> src, List<Integer> des) {
        int desLen = des.size();
        for (int i = 0; i < desLen; i++) {
            des.set(i, src.get(i));
        }
        int srcLen = src.size();
        for (int i = desLen - 1; i < srcLen; i++)
            des.add(src.get(i));
    }
}
