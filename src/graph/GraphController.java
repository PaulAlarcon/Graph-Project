package graph;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class GraphController implements MouseListener, MouseMotionListener {

    private static Random randNum = new Random();
    private Graph graph;
    private MousePressedListener mousePressedListener;
    private Vertex selectedVertex;

    private List<ComponentCanvasModel> connectedComponents;

    public enum Mode{NONE, ADD_VERTEX, ADD_EDGE, REMOVE_VERTEX, REMOVE_EDGE, MOVE_VERTEX, CONNECTED_COMPONENTS, CUT_VERTICES, CONVEX_HULL }
    private Mode mode;

    public boolean testMode(Mode m){
        return mode.equals(m);
    }

    public GraphController(Graph graph) {
        this.graph = graph;
        mode = Mode.NONE;
        selectedVertex = null;
    }

    public interface CanvasModel{

    }

    public interface VertexCanvasModel extends CanvasModel {
        int getX();
        int getY();
        Color getColor();
    }

    public interface EdgeCanvasModel extends CanvasModel{
        int getX1();
        int getY1();
        int getX2();
        int getY2();
        Color getColor();
    }

    public interface ComponentCanvasModel{
        Set<Vertex> vertices();
        Set<Edge> edges();
        Color getColor();
    }

    public void setMode(Mode mode){
        this.mode = mode;
    }

    public List<VertexCanvasModel> verticesList() {
       return graph.vertices().stream().map(v -> new VertexCanvasModel() {
           @Override
           public int getX() {
               return (int)v.getX();
           }

           @Override
           public int getY() {
               return (int)v.getY();
           }

           @Override
           public Color getColor() {
               if(mode == Mode.CONNECTED_COMPONENTS)
                   for (ComponentCanvasModel c: connectedComponents)
                       if (c.vertices().contains(v))
                           return c.getColor();
               if(mode == Mode.CUT_VERTICES)
                    return Color.YELLOW;
               if(mode == Mode.CONVEX_HULL)
                   return new Color(125, 43, 224);
               if(selectedVertex != null && selectedVertex.equals(v))
                   return Color.BLUE;
               return Color.RED;
           }
       }).collect(Collectors.toList());
    }

    public List<EdgeCanvasModel> edgesList() {
        return graph.edges().stream().map(e -> new EdgeCanvasModel() {
            @Override
            public int getX1() {
                return (int)e.getX1();
            }

            @Override
            public int getY1() {
                return (int)e.getY1();
            }

            @Override
            public int getX2() {
                return (int)e.getX2();
            }

            @Override
            public int getY2() {
                return (int)e.getY2();
            }

            @Override
            public Color getColor() {
                if(mode == Mode.CONNECTED_COMPONENTS)
                    for (ComponentCanvasModel c: connectedComponents) {
                        if (c.edges().contains(e))
                            return c.getColor();
                    }
                if(mode == Mode.CONVEX_HULL)
                    return new Color(125, 43, 224);
                return Color.BLACK;
            }
        }).collect(Collectors.toList());
    }

    public void setMousePressedListener(MousePressedListener listener) { this.mousePressedListener = listener; }

    public void drawCircleToCanvas() {
        int originX = 300;
        int originY = 300;
        Set<Vertex> tempVertices = graph.vertices();
        int radius = (int)(Math.min(originX, originY)*0.8);
        int size = tempVertices.size();
        double anglePortion = 2*Math.PI/size;
        double angle = 0;
        for (Vertex tempVertex : tempVertices) {
            tempVertex.setX(radius * Math.cos(angle) + originX);
            tempVertex.setY(radius * Math.sin(angle) + originY);
            angle += anglePortion;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        switch (mode) {
            default:
                break;
            case ADD_VERTEX:
                graph.addVertex(new Vertex(e.getX(), e.getY()));
                break;
            case ADD_EDGE:
                selectedVertex = addEdgeToGraph(e);
                break;
            case REMOVE_VERTEX:
                removeVertexFromGraph(e);
                break;
            case REMOVE_EDGE:
                removeEdgeFromGraph(e);
                break;
            case MOVE_VERTEX:
                selectedVertex = moveVertexFromGraph(e);
                break;
        }
        mousePressedListener.onMousePressed();
    }

    private Vertex addEdgeToGraph(MouseEvent e) {
        Vertex v = graph.identifySelected(e.getX(), e.getY(), 20);
        if (selectedVertex == null)
            return v;
        else
            if (v != null) {
                graph.addEdge(selectedVertex, v);
                return null;
            }
            else
                return selectedVertex;
    }

    private void removeVertexFromGraph(MouseEvent e) {
        graph.removeVertex(graph.identifySelected(e.getX(), e.getY(), 20));
    }

    private Vertex moveVertexFromGraph(MouseEvent e) {
        if (selectedVertex == null)
            return graph.identifySelected(e.getX(), e.getY(), 20);
        else{
            Vertex v = new Vertex(e.getX(), e.getY());
            selectedVertex.translate(v);
            return null;
        }
    }

    private void removeEdgeFromGraph(MouseEvent e) {
        graph.removeEdge(graph.identifyEdge(e.getX(), e.getY(), 20));
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    public void addAllEdgesToGraph() {
        graph.addAllEdges();
    }

    public void clearCanvas() {
        graph.clear();
    }

    public void mousePressed() {
        mousePressedListener.onMousePressed();
    }

    //rename method
    public List<CanvasModel> addConnectedComponentsToGraph() {
            List<Stack<Vertex>> cc = graph.getConnectedComponents();

            List<CanvasModel> res = new ArrayList<>();

            for (Stack<Vertex> c : cc) {
                final Color color = new Color(randNum.nextInt(255), randNum.nextInt(255), randNum.nextInt(255));
                for ( Vertex v : c){
                    res.add(new VertexCanvasModel() {
                        @Override
                        public int getX() {
                            return (int)v.getX();
                        }

                        @Override
                        public int getY() {
                            return (int)v.getY();
                        }

                        @Override
                        public Color getColor() {
                            return color;
                        }
                    });
                    for (Edge e : graph.getEdges(v)) {
                        res.add(new EdgeCanvasModel() {
                            @Override
                            public int getX1() {
                                return (int)e.getX1();
                            }

                            @Override
                            public int getY1() {
                                return (int)e.getY1();
                            }

                            @Override
                            public int getX2() {
                                return (int)e.getX2();
                            }

                            @Override
                            public int getY2() {
                                return (int)e.getY2();
                            }

                            @Override
                            public Color getColor() {
                                return color;
                            }
                        });
                    }
                }

        }

            return res;
    }

    public void drawConvexHull(){
        graph.convexHull();
    }

}
