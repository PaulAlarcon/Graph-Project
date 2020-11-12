package ui;
import graph.GraphController;

import java.awt.*;
import java.awt.Canvas;
import java.util.List;

import static graph.GraphController.Mode.CONNECTED_COMPONENTS;

public class GraphCanvas extends Canvas  {

    private static final int RADIUS = 20;
    private static final int HALF_RADIUS = RADIUS >> 1;
    private final Stroke DASHED = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
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
    }

    @Override
    public void paint(Graphics g) {
//        if mode == connected components;
        if(graphController.testMode(CONNECTED_COMPONENTS)){
          graphController.addConnectedComponentsToGraph()
                  .forEach(
                          m -> {
                              if(m instanceof GraphController.VertexCanvasModel)
                                  drawVertex((GraphController.VertexCanvasModel)m, g);
                                else drawEdge((GraphController.EdgeCanvasModel)m, g);
                          });

        }
        else{
            this.graphController.edgesList().forEach(e -> drawDashedLine(e, g));
            this.graphController.verticesList().forEach( v -> drawVertex(v , g));
        }

    }

    public void drawVertex(GraphController.VertexCanvasModel v, Graphics g) {
        g.setColor(v.getColor());
        g.fillOval( v.getX() - HALF_RADIUS, v.getY() - HALF_RADIUS, RADIUS, RADIUS);
    }

    public void drawEdge(GraphController.EdgeCanvasModel e, Graphics g) {
        g.setColor(e.getColor());
        g.drawLine(e.getX1(), e.getY1(), e.getX2(), e.getY2());
    }


    public void drawDashedLine( GraphController.EdgeCanvasModel e, Graphics g){
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
