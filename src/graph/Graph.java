package graph;

import java.io.Serializable;
import java.util.*;

public class Graph implements Serializable {

    private Set<Vertex> vertices;
    private Set<Edge> edges;

    public Graph(){
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    Graph(Set<Vertex> v, Set<Edge> e){
        this.vertices = v;
        this.edges = e;
    }

    public Set<Vertex> vertices() {
        return vertices;
    }

    public Set<Edge> edges() {
        return edges;
    }

    public void addVertex(Vertex vertex){
        this.vertices().add(vertex);
    }

    public boolean removeVertex (Vertex vertex) {
        if(vertex == null ) return false;
        if(vertices.remove(vertex))
            adjacentVertices(vertex).forEach(v -> removeEdge(new Edge(v, vertex)));
        return true;
    }

    public Vertex identifySelected(int x, int y, int rad){
        Vertex selectedVertex = null;
        for(Vertex v : this.vertices())
            if(v.distance(x, y) < rad) selectedVertex = v;
        return selectedVertex;
    }

    public boolean addEdge(Vertex v1, Vertex v2) {
        if(v1.equals(v2))
            return false;
        return edges.add(new Edge(v1, v2));
    }

    public void removeEdge(Edge e) {
        if(e == null) return;
        this.edges.remove(e);
    }

    public Edge identifyEdge(int x, int y, int rad) {
        for (Edge edge : this.edges())
            if(edge.ptLineDist(x, y) <= rad) return edge;
        return null;
    }

    public void clearEdges(){
        if(!edges.isEmpty()) edges = new HashSet<>();
    }

    public void clearVertices(){
        if(!vertices.isEmpty()) vertices = new HashSet<>();
    }

    public void clear() {
        clearEdges();
        clearVertices();
    }

    public void addAllEdges(){
        List<Vertex> vertices = new ArrayList<>(vertices());
        int n = vertices.size();
        for(int i = 0; i < n; ++i){
            for (int j = i + 1; j < n; ++j){
                edges.add(new Edge(vertices.get(i), vertices.get(j)));
                System.out.println("Edge added, edge size : " + edges.size() );
            }
        }
    }

    public List<Vertex> adjacentVertices(Vertex v){
        List<Vertex> adjacentVertices = new ArrayList<>();
        for (Edge e : this.edges()) {
            if(e.getP1().equals(v))
                adjacentVertices.add(e.getP2());
            else if(e.getP2().equals(v))
                adjacentVertices.add(e.getP1());
        }
        return adjacentVertices;
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
//         added lines
//            visited.replace(curr, true);
//            for(Vertex v : adjacentVertices(curr)){
//                if(!visited.get(v))
//                    stack.push(v);
//            }
//            path.push(curr);
//
//        end added lines

         if(!visited.get(curr)) {
             for(Vertex v : adjacentVertices(curr))
                 stack.push(v);
             visited.replace(curr, true);
             path.push(curr);
         }
        }
        return path;
    }

    public boolean depthFirstSearch (Vertex target) {
        return depthFirstSearchPath(null, target) != null;
    }

    public Stack<Vertex> depthFirstSearchPath ( Vertex start, Vertex target ) {

        Stack<Vertex> path = new Stack<>();
        Stack<Vertex> stack = new Stack<>();

        Map<Vertex, Boolean> visited = new HashMap<>();
        for (Vertex v : vertices)
            visited.put(v, false);

        Vertex s = start == null ? new ArrayList<>(vertices).get(0) : start;

        stack.push(s);
        visited.replace(s, true);

        while (!stack.isEmpty()) {
            Vertex curr = stack.pop();
            if(target != null && curr == target)
                return path;
            path.push(curr);
            if(!visited.get(curr)){
                visited.replace(curr, true);
                for (Vertex v : adjacentVertices(curr))
                    stack.push(v);
            }

        }
        return path;
    }

    public List<Vertex> getCutVertices () {

        List<Vertex> ans = new ArrayList<>();

//        int componentsNumber = this.getConnectedComponents().size();
//
//        for(int i = 0; i < this.getVerticesList().size(); i++){
//            List<graph.Vertex> copyVertices = new LinkedList<>(this.getVerticesList());
//            List<graph.Edge> copyEdges = new LinkedList<>(this.getEdgesList());
//            graph.Graph g = new graph.Graph(copyVertices, copyEdges);
//            graph.Vertex v = this.getVerticesList().get(i);
//            g.removeVertex(v);
//            if(g.DFS(copyVertices, copyEdges).size() > componentsNumber){
//                ans.add(v);
//            }
//        }

        return ans;
    }

    public void convexHull() {

        clearEdges();

        Stack<Integer> stack = new Stack<>();

        List<Vertex> verticesList = new LinkedList<>(vertices);

        Vertex P0 = verticesList.get(0);
        Vertex curr;

        for(int i = 0; i < verticesList.size(); ++i){
            curr = verticesList.get(i);
            if(curr.getY() < P0.getY() && curr.getX() < P0.getX()) P0 = curr;
        }

//        P0.dis



    }


    public List<Edge> getEdges(Vertex v){
        List<Edge> edgesList = new LinkedList<>();
        for (Edge e : edges())
            if(e.contains(v)) edgesList.add(e);
        return edgesList;
    }

}
