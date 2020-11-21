package graph;
import java.util.*;

public class Graph {

    private Set<Vertex> vertices;
    private Set<Edge> edges;

    public Graph(){
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    public Set<Vertex> vertices() {
        return vertices;
    }

    public Set<Edge> edges() {
        return edges;
    }

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public boolean removeVertex (Vertex vertex) {
        if (vertex == null ) return false;
        if (vertices.remove(vertex))
            adjacentVertices(vertex).forEach(v -> removeEdge(new Edge(v, vertex)));
        return true;
    }

    public boolean addEdge (Vertex v1, Vertex v2) {
        if(v1.equals(v2))
            return false;
        return edges.add(new Edge(v1, v2));
    }

    public boolean addEdge (Edge e){
        return edges.add(e);
    }

    public void removeEdge (Edge e) {
        if(e == null)
            return;
        this.edges.remove(e);
    }

    public void clearEdges(){
        if(!edges.isEmpty())
            edges = new HashSet<>();
    }

    public void clearVertices(){
        if(!vertices.isEmpty())
            vertices = new HashSet<>();
    }

    public void clear() {
        clearEdges();
        clearVertices();
    }

    public void addAllEdges(){
        List<Vertex> vertices = new ArrayList<>(this.vertices);
        int n = vertices.size();
        for(int i = 0; i < n; ++i){
            for (int j = i + 1; j < n; ++j)
                edges.add(new Edge(vertices.get(i), vertices.get(j)));

        }
    }

    public Set<Vertex> adjacentVertices (Vertex v) {
        Set<Vertex> adjacentVertices = new HashSet<>();
        for (Edge e : this.edges()) {
            if (e.v1().equals(v))
                adjacentVertices.add(e.v2());
            else if (e.v2().equals(v))
                adjacentVertices.add(e.v1());
        }
        return adjacentVertices;
    }

    public List<Edge> adjacentEdges (Vertex v) {
        List<Edge> edgesList = new LinkedList<>();
        for (Edge e : edges())
            if(e.has(v)) edgesList.add(e);
        return edgesList;
    }


    public List<Stack<Vertex>> getConnectedComponents() {
        //Map to keep track the visited vertices
        Map<Vertex, Boolean> map = new HashMap<>();

        //Make sure to initiate all the values of each vertex to false
        for (Vertex v : this.vertices()) map.put(v, false);

        List<Stack<Vertex>> connectedComponents = new ArrayList<>();

        for (Vertex v : this.vertices())
            if(!map.get(v))
                connectedComponents.add(DFSUtil(v, map));

        return connectedComponents;
    }

    public Stack<Vertex> DFSUtil(Vertex start, Map<Vertex, Boolean> visited){
        Stack<Vertex> path, stack;
        path = new Stack<>();
        stack = new Stack<>();

        stack.push(start);

        while(!stack.empty()) {
         Vertex curr = stack.pop();

         if(!visited.get(curr)) {
             for(Vertex v : adjacentVertices(curr))
                 stack.push(v);
             visited.replace(curr, true);
             path.push(curr);
         }
        }
        return path;
    }

    public Set<Vertex> getCutVertices () {

        Set<Vertex> cutVertices = new HashSet<>();
        // store the Connected Components size of the original graph.
        int originalCCSize = getConnectedComponents().size();

        List<Vertex> verticesCopy = new ArrayList<>(vertices);

        List<Edge> adjacentEdges;

        for ( Vertex v : verticesCopy) {
            // store all the adjacent edges of the current vertex.
            adjacentEdges = adjacentEdges(v);
            removeVertex(v);

            // if the size of the CC of the current graph is greater than CC of full graph then V is a part of Cut Vertices.
            if (getConnectedComponents().size()> originalCCSize)
                cutVertices.add(v);

            // add the removed vertex and the removed edges back to the graph.
            addVertex(v);
            for (Edge e : adjacentEdges)
                addEdge(e);
        }

        return cutVertices;
    }
}
