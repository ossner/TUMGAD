package Algorithms.Graphs.ShortestPaths;

import Util.Terminal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Dijkstra {
    static StringBuilder dijkstraExerciseStringBuilder;
    static StringBuilder dijkstraSolutionStringBuilder;
    private static List<String> nodeList = new ArrayList<>(); // List of LaTeX node-id's makes it easier to generate the TeX

    public static void generateExercise() {
        dijkstraExerciseStringBuilder = Terminal.readFile("src/Algorithms/Graphs/ShortestPaths/DijkstraExerciseTemplate.tex");
        dijkstraSolutionStringBuilder = Terminal.readFile("src/Algorithms/Graphs/ShortestPaths/DijkstraSolutionTemplate.tex");

        int numNodes = Terminal.rand.nextInt(3) + 11; // Number of nodes between start and end

        int[][] nodeMatrix = generateNodeMatrix(numNodes);
        Collections.sort(nodeList);
        char maxChar = generateGraphNodes(nodeMatrix);
        int[][] distMatrix = generateDistMatrix(nodeMatrix, numNodes + 2);
        generateGraphEdges(nodeMatrix, distMatrix);

        Terminal.replaceinSB(dijkstraExerciseStringBuilder, "MAXCHAR", "" + maxChar);
        Terminal.replaceinSB(dijkstraSolutionStringBuilder, "MAXCHAR", "" + maxChar);

        Terminal.replaceinSB(dijkstraSolutionStringBuilder, "$MINLEN$",
                "\\color{tumgadRed}" + dijkstra(distMatrix) + "\\color{black}");


        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$DIJKSTRACELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$DIJKSTRACELL$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$DIJKSTRA$", "\\newpage\n" + dijkstraExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$DIJKSTRA$", "\\newpage\n" + dijkstraSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    /**
     * The actual Dijkstra implementation, although the Queue class does a lot of the heavy-lifting
     */
    private static int dijkstra(int[][] distMatrix) {
        Queue q = new Queue(); // the priority Queue containing the next nodes to be visited and their distances
        Queue shortestPaths = new Queue(); // a queue that keeps all the nodes, only updating the lengths from a to the nodes

        q.insert(new QueueElement(0, 0)); // the first node is trivially always the same
        for (int i = 0; i < distMatrix.length - 1; i++) {
            QueueElement first = q.deQueue();
            shortestPaths.insert(first);
            int offset = first.prio;
            int nodeNum = first.nodeNum;
            // If we arrive at the final node earlier than normally
            if (nodeNum == distMatrix.length - 1) {
                shortestPaths.insert(first);
                printShortestPath(distMatrix, shortestPaths);
                return first.prio;
            }
            q.addNeighbors(nodeNum, distMatrix, offset); // insert the neighbours of the node in question into the queue
            Terminal.replaceinSB(dijkstraSolutionStringBuilder, "%$QUEUEPRINT$",
                    "\\color{tumgadRed}" + Terminal.printArrayList(q.queue) + "\\color{black}");
        }
        QueueElement last = q.deQueue();
        shortestPaths.insert(last);
        printShortestPath(distMatrix, shortestPaths);
        return last.prio;
    }

    private static void printShortestPath(int[][] distMatrix, Queue shortestPaths) {
        ArrayList<Integer> path = new ArrayList<>();
        for (int i = shortestPaths.queue.size() - 1; i >= 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (shortestPaths.queue.get(i).prio - shortestPaths.queue.get(j).prio == distMatrix[shortestPaths.queue.get(j).nodeNum][shortestPaths.queue.get(i).nodeNum]) {
                    path.add(shortestPaths.queue.get(i).nodeNum);
                    i = j + 1;
                    break;
                }
            }
        }
        Terminal.replaceinSB(dijkstraSolutionStringBuilder, "%$SOLUTIONPATH$", "\\color{tumgadRed} %$SOLUTIONPATH$");
        for (int i = path.size() - 1; i >= 0; i--) {
            Terminal.replaceinSB(dijkstraSolutionStringBuilder, "%$SOLUTIONPATH$", "$\\rightarrow$" + ((char) ('A' + path.get(i))) + "%$SOLUTIONPATH$");
        }
    }

    /**
     * generates the LaTeX verteces with the weights specified in the dist matrix
     *
     * @param nodeMatrix the binary NodeMatrix
     * @param distMatrix the dist matrix specifying the weights between nodes
     */
    private static void generateGraphEdges(int[][] nodeMatrix, int[][] distMatrix) {
        for (int i = 0; i < distMatrix.length; i++) {
            for (int j = i + 1; j < distMatrix[0].length; j++) {
                if (distMatrix[i][j] != 0) {
                    if (i == 0 && j > 1) {
                        Terminal.replaceinSB(dijkstraSolutionStringBuilder, "%$CONNECTIONS$", "\\path (" + nodeList.get(i)
                                + ") edge[bend right=" + (j * 5 - j) + "] node[pos=0.25] {" + distMatrix[i][j] + "} (" + nodeList.get(j) + ");\n%$CONNECTIONS$");
                        Terminal.replaceinSB(dijkstraExerciseStringBuilder, "%$CONNECTIONS$", "\\path (" + nodeList.get(i)
                                + ") edge[bend right=" + (j * 5 - j) + "] node[pos=0.25] {" + distMatrix[i][j] + "} (" + nodeList.get(j) + ");\n%$CONNECTIONS$");
                    } else if (j == i + 1 && acrossLeft(i, j)) {
                        if (pathClear(nodeMatrix, i, j)) {
                            Terminal.replaceinSB(dijkstraSolutionStringBuilder, "%$CONNECTIONS$", "\\path (" + nodeList.get(i)
                                    + ") edge[bend right=8] node[pos=0.18] {" + distMatrix[i][j] + "} (" + nodeList.get(j) + ");\n%$CONNECTIONS$");
                            Terminal.replaceinSB(dijkstraExerciseStringBuilder, "%$CONNECTIONS$", "\\path (" + nodeList.get(i)
                                    + ") edge[bend right=8] node[pos=0.18] {" + distMatrix[i][j] + "} (" + nodeList.get(j) + ");\n%$CONNECTIONS$");
                        } else {
                            Terminal.replaceinSB(dijkstraSolutionStringBuilder, "%$CONNECTIONS$", "\\path (" + nodeList.get(i)
                                    + ") edge[bend right=4] node[pos=0.18] {" + distMatrix[i][j] + "} (" + nodeList.get(j) + ");\n%$CONNECTIONS$");
                            Terminal.replaceinSB(dijkstraExerciseStringBuilder, "%$CONNECTIONS$", "\\path (" + nodeList.get(i)
                                    + ") edge[bend right=4] node[pos=0.18] {" + distMatrix[i][j] + "} (" + nodeList.get(j) + ");\n%$CONNECTIONS$");
                        }
                    } else {
                        Terminal.replaceinSB(dijkstraSolutionStringBuilder, "%$CONNECTIONS$", "\\path (" + nodeList.get(i)
                                + ") edge node[pos=0.3] {" + distMatrix[i][j] + "} (" + nodeList.get(j) + ");\n%$CONNECTIONS$");
                        Terminal.replaceinSB(dijkstraExerciseStringBuilder, "%$CONNECTIONS$", "\\path (" + nodeList.get(i)
                                + ") edge node[pos=0.3] {" + distMatrix[i][j] + "} (" + nodeList.get(j) + ");\n%$CONNECTIONS$");
                    }
                }
            }
        }
    }

    /**
     * checks if a path between nodes is clear (not obstructed by another path)
     * used for nodes when the first node and the second are in different rows and columns
     *
     * @param i the node first in the sequence
     * @param j the node to the left and below of the i node
     *
     * @return whether or not the path between the two nodes is clear
     */
    private static boolean pathClear(int[][] nodeMatrix, int i, int j) {
        int row1 = Integer.parseInt("" + nodeList.get(i).charAt(0));
        int col1 = Integer.parseInt("" + nodeList.get(i).charAt(1));
        int row2 = Integer.parseInt("" + nodeList.get(j).charAt(0));
        int col2 = Integer.parseInt("" + nodeList.get(j).charAt(1));

        for (int k = col2 + 1; k < col1; k++) {
            if (nodeMatrix[row1][k] == 1 && nodeMatrix[row2][k + 1] == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * asserts whether the nodes i and j are in different rows AND different columns
     * (i.e. whether you have to go across)
     */
    private static boolean acrossLeft(int i, int j) {
        return nodeList.get(i).charAt(0) != nodeList.get(j).charAt(0) && nodeList.get(i).charAt(1) != nodeList.get(j).charAt(1);
    }

    /**
     * Generates random values for the upper half of the dist-matrix (as the graph is non-directional)
     * the values are depending on how many nodes the vertex skips, to make it harder to find a path
     *
     * @param nodeMatrix the binary nodeMatrix
     * @param numNodes the number of nodes in the graph makes up the dimensions of the dist matrix
     *
     * @return a numNodes x numNodes int-array with the vertex weights between each of the nodes
     * "taxing" the weights based on how far they would get you (faster paths => higher tax)
     */
    private static int[][] generateDistMatrix(int[][] nodeMatrix, int numNodes) {
        int currNode = 0;
        int[][] distMatrix = new int[numNodes][numNodes];
        // connect each node with its successor
        for (int i = 0; i < nodeMatrix.length; i++) {
            for (int j = i + 1; j < nodeMatrix[0].length && currNode < numNodes - 1; j++) {
                distMatrix[currNode][++currNode] = Terminal.rand.nextInt(5) + 4;
            }
        }
        currNode = 0;
        // connect each node with the node below it
        for (int i = 0; i < nodeMatrix.length - 1; i++) {
            for (int j = 0; j < nodeMatrix[0].length && currNode < numNodes - 1; j++) {
                if (nodeMatrix[i][j] == 1) {
                    if (nodeMatrix[i + 1][j] == 1 && distMatrix[currNode][getNodeNum(nodeMatrix, i + 1, j)] == 0) {
                        distMatrix[currNode][getNodeNum(nodeMatrix, i + 1, j)] = Terminal.rand.nextInt(5) + 7;
                    }
                    currNode++;
                }
            }
        }
        // connect the A-node with up to two nodes in the first column (heavily taxed)
        int max = 2; // A maximum of 2 extra connections to A
        for (int i = 1; i < nodeMatrix.length - 1; i++) {
            if (max == 0) {
                return distMatrix;
            }
            if (nodeMatrix[i][1] == 1) {
                distMatrix[0][getNodeNum(nodeMatrix, i, 1)] = Terminal.rand.nextInt(5) + (11 * i);
                max--;
            }
        }
        return distMatrix;
    }

    /**
     * provided with the coordinates of a node and the nodeMatrix, the method finds the
     * corresponding nodeNumber
     *
     * @param nodeMatrix the binary nodeMatrix
     * @param i the row the searched node is in
     * @param j the column the wanted node is in
     *
     * @return the nodeNumber of the node with the given coordinates
     */
    private static int getNodeNum(int[][] nodeMatrix, int i, int j) {
        int nodeNum = 0;
        for (int k = 0; k < nodeMatrix.length; k++) {
            for (int l = 0; l < nodeMatrix[0].length; l++) {
                if (k == i && l == j) {
                    return nodeNum;
                }
                if (nodeMatrix[k][l] == 1) {
                    nodeNum++;
                }
            }
        }
        return -1;
    }

    /**
     * makes the nodes specified in the nodeMatrix visible in the LaTeX templates
     *
     * @param nodeMatrix the nodeMatrix with the position of the nodes
     *
     * @return the highest node number as a char to replace in the template
     */
    private static char generateGraphNodes(int[][] nodeMatrix) {
        int numLabeled = 0;
        for (int i = 0; i < nodeMatrix.length; i++) {
            for (int j = 0; j < nodeMatrix[0].length; j++) {
                if (nodeMatrix[i][j] == 1) {
                    if (!(i == 0 && j == 0) && !(i == nodeMatrix.length - 1 && j == nodeMatrix[0].length - 1)) {
                        Terminal.replaceinSB(dijkstraExerciseStringBuilder, ", draw=none] (" + i + j + ")", "](" + i + j + ")");
                        Terminal.replaceinSB(dijkstraSolutionStringBuilder, ", draw=none] (" + i + j + ")", "](" + i + j + ")");
                    }
                    Terminal.replaceinSB(dijkstraExerciseStringBuilder, "%" + i + j, "" + Character.toString('A' + numLabeled));
                    Terminal.replaceinSB(dijkstraSolutionStringBuilder, "%" + i + j, "" + Character.toString('A' + numLabeled++));
                }
            }
        }
        return (char) ('A' + --numLabeled); // the last node
    }

    /**
     * Generates a binary node matrix, where a 1 stands for a node present, 0 for no node
     * present.
     *
     * @param numNodes number of nodes that have to be generated (i.e. the ones beside start and end)
     * @return a 5x6 binary matrix specifying the position of graph nodes
     */
    private static int[][] generateNodeMatrix(int numNodes) {
        int[][] nodeMatrix = new int[5][6];
        nodeList.add("00"); //
        nodeMatrix[0][0] = 1; // first node
        while (numNodes > 0) {
            int x = Terminal.rand.nextInt(nodeMatrix.length);
            int y = Terminal.rand.nextInt(nodeMatrix[0].length - 2) + 1;
            if (nodeMatrix[x][y] != 1) {
                nodeList.add("" + x + y);
                nodeMatrix[x][y] = 1;
                numNodes--;
            }
        }
        nodeList.add("45");
        nodeMatrix[4][5] = 1; // last node
        return nodeMatrix;
    }
}

class QueueElement {
    int nodeNum;
    int prio;

    public QueueElement(int nodeNum, int prio) {
        this.nodeNum = nodeNum;
        this.prio = prio;
    }

    @Override
    public String toString() {
        return "(" + ((char) ('A' + nodeNum)) + ", " + prio + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueueElement that = (QueueElement) o;
        return nodeNum == that.nodeNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeNum);
    }
}

class Queue {
    ArrayList<QueueElement> queue;
    ArrayList<Integer> pastQueue;

    public Queue() {
        queue = new ArrayList<>();
        pastQueue = new ArrayList<>();
    }

    /**
     * inserts a QueueElement ito this queue, if it matches the criteria
     */
    void insert(QueueElement element) {
        // if there is already a node with this number in the queue and its distance is already lower, we will not insert it
        // Also if the node was already inserted into the queue before and already visited (i.e. it's in the pastQueue)
        if (queue.contains(element) && element.prio > queue.get(queue.indexOf(element)).prio || pastQueue.contains(element.nodeNum)) {
            return;
        }
        int i = 0;
        while (i < queue.size() && queue.get(i).prio <= element.prio) {
            i++;
        }
        queue.add(i, element);
        for (int j = 0; j < queue.size(); j++) {
            if (queue.get(j).nodeNum == element.nodeNum && queue.get(j) != element) {
                queue.remove(j);
            }
        }
    }

    QueueElement deQueue() {
        QueueElement element = queue.remove(0);
        pastQueue.add(element.nodeNum);
        return element;
    }

    @Override
    public String toString() {
        return queue.toString();
    }

    /**
     * inserts the neighbours of this node into the queue list with the distances specified in the
     * dist Matrix
     */
    public void addNeighbors(int i, int[][] distMatrix, int offset) {
        Queue printQueue = new Queue();
        for (int j = 0; j < distMatrix.length; j++) {
            if (distMatrix[i][j] != 0) {
                QueueElement temp = new QueueElement(j, distMatrix[i][j] + offset);
                if (!pastQueue.contains(temp.nodeNum) && (!queue.contains(temp) || queue.get(queue.indexOf(temp)).prio > temp.prio)) {
                    printQueue.insert(temp);
                }
                insert(temp);
            }
        }
        if (printQueue.queue.size() == 0) {
            Terminal.replaceinSB(Dijkstra.dijkstraSolutionStringBuilder, "%$QUEUECHANGE$",
                    "\\color{tumgadRed}---\\color{black}\\\\\n\\hline" +
                            "\n%$QUEUEPRINT$ & %$QUEUECHANGE$");
        } else {
            Terminal.replaceinSB(Dijkstra.dijkstraSolutionStringBuilder, "%$QUEUECHANGE$",
                    "\\color{tumgadRed}" + Terminal.printArrayList(printQueue.queue) + "\\color{black}\\\\\n\\hline" +
                            "\n%$QUEUEPRINT$ & %$QUEUECHANGE$");
        }
    }
}
