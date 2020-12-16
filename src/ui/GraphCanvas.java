package ui;
import graph.GraphController;

import java.awt.*;
import java.awt.Canvas;
import java.util.List;

import static graph.GraphController.*;
import static graph.GraphController.Mode.CONNECTED_COMPONENTS;
import static graph.GraphController.Mode.CUT_VERTICES;

public class GraphCanvas extends Canvas  {

    private static final int RADIUS = 20;
    private static final int HALF_RADIUS = RADIUS >> 1;
    private final Stroke DASHED = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
    private final GraphController graphController;

    //Double buffer
    private Image dbImage;
    private Graphics dbg;

    public GraphCanvas(GraphController graphController, int width, int height) {
        super();
        this.graphController = graphController;
        setPreferredSize(new Dimension(width, height));
        addMouseListener(this.graphController);
        addMouseMotionListener(this.graphController);
        setBackground(Color.white);
        graphController.setMousePressedListener(this::repaint);
        graphController.setMouseMovedListener(this::repaint);
    }

    @Override
    public void paint(Graphics g) {

        if (graphController.checkMode(CONNECTED_COMPONENTS) || graphController.checkMode(CUT_VERTICES)){

            List<CanvasModel> canvasModelList = graphController.checkMode(CONNECTED_COMPONENTS)
                    ? graphController.calculateConnectedComponentsToGraph()
                    : graphController.calculateCutVerticesToGraph();

            for (CanvasModel cm : canvasModelList) {
                if (cm instanceof VertexCanvasModel)
                    drawVertex((VertexCanvasModel) cm, g);
                else
                    drawEdge((EdgeCanvasModel) cm, g);
            }
        }

        else{
            this.graphController.edgesList().forEach(e -> drawEdge(e, g));
            this.graphController.verticesList().forEach( v -> drawVertex(v , g));
        }

    }

    public void drawVertex(VertexCanvasModel v, Graphics g) {
        g.setColor(v.getColor());
        g.fillOval( v.x() - HALF_RADIUS, v.y() - HALF_RADIUS, RADIUS, RADIUS);
    }

    public void drawEdge(EdgeCanvasModel e, Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setStroke(new BasicStroke(2));
        g2.setColor(e.getColor());
        g2.drawLine(e.getX1(), e.getY1(), e.getX2(), e.getY2());
        g2.dispose();
    }


    public void drawDashedLine( EdgeCanvasModel e, Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(e.getColor());
        g2d.setStroke(DASHED);
        g2d.drawLine(e.getX1(), e.getY1(), e.getX2(), e.getY2());
        g2d.dispose();
    }

    public void update (Graphics g) {
        // initialize buffer
        if (dbImage == null) {
            dbImage = createImage (this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics ();
        }

        // clear screen in background
        dbg.setColor (getBackground ());
        dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

        // draw elements in background
        dbg.setColor (getForeground());
        paint(dbg);

        // draw image on the screen
        g.drawImage (dbImage, 0, 0, this);
    }


}
