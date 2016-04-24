/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solver;

import components.GraphEdge;
import components.GraphNode;
import components.Path;
import components.Paths;
import java.util.ArrayList;

/**
 *
 * @author amr
 */
public class MasonSolver {

    Paths listOfCycles;
    Paths listOfPaths;

    private ArrayList<GraphNode> nodes;
    private ArrayList<GraphEdge> edges;

    private ArrayList<ArrayList<ArrayList<Path>>> nonTouchingLoops;

    public MasonSolver(ArrayList<GraphNode> nodes, ArrayList<GraphEdge> edges) {

        this.nodes = nodes;
        this.edges = edges;

        GraphSolver graphSolver = new GraphSolver();
        listOfCycles = graphSolver.findCycles(nodes);
        listOfPaths = graphSolver.findForwardPaths(nodes.get(0));

        nonTouchingLoops = new ArrayList<>();

    }

    public String[] calculateResult() {
        String numerator = getNumerator();

        String denominator = getDelta();
        return new String[]{numerator, denominator};
    }

    private String getNumerator() {
        String num = "";

        for (int i = 0; i < listOfPaths.size(); i++) {
            Path path = listOfPaths.get(i);
            String gain = getGainOfPath(path);
            String delta = getDeltaOfPath(path);
            num += (gain + "*" + delta);
            if (i < listOfPaths.size() - 1) {
                num += " + ";
            }
        }

        return num;
    }

    private String getDeltaOfPath(Path path) {
        Paths cyclesToBeRemoved = cloneList(listOfCycles);
        Paths cyclesCopy = cloneList(listOfCycles);
        String deltaPath = "(1 - (";
        for (Path cycle : cyclesCopy) {
            if (isTouching(cycle, path)) {
                cyclesToBeRemoved.remove(cycle);
            }
        }
        for (int i = 0; i < cyclesToBeRemoved.size(); i++) {
            Path cycle = cyclesToBeRemoved.get(i);
            deltaPath += getGainOfCycle(cycle);
            if (i < cyclesToBeRemoved.size() - 1) {
                deltaPath += " + ";
            }
        }
        if (cyclesToBeRemoved.isEmpty()) {
            deltaPath += "0";
        }
        deltaPath += "))";
        return deltaPath;
    }

    private String getGainOfPath(Path path) {
        String gain = "(";
        for (int i = 0; i < path.size() - 1; i++) {
            GraphNode from = path.get(i);
            GraphNode to = path.get(i + 1);
            GraphEdge edge = getEdge(from.getIndex(), to.getIndex());
            gain += edge.getGain();
        }
        gain += ")";
        return gain;
    }

    private String getDelta() {
        String delta = "1 - (";
        int counter = 0;

        String gainOfAllLoops = "(";
        for (int i = 0; i < listOfCycles.size(); i++) {
            Path cycle = listOfCycles.get(i);
            gainOfAllLoops += getGainOfCycle(cycle);
            if (i != listOfCycles.size() - 1) {
                gainOfAllLoops += " + ";
            }
        }
        gainOfAllLoops += ")";

        delta += gainOfAllLoops;

        for (ArrayList<ArrayList<Path>> listDegreeofNontouchingLoops : nonTouchingLoops) {
            if (counter % 2 == 0) {
                delta += " + ";
            } else {
                delta += " - ";
            }

            String gain = "(";
            for (int i = 0; i < listDegreeofNontouchingLoops.size(); i++) {
                ArrayList<Path> pathsOfSameDegree = listDegreeofNontouchingLoops.get(i);

                for (int j = 0; j <= pathsOfSameDegree.size() - 1; j++) {
                    Path cycle = pathsOfSameDegree.get(j);
                    gain += getGainOfCycle(cycle);
                }
                if (i != listDegreeofNontouchingLoops.size() - 1) {
                    gain += " + ";
                }
            }
            gain += ")";
            delta += gain;
            counter++;
        }
        delta += ")";
        return delta;
    }

    private String getGainOfCycle(Path cycle) {
        String cycleGain = "";
        for (int i = 0; i < cycle.size() - 1; i++) {
            GraphNode from = cycle.get(i);
            GraphNode to = cycle.get(i + 1);
            GraphEdge edge = getEdge(from.getIndex(), to.getIndex());
            cycleGain += edge.getGain();
        }
        return cycleGain;
    }

    private GraphEdge getEdge(int from, int to) {
        for (GraphEdge edge : edges) {
            if (edge.getFromID() == from && edge.getToID() == to) {
                return edge;
            }
        }
        return null;
    }

    private void expand(ArrayList<Path> cycles) {
        boolean flag = false;
        for (Path cycle1 : listOfCycles) {
            flag = false;
            for (Path cycle2 : cycles) {
                if (isTouching(cycle1, cycle2)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                ArrayList<Path> temp = (ArrayList<Path>) cycles.clone();
                temp.add(cycle1);

                if (nonTouchingLoops.size() < temp.size() - 1) {
                    nonTouchingLoops.add(new ArrayList<ArrayList<Path>>());
                }

                nonTouchingLoops.get(temp.size() - 2).add(temp);
                expand(temp);
            }
        }
    }

    public ArrayList<ArrayList<ArrayList<Path>>> getNonTouchingLoops() {
        for (Path cycle1 : listOfCycles) {
            for (Path cycle2 : listOfCycles) {

                if (!isTouching(cycle1, cycle2)) {
                    ArrayList<Path> temp = new ArrayList<>();
                    temp.add(cycle1);
                    temp.add(cycle2);

                    if (nonTouchingLoops.isEmpty()) {
                        nonTouchingLoops.add(new ArrayList<ArrayList<Path>>());
                    }

                    nonTouchingLoops.get(0).add(temp);
                    expand(temp);
                }
            }
        }
        for (int i = 0; i < nonTouchingLoops.size(); i++) {
            removeNonTouchingDuplicateCycles();
        }

        return nonTouchingLoops;
    }

    public void removeNonTouchingDuplicateCycles() {

        boolean flag;

        for (ArrayList<ArrayList<Path>> listDegreeofNontouchingLoops : nonTouchingLoops) {

            for (int i = 0; i < listDegreeofNontouchingLoops.size(); i++) {
                for (int j = 0; j < listDegreeofNontouchingLoops.size(); j++) {

                    flag = false;

                    ArrayList<Path> paths1 = listDegreeofNontouchingLoops.get(i);
                    ArrayList<Path> paths2 = listDegreeofNontouchingLoops.get(j);

                    if (paths1.size() == paths2.size()) {
                        for (Path cycle : paths1) {
                            if (!paths2.contains(cycle)) {
                                flag = true;
                                break;
                            }
                        }
                    }

                    if (!flag && i != j) {
                        listDegreeofNontouchingLoops.remove(i);
                    }
                }
            }
        }
    }

    private boolean isTouching(Path cycle1, Path cycle2) {
        for (GraphNode node1 : cycle1) {
            for (GraphNode node2 : cycle2) {
                if (node1.getIndex() == node2.getIndex()) {
                    return true;
                }
            }
        }
        return false;
    }

    private Paths cloneList(Paths list) {
        Paths clone = new Paths();
        for (Path item : list) {
            clone.add((Path) item.clone());
        }
        return clone;
    }
}
