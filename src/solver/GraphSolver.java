/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solver;

import components.GraphNode;
import components.Path;
import components.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author amr
 */
public class GraphSolver {

    private Paths listOfForwardPaths;
    private Paths listOfCycles;

    public GraphSolver() {
        listOfForwardPaths = new Paths();
        listOfCycles = new Paths();
    }

    public Paths findForwardPaths(GraphNode source) {
        Path stack = new Path();
        stack.push(source);
        findForwardPathsHelper(stack);
        return listOfForwardPaths;
    }

    private void findForwardPathsHelper(Path visited) {

        GraphNode source = visited.peek();

        for (GraphNode neighbor : source.getNeighbors()) {
            if (!visited.contains(neighbor)) {
                visited.push(neighbor);
                if (neighbor.isSink()) {
                    listOfForwardPaths.add((Path) visited.clone());
                }
                findForwardPathsHelper(visited);
            }
        }
        visited.pop();
    }

    public Paths findCycles(ArrayList<GraphNode> nodes) {
        for (GraphNode node : nodes) {
            Path stack = new Path();
            stack.push(node);
            findCyclesHelper(stack, node);
        }
        removeDuplicateCycles();
        return listOfCycles;
    }

    private void findCyclesHelper(Path visited, GraphNode source) {

        GraphNode miniSource = visited.peek();

        for (GraphNode neighbor : miniSource.getNeighbors()) {

            if (neighbor.getIndex() == source.getIndex()) {
                Path temp = (Path) visited.clone();
                temp.push(neighbor);
                listOfCycles.add((Path) temp.clone());
                visited.pop();
                return;
            }

            if (!visited.contains(neighbor)) {
                visited.push(neighbor);
                findCyclesHelper(visited, source);
            }
        }
        visited.pop();
    }

    private void removeDuplicateCycles() {
        Paths testCycleList = (Paths) cloneList(listOfCycles);
        for (Path cycle : testCycleList) {
            cycle.remove(0);
            Collections.sort(cycle, new Comparator<GraphNode>() {
                @Override
                public int compare(GraphNode left, GraphNode right) {
                    return (left.getIndex() > right.getIndex()) ? 1
                            : (left.getIndex() < right.getIndex()) ? -1 : 0;
                }
            });
        }

        for (int i = 0; i < listOfCycles.size(); i++) {
            for (int j = 0; j < listOfCycles.size(); j++) {
                if (testCycleList.get(i).equals(testCycleList.get(j)) && i != j) {
                    listOfCycles.remove(i);
                    testCycleList.remove(i);
                }
            }
        }
    }

    private Paths cloneList(Paths list) {
        Paths clone = new Paths();
        for (Path item : list) {
            clone.add((Path) item.clone());
        }
        return clone;
    }
}
