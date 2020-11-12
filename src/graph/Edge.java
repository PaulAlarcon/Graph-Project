package graph;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Edge extends Line2D {

    private Vertex v1;
    private Vertex v2;

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null) return false;
        if(!(obj instanceof Edge))
            return false;
        return equals((Edge)obj);
    }

    public Edge(Vertex v1, Vertex v2){
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public double getX1() {
        return v1.getX();
    }

    @Override
    public double getY1() {
        return v1.getY();
    }

    @Override
    public Vertex getP1() {
        return v1;
    }

    @Override
    public double getX2() {
        return v2.getX();
    }

    @Override
    public double getY2() {
        return v2.getY();
    }

    @Override
    public Vertex getP2() {
        return v2;
    }

    @Override
    public void setLine(double x1, double y1, double x2, double y2) {}

    public String toString() {
        return "graph.Edge{" +
                "v1=" + v1 +
                ", v2=" + v2 +
                '}';
    }

    public boolean equals(Edge e){
        return (this.getP1().equals(e.getP1()) && this.getP2().equals(e.getP2()))
                || (this.getP1().equals(e.getP2()) && this.getP2().equals(e.getP1()));
    }

    public boolean contains(Vertex v) {
        return v1.equals(v) || v2.equals(v);
    }

    @Override
    public int hashCode() {
        return getP1().hashCode() + getP2().hashCode();
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }
}