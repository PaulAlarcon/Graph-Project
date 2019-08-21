import java.util.*;

public class Graph implements Cloneable {

        LinkedList<Vertex> vertices;
        LinkedList<Edge> edges;

        ArrayList<ArrayList<Edge>> connectedComponents;


    Graph(){
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
        connectedComponents = new ArrayList<>();
    }

    Graph(LinkedList<Vertex> v, LinkedList<Edge> e){
        this.vertices = v;
        this.edges = e;
        connectedComponents = new ArrayList<>();
    }

    public LinkedList<Vertex> getVerticesList() {
        return vertices;
    }

    public LinkedList<Edge> getEdgesList() {
        return edges;
    }

    public void addVertex(Vertex p){
        this.getVerticesList().add(p);
    }

    public Subgraph removeVertex(Vertex v){
        Vertex vRemoved = this.getVerticesList().remove(findVertex(v));
        Subgraph ans = removeVertexUtil(v);

        Iterator i = getEdgesList().iterator();
        Edge e;
        while (i.hasNext()) {
            e = (Edge) i.next();
            if (e.getP1().equals(vRemoved) || e.getP2().equals(vRemoved)) {
                i.remove();
            }
        }

        return ans;

    }

    public Subgraph removeVertexUtil(Vertex v){
        LinkedList<Edge> eRemoved = new LinkedList<>();
        Iterator i = this.getEdgesList().iterator();
        Edge e;
        while (i.hasNext()) {
            e = (Edge) i.next();
            if (e.getP1().equals(v) || e.getP2().equals(v)) {
                eRemoved.add(e);
            }
        }

        return new Subgraph(v, eRemoved);
    }

    public int findVertex(Vertex v){
        int sVertex = -1;
        int index = 0;

        for (Vertex vertex : this.getVerticesList()){
            if(vertex.equals(v)){
                sVertex = index;
            }
            index++;
        }

        return sVertex;
    }

    public Vertex identifySelected(int x, int y, int rad){
        Vertex vAns = null;
        //greedy approach will be to sort it
        for(Vertex v : this.getVerticesList()){
            if(v.distance((double)x, (double)(y)) < rad){
                vAns = v;
            }
        }
        return vAns;
    }

    public void addEdge(Vertex p1, Vertex p2) {
        if(p1 != p2){
            Edge e = new Edge(p1, p2);
            if(!contains(this.getEdgesList(), e)){
                this.getEdgesList().add(e);
            }
        }
    }

    public void removeEdges(Edge e){
        this.getEdgesList().remove(findEdge(e));
    }

    public int findEdge(Edge e){
        int sEdge = -1;
        int index = 0;

        for (Edge edge : this.getEdgesList()){
            if(edge.equals(e)){
                sEdge = index;
            }
            index++;
        }

        return sEdge;
    }

    public Edge identifyEdge(int x, int y, int rad){
        Edge eAns = null;
        for(Edge e : this.getEdgesList()){
            if(e.ptLineDist((double)x, (double)y) < rad){
                eAns = e;
            }
        }
        return eAns;
    }

    public void clear(){
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
        connectedComponents = new ArrayList<>();
    }

    public ArrayList<ArrayList<Vertex>> DFS(LinkedList<Vertex> verticesArray, LinkedList<Edge> edgeArray){
        ArrayList<ArrayList<Vertex>> DFSresult = new ArrayList<>();
        for(Vertex v : getVerticesList()){
            v.visited = false;
            v.camefrom = null;
        }
        ArrayList<Vertex> vTemp ;
        for(Vertex v : verticesArray){
            vTemp = new ArrayList<>();
            if(!v.isVisited()){
                DFSresult.add(DFSUtil(v, vTemp, edgeArray));
            }
        }

        return DFSresult;
    }

    public ArrayList<Vertex> DFSUtil(Vertex v, ArrayList vTemp, LinkedList<Edge> edgeArray ){
        v.visited = true;
        vTemp.add(v);
        for(Vertex cV: neighbors(v, edgeArray)){
            if(!cV.isVisited()){
                cV.camefrom = v;
                DFSUtil(cV,vTemp, edgeArray);
            }
        }
        return vTemp;
    }

    public ArrayList<Vertex> neighbors(Vertex v, LinkedList<Edge> edgesArray){
        ArrayList<Vertex> n = new ArrayList<>();
        for( Edge e : edgesArray){
            if(e.getP1().equals(v)){
                n.add(e.getP2());
            }

            if(e.getP2().equals(v)){
                n.add(e.getP1());
            }
        }
        return n;
    }
    
    public ArrayList<ArrayList<Edge>> getConnectedComponents(){
        connectedComponents = new ArrayList<>();
        ArrayList<Edge> eTemp;
        for( ArrayList<Vertex> a : DFS(this.getVerticesList(), this.getEdgesList())){
            eTemp = new ArrayList<>();
            for(Vertex v: a){
//                if(v.camefrom != null){
//                    Edge e = new Edge(v, v.camefrom);
//                    eTemp.add(e);
//                }
                for ( Edge e : getEdgesList()){
                    if(e.exists(v)){
                        eTemp.add(e);
                    }
                }
            }
            connectedComponents.add(eTemp);
        }

        return connectedComponents;
    }

    public ArrayList<Vertex> getCutVertices(){

        ArrayList<Vertex> ans = new ArrayList<>();

        int componentsNumber = this.getConnectedComponents().size();

        for(int i = 0; i < this.getVerticesList().size(); i++){
            LinkedList<Vertex> copyVertices = (LinkedList)this.getVerticesList().clone();
            LinkedList<Edge> copyEdges = (LinkedList)this.getEdgesList().clone();
            Graph g = new Graph(copyVertices, copyEdges);
            Vertex v = this.getVerticesList().get(i);
            g.removeVertex(v);
            if(g.DFS(copyVertices, copyEdges).size() > componentsNumber){
                ans.add(v);
            }
        }

        return ans;
    }

    public void addAllEdges(){
        for(Vertex v1 : this.getVerticesList() ){
            for(Vertex v2: this.getVerticesList()){
                this.addEdge(v1, v2);
            }
        }
    }

    public boolean contains(LinkedList<Edge> edArr, Edge edge){
        for(Edge e : edArr){
            if(e.equals(edge))
                return true;
        }
        return false;
    }
}
