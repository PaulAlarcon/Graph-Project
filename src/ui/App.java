package ui;

import graph.Graph;
import graph.GraphController;
import graph.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class App extends JFrame  {

    private SidePanel sidePanel;
    private GraphCanvas graphCanvas ;
    private GraphController graphController;
    private final Graph graph;

    private final int canvasWidth = 600;
    private final int canvasHeight = 600;

    public App(){
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(840, 640);
        setTitle("Graph Project - Paul Alarcon 2020");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());
        graph = new Graph();
        graphController = new GraphController(graph);
        graphCanvas = new GraphCanvas(graphController, canvasWidth, canvasHeight);
        sidePanel = new SidePanel(graphController);
        add(sidePanel);
        add(graphCanvas);
        testGraph();
    }



    public void testGraph() {
        Random rand = new Random();
        for ( int i = 0; i < 100; i++)
            graph.addVertex(new Vertex(rand.nextInt(canvasWidth), rand.nextInt(canvasHeight)));
    }
}
