/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 *
 * @author amr
 */
public class GraphNode {

    private int index;
    private String name;
    private int X;
    private int Y;
    public static int nodeRadius = 20;

    private ArrayList<GraphNode> neighbors;

    private boolean isSink;

    private Ellipse2D shape;

    public GraphNode(int index, int x, int y) {
        this.index = index;
        this.name = ("Node " + (index + 1));
        this.X = x;
        this.Y = y;

        shape = new Ellipse2D.Double(
                getCenterX(),
                getCenterY(),
                (2 * nodeRadius),
                (2 * nodeRadius)
        );

        neighbors = new ArrayList<>();
        isSink = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GraphNode)) {
            return false;
        }
        return this.index == ((GraphNode) obj).index;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the X
     */
    public int getX() {
        return X;
    }

    /**
     * @return the Y
     */
    public int getY() {
        return Y;
    }

    public final int getCenterX() {
        return X - nodeRadius;
    }

    public final int getCenterY() {
        return Y - nodeRadius;
    }

    /**
     * @return the shape
     */
    public Ellipse2D getShape() {
        return shape;
    }

    /**
     * @param shape the shape to set
     */
    public void setShape(Ellipse2D shape) {
        this.shape = shape;
    }

    /**
     * @return the neighbors
     */
    public ArrayList<GraphNode> getNeighbors() {
        return neighbors;
    }

    /**
     * @return the isSink
     */
    public boolean isSink() {
        return isSink;
    }

    /**
     * @param isSink the isSink to set
     */
    public void setIsSink(boolean isSink) {
        this.isSink = isSink;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

}
