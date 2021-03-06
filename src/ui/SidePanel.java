package ui;

import graph.GraphController;
import javax.swing.*;
import java.awt.event.ActionListener;

import static graph.GraphController.Mode.*;

public class SidePanel extends JPanel {

    private GraphController graphController;
    private final ActionListener listener;

    public SidePanel(GraphController graphController, HelpDialog helpDialog) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setSize(200,400);
        setLocation(0, 0);
        this.graphController = graphController;
        ButtonGroup group = new ButtonGroup();
        listener = e -> {
          switch(e.getActionCommand()) {
              case "addVertex":
                  graphController.setMode(ADD_VERTEX);
                  break;
              case "addEdge":
                  graphController.setMode(ADD_EDGE);
                  break;
              case "rmvVertex":
                  graphController.setMode(REMOVE_VERTEX);
                  break;
              case "rmvEdge":
                  graphController.setMode(REMOVE_EDGE);
                  break;
              case "mvVertex":
                  graphController.setMode(MOVE_VERTEX);
                  break;
              case "addAllEdges":
                  graphController.addAllEdgesToGraph();
                  break;
              case "drawCircle":
                  graphController.drawCircleToCanvas();
                  break;
              case "connComp":
                  graphController.setMode(CONNECTED_COMPONENTS);
                  break;
              case "cutVertices":
                  graphController.setMode(CUT_VERTICES);
                  break;
              case "clear":
                  graphController.clearCanvas();
                  break;
              case "help":
                  helpDialog.setVisible(true);
                  break;
              default:
                  break;
          }
          graphController.mousePressed();
        };

        setUpButton( new JRadioButton("Add Vertex"), "addVertex", group );
        setUpButton( new JRadioButton("Add Edge"), "addEdge", group );
        setUpButton( new JRadioButton("Remove Vertex"), "rmvVertex", group );
        setUpButton( new JRadioButton("Remove Edge"), "rmvEdge", group );
        setUpButton( new JRadioButton("Move Vertex"), "mvVertex", group );
        setUpButton(new JButton("Add All Edges"), "addAllEdges", null);
        setUpButton(new JButton("Draw Circle"), "drawCircle", null);
        setUpButton(new JButton("Connected Components"), "connComp", null);
        setUpButton(new JButton("Show Cut Vertices"), "cutVertices", null);
        setUpButton(new JButton("Help"), "help", null);
        setUpButton(new JButton("Clear"), "clear", null);
    }

    private void setUpButton(AbstractButton btn, String actionCommand, ButtonGroup btnGroup ) {
        btn.setActionCommand(actionCommand);
        if(btnGroup != null)
            btnGroup.add(btn);
        this.add(btn);
        btn.addActionListener(this.listener);
    }

}