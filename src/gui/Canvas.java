/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.alee.laf.panel.WebPanel;
import components.GraphEdge;
import components.GraphNode;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author amr
 */
public class Canvas extends WebPanel {

    private Graphics2D g2d;

    private final Color NODE_MASTER_COLOR = Color.CYAN;
    private final Color NODE_SELECTED_COLOR = Color.YELLOW;

    private MouseAdapter mouseListener;

    private ArrayList<GraphNode> nodes = new ArrayList<>();
    private ArrayList<GraphEdge> edges = new ArrayList<>();

    private MainWindow mainWindow;

    private int selectedNodeID = -1;

    private GraphNode selectionNode = null;

    private Stack<Integer> stack = new Stack<>();

    public Canvas(final MainWindow mainWindow) {
        super();

        setBackground(Color.WHITE);

        this.mainWindow = mainWindow;

        mouseListener = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if (!isInsideNode(me.getX(), me.getY()) && mainWindow.newNode) {
                    // new node drawing (addition)
                    nodes.add(new GraphNode(nodes.size(),
                            me.getX(), me.getY())
                    );

                    mainWindow.newNode = false;
                } else if (isInsideNode(me.getX(), me.getY())) {
                    // node selection
                    selectedNodeID = selectNode(me.getX(), me.getY());

                    if (selectedNodeID >= 0) {
                        GraphNode temp = nodes.get(selectedNodeID);
                        selectionNode = new GraphNode(1, temp.getX(), temp.getY());
                    }

                    if (mainWindow.newPath) {
                        // path selection
                        stack.push(selectNode(me.getX(), me.getY()));
                        if (stack.size() >= 2) {
                            int nodetoID = stack.pop();
                            int nodefromID = stack.pop();
                            edges.add(new GraphEdge(
                                    nodefromID,
                                    nodetoID)
                            );

                            nodes.get(nodefromID).getNeighbors()
                                    .add(nodes.get(nodetoID));

                            mainWindow.newPath = false;
                        }

                    }

                } else {
                    // node unselection
                    selectionNode = null;
                    selectedNodeID = -1;
                    System.out.println("Not painting");
                }

                repaint();
            }

        };

        addMouseListener(mouseListener);
    }

    public int selectNode(int x, int y) {
        int counter = 0;
        for (GraphNode node : nodes) {
            // (x - center_x)^2 + (y - center_y)^2 < radius^2
            double xPart = Math.pow((x - node.getX()), 2);
            double yPart = Math.pow((y - node.getY()), 2);
            double radiusPart = Math.pow(GraphNode.nodeRadius, 2);

            counter++;

            if ((xPart + yPart) < radiusPart) {
                break;
            }
        }

        if (counter > 0) {
            return counter - 1;
        }

        return -1;
    }

    private boolean isInsideNode(int x, int y) {
        for (GraphNode node : nodes) {
            // (x - center_x)^2 + (y - center_y)^2 < radius^2
            double xPart = Math.pow((x - node.getX()), 2);
            double yPart = Math.pow((y - node.getY()), 2);
            double radiusPart = Math.pow(GraphNode.nodeRadius, 2);

            if ((xPart + yPart) < radiusPart) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.drawOval(500, 500, 10, 10);

        for (GraphNode node : nodes) {
            g2d.setColor(NODE_MASTER_COLOR);
            g2d.fill(node.getShape());

            g2d.drawString(node.getName(),
                    node.getCenterX(),
                    node.getY() + 50);
        }

        for (GraphEdge edge : edges) {

            GraphNode from = nodes.get(edge.getFromID());
            GraphNode to = nodes.get(edge.getToID());

            QuadCurve2D edgeCurve = new QuadCurve2D.Double(
                    from.getX(),
                    from.getY(),
                    (from.getX() + to.getX()) / 2,
                    (from.getY() + to.getY()) / 2,
                    to.getX(),
                    to.getY()
            );

            g2d.setColor(Color.RED);
            g2d.draw(edgeCurve);
        }

        if (selectionNode != null) {
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.BLACK);
            g2d.draw(new Ellipse2D.Double(
                    selectionNode.getCenterX(),
                    selectionNode.getCenterY(),
                    (2 * GraphNode.nodeRadius),
                    (2 * GraphNode.nodeRadius)
            ));
        }
    }

}
