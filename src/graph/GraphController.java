package graph;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GraphController implements MouseListener, MouseMotionListener {

    private static Random randNum = new Random();
    private Graph graph;
    private MousePressedListener mousePressedListener;
    private MouseMovedListener mouseMotionListener;
    private Vertex selectedVertex;
    private Edge hoveredEdge;
    private Vertex hoveredVertex;

    Set<Vertex> cutVertices;

    public enum Mode{NONE, ADD_VERTEX, ADD_EDGE, REMOVE_VERTEX, REMOVE_EDGE, MOVE_VERTEX, CONNECTED_COMPONENTS, CUT_VERTICES }
    private Mode mode;

    public boolean checkMode(Mode m){
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
        int id();
        int x();
        int y();
        Color getColor();
    }

    public interface EdgeCanvasModel extends CanvasModel{
        int getX1();
        int getY1();
        int getX2();
        int getY2();
        Color getColor();
    }

    public void setMode(Mode mode){
        this.mode = mode;
    }

    public List<VertexCanvasModel> verticesList() {
       return graph.vertices().stream().map(v -> new VertexCanvasModel() {
           @Override
           public int id() {
               return v.id();
           }

           @Override
           public int x() {
               return v.x();
           }

           @Override
           public int y() {
               return v.y();
           }

           @Override
           public Color getColor() {
               if(mode == Mode.CUT_VERTICES && cutVertices.contains(v)){
                   return new Color(44, 149, 104);
               }
               if(selectedVertex != null && selectedVertex.equals(v))
                   return Color.BLUE;
               if ( mode == Mode.MOVE_VERTEX || mode == Mode.REMOVE_VERTEX){
                   if(hoveredVertex != null && hoveredVertex.equals(v))
                       return new Color(17, 191, 191);
               }

               return Color.RED;
           }
       }).collect(Collectors.toList());
    }

    public List<EdgeCanvasModel> edgesList() {
        return graph.edges().stream().map(e -> new EdgeCanvasModel() {
            @Override
            public int getX1() {
                return e.x1();
            }

            @Override
            public int getY1() {
                return e.y1();
            }

            @Override
            public int getX2() {
                return e.x2();
            }

            @Override
            public int getY2() {
                return e.y2();
            }

            @Override
            public Color getColor() {
                if ( mode == Mode.REMOVE_EDGE && (hoveredEdge != null && e.equals(hoveredEdge)))
                    return Color.GREEN;
                return Color.BLACK;
            }
        }).collect(Collectors.toList());
    }

    public void setMousePressedListener(MousePressedListener listener) {
        this.mousePressedListener = listener;
    }

    public void setMouseMovedListener(MouseMovedListener listener){
        this.mouseMotionListener = listener;
    }

    public void drawCircleToCanvas() {
        int originX = 300;
        int originY = 300;
        Set<Vertex> tempVertices = graph.vertices();
        int radius = (int) (Math.min(originX, originY)*0.8);
        int size = tempVertices.size();
        double anglePortion = 2*Math.PI/size;
        double angle = 0;
        for (Vertex tempVertex : tempVertices) {
            tempVertex.setX((int) (radius * Math.cos(angle) + originX));
            tempVertex.setY((int) (radius * Math.sin(angle) + originY));
            angle += anglePortion;
        }
    }

    private Vertex addEdgeToGraph(MouseEvent e) {
        Vertex v = identifySelectedVertex(e.getX(), e.getY(), 20);
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
        graph.removeVertex(identifySelectedVertex(e.getX(), e.getY(), 50));
    }

    private Vertex moveVertexFromGraph(MouseEvent e) {
        if (selectedVertex == null)
            return identifySelectedVertex(e.getX(), e.getY(), 20);
        else{
            Vertex v = new Vertex(e.getX(), e.getY());
            selectedVertex.translate(v);
            return null;
        }
    }

    public Vertex identifySelectedVertex(int x, int y, int rad) {

        if (graph.vertices().isEmpty()) return null;

        List<Vertex> verticesList = new ArrayList<>(graph.vertices());

        Vertex selectedVertex = verticesList.get(0);
        double selectedVertexDistance = selectedVertex.point().distance(x, y);

        double currentDistance;
        Vertex currentVertex;
        for ( int i = 1; i < verticesList.size(); ++i) {

            currentVertex = verticesList.get(i);
            currentDistance = currentVertex.point().distance(x, y);

            if ( currentDistance < selectedVertexDistance )
                selectedVertex = currentVertex;

        }
        return selectedVertex;
    }

    public Edge identifySelectedEdge(int x, int y, int rad) {

        if (this.graph.edges().isEmpty()) return null;

        List<Edge> edgeList = new ArrayList<>(this.graph.edges());
        Edge selectedEdge = edgeList.get(0);
        double selectedEdgeDistance = selectedEdge.line().ptLineDist(x, y);

        Edge currentEdge;
        double currentEdgeDistance;

        for (int i = 0; i < edgeList.size(); ++i) {
            currentEdge = edgeList.get(i);
            currentEdgeDistance = currentEdge.line().ptLineDist(x, y);

            if (currentEdgeDistance < selectedEdgeDistance)
                selectedEdge = currentEdge;
        }

        return selectedEdge;
    }

    private void removeEdgeFromGraph(MouseEvent e) {
        graph.removeEdge(identifySelectedEdge(e.getX(), e.getY(), 50));
    }

    public void addAllEdgesToGraph() {
        graph.addAllEdges();
    }

    public void clearCanvas() {
        graph.clear();
    }

    public void mousePressed() {
        mousePressedListener.onMousePressed();
    }

    public List<CanvasModel> calculateConnectedComponentsToGraph() {
        List<Stack<Vertex>> cc = graph.getConnectedComponents();
        List<CanvasModel> res = new ArrayList<>();
        for (Stack<Vertex> c : cc) {
            Color color = new Color(randNum.nextInt(255), randNum.nextInt(255), randNum.nextInt(255));
            for ( Vertex v : c){
                res.add(new VertexCanvasModel() {
                    @Override
                    public int id() {
                        return v.id();
                    }
                    @Override
                    public int x() {
                        return v.x();
                    }

                    @Override
                    public int y() {
                        return v.y();
                    }

                    @Override
                    public Color getColor() {
                        return color;
                    }
                });
                for (Edge e : graph.adjacentEdges(v))
                    res.add(new EdgeCanvasModel() {
                        @Override
                        public int getX1() {
                            return e.x1();
                        }

                        @Override
                        public int getY1() {
                            return e.y1();
                        }

                        @Override
                        public int getX2() {
                            return e.x2();
                        }

                        @Override
                        public int getY2() {
                            return e.y2();
                        }

                        @Override
                        public Color getColor() {
                            return color;
                        }
                    });
            }
        }

        return res;
    }

    public List<CanvasModel> calculateCutVerticesToGraph(){
        List<CanvasModel> res = new ArrayList<>();
        Set<Vertex> cutVertices = graph.getCutVertices();

        for (Vertex v : graph.vertices()) {
            res.add(new VertexCanvasModel() {
                @Override
                public int id() {
                    return v.id();
                }

                @Override
                public int x() {
                    return v.x();
                }

                @Override
                public int y() {
                    return v.y();
                }

                @Override
                public Color getColor() {
                    if (cutVertices.contains(v))
                        return new Color(randNum.nextInt(255), randNum.nextInt(255), randNum.nextInt(255));
                    return Color.RED;
                }
            });
        }

        for (Edge e : graph.edges()){
            res.add(new EdgeCanvasModel() {
                @Override
                public int getX1() {
                    return e.x1();
                }

                @Override
                public int getY1() {
                    return e.y1();
                }

                @Override
                public int getX2() {
                    return e.x2();
                }

                @Override
                public int getY2() {
                    return e.y2();
                }

                @Override
                public Color getColor() {
                    return Color.BLACK;
                }
            });
        }


        return res;

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

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        hoveredVertex = identifySelectedVertex(e.getX(), e.getY(),50);
        hoveredEdge = identifySelectedEdge(e.getX(), e.getY(), 50);
        mouseMotionListener.onMouseMoved();
    }

}
