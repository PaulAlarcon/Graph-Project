package graph;

import javax.sound.sampled.Line;
import java.awt.geom.Line2D;

public class Edge{

    private Vertex v1;
    private Vertex v2;
    private Line2D line;

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
        line = new Line2D.Double(x1(), y1(), x2(), y2());
    }

    public Line2D line(){return line;}

    public int x1() {
        return v1.x();
    }

    public int y1() {
        return v1.y();
    }

    public Vertex v1() {
        return v1;
    }

    public int x2() {
        return v2.x();
    }

    public int y2() {
        return v2.y();
    }

    public Vertex v2() {
        return v2;
    }

    public String toString() {
        return "graph.Edge{" +
                "v1=" + v1 +
                ", v2=" + v2 +
                '}';
    }

    public boolean equals(Edge e){
        return (this.v1().equals(e.v1()) && this.v2().equals(e.v2()))
                || (this.v1().equals(e.v2()) && this.v2().equals(e.v1()));
    }

    public boolean has(Vertex v) {
        return v1.equals(v) || v2.equals(v);
    }

    @Override
    public int hashCode() {
        return v1().hashCode() + v2().hashCode();
    }

}