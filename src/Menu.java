import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    SidePanel sidePanel;
    Canvas canvas ;

    Graph graph;

    Menu(){
        setLocation(50, 50);
        setResizable(false);
        setSize(840, 640);
        setTitle("Data Strucutre Project - Paul Alarcon");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());
        sidePanel = new SidePanel();
        add(sidePanel);

        graph = new Graph();

        canvas = new Canvas(sidePanel, graph);
        add(canvas, graph);

    }



}
