/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signalflowgraph;

import com.alee.laf.WebLookAndFeel;
import components.GraphEdge;
import components.GraphNode;
import components.Path;
import gui.MainWindow;
import java.util.ArrayList;
import solver.MasonSolver;

/**
 *
 * @author amr
 */
public class SFG {

    public static void main(String[] args) {

        WebLookAndFeel.install();
        WebLookAndFeel.setDecorateFrames(true);
        WebLookAndFeel.setDecorateDialogs(true);

        MainWindow mainWindow = new MainWindow();

        mainWindow.show();

    }

    /*public static void main(String[] args) {

     ArrayList<GraphNode> nodes = new ArrayList<>();
     ArrayList<GraphEdge> edges = new ArrayList<>();

     GraphNode node1 = new GraphNode(0, 0, 0);
     GraphNode node2 = new GraphNode(1, 0, 0);
        
     node1.getNeighbors().add(node2);
     node2.getNeighbors().add(node1);
        
     node2.setIsSink(true);

     nodes.add(node1);
     nodes.add(node2);
        
     GraphEdge edge1 = new GraphEdge(0, 1, "G(s)");
     GraphEdge edge2 = new GraphEdge(1, 0, "1");
        
     edges.add(edge1);
     edges.add(edge2);
        
     MasonSolver mason = new MasonSolver(nodes, edges);

     ArrayList<ArrayList<ArrayList<Path>>> list = mason.getNonTouchingLoops();

     mason.calculateResult();

     System.out.println("bye");
     }*/
}
