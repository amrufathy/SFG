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
import javax.swing.JOptionPane;

/**
 *
 * @author amr
 */
public class Canvas extends WebPanel {

    private Graphics2D g2d;

    private final Color NODE_MASTER_COLOR = Color.CYAN;
    
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
                    getNodes().add(new GraphNode(getNodes().size(),
                            me.getX(), me.getY())
                    );

                    mainWindow.newNode = false;
                } else if (isInsideNode(me.getX(), me.getY())) {
                    // node selection
                    selectedNodeID = selectNode(me.getX(), me.getY());

                    if (selectedNodeID >= 0) {
                        GraphNode temp = getNodes().get(selectedNodeID);
                        selectionNode = new GraphNode(1, temp.getX(), temp.getY());
                    }

                    if (mainWindow.newPath) {
                        // path selection
                        stack.push(selectNode(me.getX(), me.getY()));
                        if (stack.size() >= 2) {
                            int nodetoID = stack.pop();
                            int nodefromID = stack.pop();
                            String gain = JOptionPane.showInputDialog("Please Enter Gain");
                            getEdges().add(new GraphEdge(
                                    nodefromID,
                                    nodetoID,
                                    gain)
                            );

                            getNodes().get(nodefromID).getNeighbors()
                                    .add(getNodes().get(nodetoID));

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

        test();
    }

    private void test() {
        GraphNode node1 = new GraphNode(0, 100, 300);
        GraphNode node2 = new GraphNode(1, 300, 100);
        GraphNode node3 = new GraphNode(2, 500, 100);
        GraphNode node4 = new GraphNode(3, 700, 300);

        node1.getNeighbors().add(node2);
        node1.getNeighbors().add(node4);

        node2.getNeighbors().add(node2);
        node2.getNeighbors().add(node3);

        node3.getNeighbors().add(node3);
        node3.getNeighbors().add(node4);
        node4.setIsSink(true);

        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);

        GraphEdge edge1 = new GraphEdge(0, 3, "a");
        GraphEdge edge2 = new GraphEdge(0, 1, "b");
        GraphEdge edge3 = new GraphEdge(1, 1, "c");
        GraphEdge edge4 = new GraphEdge(1, 2, "d");
        GraphEdge edge5 = new GraphEdge(2, 2, "e");
        GraphEdge edge6 = new GraphEdge(2, 3, "f");

        edges.add(edge1);
        edges.add(edge2);
        edges.add(edge3);
        edges.add(edge4);
        edges.add(edge5);
        edges.add(edge6);
    }

    public int selectNode(int x, int y) {
        int counter = 0;
        for (GraphNode node : getNodes()) {
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
        for (GraphNode node : getNodes()) {
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

        for (GraphNode node : getNodes()) {
            g2d.setColor(NODE_MASTER_COLOR);
            g2d.fill(node.getShape());

            g2d.drawString(node.getName(),
                    node.getCenterX(),
                    node.getY() + 50);
        }

        for (GraphEdge edge : getEdges()) {

            GraphNode from = getNodes().get(edge.getFromID());
            GraphNode to = getNodes().get(edge.getToID());

            int distance = 100;

            QuadCurve2D edgeCurve = new QuadCurve2D.Double(
                    from.getX(),
                    from.getY(),
                    ((from.getX() + to.getX()) / 2),
                    ((from.getY() + to.getY()) / 2) + distance,
                    to.getX(),
                    to.getY()
            );

            distance += 50;

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

    /**
     * @return the nodes
     */
    public ArrayList<GraphNode> getNodes() {
        return nodes;
    }

    /**
     * @return the edges
     */
    public ArrayList<GraphEdge> getEdges() {
        return edges;
    }

}
