package ui;

import graph.Graph;
import graph.GraphController;
import javax.swing.*;
import java.awt.*;

public class App extends JFrame  {

    private SidePanel sidePanel;
    private GraphCanvas graphCanvas ;
    private GraphController graphController;
    private HelpDialog helpDialog;
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
        helpDialog = new HelpDialog();
        sidePanel = new SidePanel(graphController, helpDialog);
        add(sidePanel);
        add(graphCanvas);
    }

}
