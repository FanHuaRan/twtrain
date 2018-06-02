package com.fhr.twtrain.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * @author FanHuaran
 * @description
 * @create 2018-06-02 17:08
 **/
public class AllPathSolver {

    public static final int MAX_DIS = Integer.MAX_VALUE;

    public  List<List<Integer>> getAllPaths(int[][] cost, int start, int end) {
        if (cost == null || start < 0 || start >= cost.length || end < 0 || end > cost.length) {
            throw new IllegalArgumentException("param is illegal");
        }

        //
        int nodeLen = cost.length;

        boolean[] inStack = new boolean[nodeLen];
        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        boolean isStart = true;
        List<List<Integer>> paths = new ArrayList<>();
        inStack[start] = true;

        int lastVisitNeiborNode = -1;

        while (!stack.isEmpty()) {
            int node = stack.peek();

            if (!isStart && node == end) {
                paths.add(clonePath(stack));
                lastVisitNeiborNode = stack.pop();
                inStack[node] = false;
                continue;
            }

            int nextNode = getNextNode(cost, inStack, nodeLen, node, lastVisitNeiborNode);
            if (nextNode == -1) {
                lastVisitNeiborNode = stack.pop();
                inStack[node] = false;
            } else {
                stack.push(nextNode);
                inStack[nextNode] = true;
                // 邻接点重置
                lastVisitNeiborNode = -1;
            }

            isStart = false;
        }

        return paths;

    }

    private  int getNextNode(int[][] cost, boolean[] inStack, int nodeLen, int node, int lastVisit) {
        for (int i = lastVisit + 1; i < nodeLen; i++) {
            if (cost[node][i] != MAX_DIS && !inStack[i]) {
                return i;
            }
        }

        return -1;
    }

    private  List<Integer> clonePath(List<Integer> path) {
        List<Integer> newPath = new ArrayList<>(path.size());
        Collections.copy(newPath, path);
        return newPath;
    }


}
