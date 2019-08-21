import java.awt.*;
import java.util.ArrayList;

public class Vertex extends Point {

    double x;
    double y;
    boolean visited;

    Vertex camefrom;

    Vertex(double x, double y){
        this.x = x;
        this.y = y;
        visited = false;
        camefrom = null;
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

    public boolean equals(Vertex v){
        return v.getX() == this.getX() && v.getY() == this.getY();
    }

    public void translate(Vertex v){
        this.setX(v.getX());
        this.setY(v.getY());
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public boolean isVisited() {
        return visited;
    }
}
