import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

class Edge extends Line2D {

    Vertex v1;
    Vertex v2;


    Edge(Vertex v1, Vertex v2){
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
    public void setLine(double x1, double y1, double x2, double y2) {

    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "v1=" + v1 +
                ", v2=" + v2 +
                '}';
    }

    //New methods

    public boolean equals(Edge e){
        return (this.getP1().equals(e.getP1()) && this.getP2().equals(e.getP2()))
                || (this.getP1().equals(e.getP2()) && this.getP2().equals(e.getP1()));

    }

    public boolean exists(Vertex v){
        return this.getP1().equals(v) || this.getP2().equals(v);
    }


}