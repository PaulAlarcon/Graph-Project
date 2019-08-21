import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SidePanel extends JPanel {

    private JRadioButton BtnMoveVertex, BtnRemEdges, BtnRemVertex, BtnAddEdges, BtnAddVertex;
    private ButtonGroup RadioBtn;
    private Button BtnAllEdges, BtnConnComp, BtnShowCVertices, BtnHelp, BtnClear;
    private int whichButton;

    public SidePanel() {

        whichButton = 0;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setSize(200,400);
        setLocation(0, 0);

        RadioBtn = new ButtonGroup();
        BtnAddVertex = new JRadioButton("Add Vertex");
        BtnAddEdges = new JRadioButton("Add Edges");
        BtnRemVertex = new JRadioButton("Remove Vertex");
        BtnRemEdges = new JRadioButton("Remove Edges");
        BtnMoveVertex = new JRadioButton("Move Vertex");

        add(BtnAddVertex);
        add(BtnAddEdges);
        add(BtnRemVertex);
        add(BtnRemEdges);
        add(BtnMoveVertex);

        RadioBtn.add(BtnAddVertex);
        RadioBtn.add(BtnAddEdges);
        RadioBtn.add(BtnRemVertex);
        RadioBtn.add(BtnRemEdges);
        RadioBtn.add(BtnMoveVertex);

        ActionListener radioListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                whichButton = Integer.parseInt(e.getActionCommand());
            }
        };
        whichButton = -1;

        BtnAddVertex.addActionListener(radioListener);
        BtnAddEdges.addActionListener(radioListener);
        BtnRemVertex.addActionListener(radioListener);
        BtnRemEdges.addActionListener(radioListener);
        BtnMoveVertex.addActionListener(radioListener);

        BtnAddVertex.setActionCommand("1");
        BtnAddEdges.setActionCommand("2");
        BtnRemVertex.setActionCommand("3");
        BtnRemEdges.setActionCommand("4");
        BtnMoveVertex.setActionCommand("5");

        BtnAllEdges = new Button("Add All Edges");
        add(BtnAllEdges);
        BtnConnComp = new Button("Connected Components");
        add(BtnConnComp);
        BtnShowCVertices = new Button("Show All Cut Vertices");
        add(BtnShowCVertices);
        BtnHelp = new Button("Help");
        add(BtnHelp);
        BtnClear = new Button("Clear");
        add(BtnClear);


    }

    public Button getBtnAllEdges() {
        return BtnAllEdges;
    }

    public Button getBtnConnComp() {
        return BtnConnComp;
    }

    public Button getBtnShowCVertices() {
        return BtnShowCVertices;
    }

    public Button getBtnHelp() {
        return BtnHelp;
    }

    public Button getBtnClear() {
        return BtnClear;
    }

    public int getWhichButton() {
        return whichButton;
    }
}