package graph;

import java.awt.*;

public class Vertex {

    static int Counter = 0;
    private final int id;
    private final Point point;
    private int x;
    private int y;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        point = new Point(x, y);
        id = ++Counter;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int id() {return id;}

    public void setX(int x) {
        this.x = x;
        point.x = x;
    }

    public void setY(int y) {
        this.y = y;
        point.y = y;
    }

    public Point point(){
        return point;
    }


    @Override
    public boolean equals(Object object) {
        if(object == this) return true;
        if(object == null) return false;
        Vertex v = (Vertex)object;
        return this.x() == v.x() && this.y() == v.y();
    }

    public void translate(Vertex v){
        if(v == null) return;
        point.setLocation(v.x(), v.y());
        this.setX(v.x());
        this.setY(v.y());
    }

    @Override
    public String toString() {
        return "Vertex id : "  + id;
    }




}
