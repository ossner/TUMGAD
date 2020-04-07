package Algorithms.Graphs;

public class Graph {
    boolean directed;
    int numNodes;
    boolean numberedNodes;
    boolean coloredFirst;

    public Graph(boolean directed, int numNodes, boolean numberedNodes, boolean coloredFirst) {
        this.directed = directed;
        this.numNodes = numNodes;
        this.numberedNodes = numberedNodes;
        this.coloredFirst = coloredFirst;
    }
}
