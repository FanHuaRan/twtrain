package com.fhr.twtrain.core;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author FanHuaran
 * @description 本问题求解的入口点，输入文件是resource目录下的input.txt
 * @create 2018-06-03 10:44
 **/
public class App {

    public static void main(String[] args) {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("input.txt");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            // 遍历获取文件的每一行，对每一行单独求解
            String input = null;
            while ((input = bufferedReader.readLine()) != null) {
                handleInput(input);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  求解问题
     * @param input
     */
    private static void handleInput(String input) {
        // build the station graph.
        int[][] graphCost = buildGraph(input);

        // question 1 : The distance of the route A-B-C
        Integer answer1 = GraphAlgorithm.getPathWeight(graphCost, convertNodeNameToIndex(new char[]{'A', 'B', 'C'}));
        if (answer1 == null) {
            System.out.println("Output #1: NO SUCH ROUTE");
        } else {
            System.out.println("Output #1: " + answer1);
        }
        // question 2 : The distance of the route A-D.
        Integer answer2 = GraphAlgorithm.getPathWeight(graphCost, convertNodeNameToIndex(new char[]{'A', 'D'}));
        if (answer2 == null) {
            System.out.println("Output #2: NO SUCH ROUTE");
        } else {
            System.out.println("Output #2: " + answer2);
        }

        // question 3 : The distance of the route A-D-C.
        Integer answer3 = GraphAlgorithm.getPathWeight(graphCost, convertNodeNameToIndex(new char[]{'A', 'D', 'C'}));
        if (answer3 == null) {
            System.out.println("Output #3: NO SUCH ROUTE");
        } else {
            System.out.println("Output #3: " + answer3);
        }

        // question 4 : The distance of the route A-E-B-C-D..
        Integer answer4 = GraphAlgorithm.getPathWeight(graphCost, convertNodeNameToIndex(new char[]{'A', 'E', 'B', 'C', 'D'}));
        if (answer4 == null) {
            System.out.println("Output #4: NO SUCH ROUTE");
        } else {
            System.out.println("Output #4: " + answer4);
        }

        // question5 : The distance of the route A -E - D.
        Integer answer5 = GraphAlgorithm.getPathWeight(graphCost, convertNodeNameToIndex(new char[]{'A', 'E', 'D'}));
        if (answer5 == null) {
            System.out.println("Output #5: NO SUCH ROUTE");
        } else {
            System.out.println("Output #5: " + answer5);
        }

        // question6 : The number of trips starting at C and ending at C with a maximum of 3 stops
        List<List<Integer>> paths6 = GraphAlgorithm.getWithingMaxStopsPaths(graphCost, 'C' - 'A', 'C' - 'A', 3);
        System.out.println("Output #6: " + paths6.size());

        // question7 : The number of trips starting at A and ending at C with exactly 4 stops
        List<List<Integer>> paths7 = GraphAlgorithm.getEqualsStopsPaths(graphCost, 'A' - 'A', 'C' - 'A', 4);
        System.out.println("Output #7: " + paths7.size());

        // question8 : The length of the shortest route (in terms of distance to travel) from A to C
        Integer distance8 = GraphAlgorithm.shortPath(graphCost, 'A' - 'A', 'C' - 'A');
        if (distance8 != null) {
            System.out.println("Output #8: " + distance8);
        } else {
            System.out.println("Output #8: NO SUCH ROUTE");
        }

        // question9 : The length of the shortest route (in terms of distance to travel) from B to B
        Integer distance9 = GraphAlgorithm.shortPath(graphCost, 'B' - 'A', 'B' - 'A');
        if (distance9 != null) {
            System.out.println("Output #9: " + distance9);
        } else {
            System.out.println("Output #9: NO SUCH ROUTE");
        }

        // question10 : The number of different routes from C to C with a distance of less than 30
        List<List<Integer>> paths10 = GraphAlgorithm.getWithinMaxWeightPaths(graphCost, 'C' - 'A', 'C' - 'A', 30);
        System.out.println("Output #10: " + paths10.size());
    }

    /**
     * build the station graph
     *
     * @param input
     * @return
     */
    private static int[][] buildGraph(String input) {
        String[] graphInfos = input.split(", ");

        // get the node count
        int nodeCount = getStationCount(graphInfos);

        // init the graph
        int[][] graphCost = new int[nodeCount][nodeCount];
        for (int[] singleCost : graphCost) {
            Arrays.fill(singleCost, GraphAlgorithm.MAX_DIS);
        }

        // build graph
        for (String graphInfo : graphInfos) {
            if (graphInfo == null || graphInfo.isEmpty()) {
                throw new IllegalArgumentException("graph info is must not be null");
            }
            char start = Character.toUpperCase(graphInfo.charAt(0));
            char end = Character.toUpperCase(graphInfo.charAt(1));
            graphCost[start - 'A'][end - 'A'] = Integer.parseInt(graphInfo.substring(2));
        }

        return graphCost;
    }

    /**
     * get the stations's count (is must be 5?)
     * @param graphInfos
     * @return
     */
    private static int getStationCount(String[] graphInfos) {
        int max = -1;

        for (String graphInfo : graphInfos) {
            char start = Character.toUpperCase(graphInfo.charAt(0));
            char end = Character.toUpperCase(graphInfo.charAt(1));
            max = Math.max(start - 'A', max);
            max = Math.max(end - 'A', max);
        }
        return max + 1;
    }

    /**
     * convert NodeName to Index . NodeName is Char and index is int.
     * @param chars
     * @return
     */
    private static int[] convertNodeNameToIndex(char[] chars) {
        int[] newValues = new int[chars.length];
        for (int i = 0, n = chars.length; i < n; i++) {
            newValues[i] = chars[i] - 'A';
        }

        return newValues;
    }
}
