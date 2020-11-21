package util;

import graph.Vertex;

public class Util {

    private Util(){};

     public static double calculatePolarAngle(Vertex v1, Vertex v2){
        return calculatePolarAngle((int)v1.x(), (int)v1.y(), (int)v2.x(), (int)v2.y());
    }

     public static double calculatePolarAngle(int x1, int y1, int x2, int y2){
        return Math.atan2(y2 - y1, x2 - x1);
    }

}
