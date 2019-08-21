import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class Canvas extends java.awt.Canvas implements MouseListener{

    ArrayList<Vertex> vArr = new ArrayList<>();

    Vertex prevPoint;
    Vertex nextPoint;

    final int RADIO = 20;
    int selectedVertex;

    SidePanel controller;
    Graph graph;

    boolean showCC;
    boolean showCV;


    Canvas(SidePanel controller, Graph graph) {
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.white);
        addMouseListener(this);
        this.controller = controller;
        this.graph = graph;

        selectedVertex = -1;

        showCC = false;
        showCV = false;


        setActions();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (controller.getWhichButton()){
            default:
                break;
            case 1:
                showCC = false;
                showCV = false;
                addVertexCanvas(e.getX(), e.getY());
                break;
            case 2:
                showCC = false;
                showCV = false;
                selectedVertex = -1;
                addEdgesCanvas(e.getX(), e.getY());
                break;
            case 3:
                showCC = false;
                showCV = false;
                removeVertexCanvas(e.getX(), e.getY());
                break;
            case 4:
                showCC = false;
                showCV = false;
                removeEdgesCanvas(e.getX(), e.getY());
                break;
            case 5:
                showCC = false;
                moveVertexCanvas(e.getX(), e.getY());
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(!showCC){
            for (Edge e : graph.getEdgesList()){
                drawEdge(e.getP1(), e.getP2(), g2, Color.black);
            }
        }
        else
            PaintComponents(g2);

        for( int i = 0; i < graph.getVerticesList().size(); i++){
            if(selectedVertex == graph.findVertex(graph.getVerticesList().get(i))){
                    drawCircle((int)graph.getVerticesList().get(i).getX(), (int)graph.getVerticesList().get(i).getY(), g2 , Color.GREEN);
            }
            else{
                drawCircle((int)graph.getVerticesList().get(i).getX(), (int)graph.getVerticesList().get(i).getY(), g2 , Color.RED);
            }
            }

        if(showCV){
            paintCutVerticeS(g2);
        }





    }

    public void drawCircle(int x, int y, Graphics g, Color c){
            g.setColor(c);
            g.fillOval( x - RADIO/2, y - RADIO/2, RADIO, RADIO);
    }

    public void drawEdge(Vertex v1, Vertex v2, Graphics g, Color c){
        g.setColor(c);
        g.drawLine((int)v1.getX(), (int)v1.getY(), (int)v2.getX(), (int)v2.getY());
    }

    public void addVertexCanvas(int x, int y){
        Vertex v = new Vertex(x,y);
        graph.addVertex(v);
        repaint();
    }

    public void addEdgesCanvas(int x, int y ){
        Vertex click = new Vertex(x, y);
        if(prevPoint == null) {
            prevPoint = graph.identifySelected((int)click.x,(int)click.y, RADIO);
            if(prevPoint != null){
                selectedVertex = graph.findVertex(prevPoint);
            }
        }

        else{
            nextPoint = graph.identifySelected((int)click.x,(int)click.y, RADIO);

            if(nextPoint != null) {
                graph.addEdge(prevPoint, nextPoint);
            }

            prevPoint = null;
            nextPoint = null;
        }
        repaint();
    }

    public void removeEdgesCanvas(int x, int y){
        Edge e = graph.identifyEdge(x, y, RADIO);
        if(e != null){
            graph.removeEdges(e);
            repaint();
        }
    }

    public void removeVertexCanvas(int x, int y){
        Vertex v = graph.identifySelected(x, y, RADIO);
        if(v != null){
            graph.removeVertex(v);
            repaint();
        }
    }

    public void moveVertexCanvas(int x, int y){
        Vertex click = new Vertex(x, y);
        if(prevPoint == null) {
            prevPoint = graph.identifySelected(x, y, RADIO);
            if(prevPoint != null){
                selectedVertex = graph.findVertex(prevPoint);
            }
        }

        else{
            if(selectedVertex != -1){
                graph.vertices.get(selectedVertex).translate(click);
            }
            prevPoint = null;
            nextPoint = null;
        }
        repaint();
    }

    public void setActions(){

        controller.getBtnAllEdges().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCV = true;
                showCC = false;
                graph.addAllEdges();
                repaint();
            }
        });

        controller.getBtnShowCVertices().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCV = true;
                showCC = false;
                graph.getCutVertices();
                repaint();
            }
        });

        controller.getBtnConnComp().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCC = true;
                showCV = false;
                graph.getConnectedComponents();
                repaint();
            }
        });

        controller.getBtnClear().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCV = true;
                showCC = false;
                graph.clear();
                repaint();
            }
        });

        controller.getBtnHelp().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCV = true;
                showCC = false;
                HelpInfo h = new HelpInfo();
                h.setVisible(true);
            }
        });

    }

    public void PaintComponents(Graphics2D g){

        Color[] colors = {Color.RED, Color.PINK, Color.ORANGE, Color.GREEN, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.lightGray};
        Collections.shuffle(Arrays.asList(colors));

        int i = 0;
        for(ArrayList<Edge> arr : graph.getConnectedComponents()){

            for(Edge e: arr){
                g.setStroke(new BasicStroke(5));
                drawEdge(e.getP1(), e.getP2(), g, colors[i % colors.length]);
            }
            i++;
        }
    }

    public void paintCutVerticeS(Graphics2D g){
        Color[] colors = {Color.PINK, Color.ORANGE, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.lightGray};
        Collections.shuffle(Arrays.asList(colors));
        int i = 0;
        for(Vertex v : graph.getCutVertices()){
            drawCircle((int)v.getX() , (int)v.getY() , g , colors[i]);
            i++;
        }
    }


}

