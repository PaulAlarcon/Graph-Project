package graph;

import java.awt.*;

public class Vertex extends Point{

    private double x;
    private double y;

    public Vertex(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }


    @Override
    public boolean equals(Object object) {
        if(object == this) return true;
        if(object == null) return false;
        Vertex v = (Vertex)object;
        return this.getX() == v.getX() && this.getY() == v.getY();
    }

    public void translate(Vertex v){
        if(v == null) return;
        this.setX(v.getX());
        this.setY(v.getY());
    }

    @Override
    public String toString() {
        return "graph.Vertex{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }




}
