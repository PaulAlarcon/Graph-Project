import java.util.LinkedList;

public class Subgraph {

    Vertex v;
    LinkedList<Edge> edges;

    Subgraph(Vertex v, LinkedList<Edge> edges){
        this.v = v;
        this.edges = edges;
    }

    public Vertex getV() {
        return v;
    }

    public LinkedList<Edge> getEdges() {
        return edges;
    }
}
