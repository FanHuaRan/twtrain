package com.fhr.twtrain.service;

import com.fhr.twtrain.domain.TrainNetwork;

import java.util.Arrays;
import java.util.List;

/**
 * @author FanHuaran
 * @description
 * @create 2018-06-01 23:33
 **/
public class TrainRouteServiceImpl implements TrainRouteService {

    @Override
    public List<String> compute(List<String> input) {
        TrainNetwork trainNetwork = null;
        try {
             trainNetwork = TrainNetwork.createTrainNetwork(input);
        }catch (Exception er){
            return Arrays.asList(er.getMessage());
        }
        /*
        The distance of the route A-B-C.
        The distance of the route A-D.
        The distance of the route A-D-C.
        The distance of the route A-E-B-C-D.
        The distance of the route A-E-D.
        The number of trips starting at C and ending at C with a maximum of 3 stops.  In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
        The number of trips starting at A and ending at C with exactly 4 stops.  In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
        The length of the shortest route (in terms of distance to travel) from A to C.
        The length of the shortest route (in terms of distance to travel) from B to B.
        The number of different routes from C to C with a distance of less than 30.  In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.*/

        /***
         * 1-5问题，直接调用TrainNetwork.getDistanceOfPath即可解决
         * 第6个问题使用图的广度遍历，遍历层最多3层，必要时可参考第10个问题
         * 第7个问题使用图的广度遍历，遍历层最多4层，必要时可参考第10个问题
         * 第8个问题和第9个问题使用迪杰斯特拉最短路径算法
         * 第10个问题实际上是求图的两点的所有路径，参考：https://blog.csdn.net/xqhadoop/article/details/66476728
         */

        return null;
    }
}
