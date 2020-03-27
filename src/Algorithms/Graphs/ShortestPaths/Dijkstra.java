package Algorithms.Graphs.ShortestPaths;

import Util.Terminal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Dijkstra {

    private static StringBuilder dijkstraExerciseStringBuilder;
    private static StringBuilder dijkstraSolutionStringBuilder;
    private static List<String> nodeList = new ArrayList<>();

    public static void generateExercise() {
        dijkstraExerciseStringBuilder = Terminal.readFile("src/Algorithms/Graphs/ShortestPaths/DijkstraExerciseTemplate.tex");
        dijkstraSolutionStringBuilder = Terminal.readFile("src/Algorithms/Graphs/ShortestPaths/DijkstraSolutionTemplate.tex");

        int numNodes = Terminal.rand.nextInt(3) + 11;

        int[][] nodeMatrix = generateNodeMatrix(numNodes);
        Collections.sort(nodeList);
        char maxChar = generateGraphNodes(nodeMatrix);
        int[][] distMatrix = generateDistMatrix(nodeMatrix, numNodes + 2);
        generateGraphEdges(nodeMatrix, distMatrix);
        dijkstra(distMatrix);

        Terminal.replaceinSB(dijkstraExerciseStringBuilder, "MAXCHAR", "" + maxChar);
        Terminal.replaceinSB(dijkstraSolutionStringBuilder, "MAXCHAR", "" + maxChar);

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$DIJKSTRACELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$DIJKSTRACELL$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$DIJKSTRA$", "\\newpage\n" + dijkstraExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$DIJKSTRA$", "\\newpage\n" + dijkstraSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    private static void dijkstra(int[][] distMatrix) {
        Queue q = new Queue();
        q.insert(new QueueElement(0, 0));
        while (q.queue.size() > 0) {
            QueueElement first = q.deQueue();
            int offset = first.prio;
            int nodeNum = first.nodeNum;
            q.addNeighbors(nodeNum, distMatrix, offset);
            System.out.println(q);
        }
    }

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

    private static boolean acrossLeft(int i, int j) {
        return nodeList.get(i).charAt(0) != nodeList.get(j).charAt(0) && nodeList.get(i).charAt(1) != nodeList.get(j).charAt(1);
    }

    private static int[][] generateDistMatrix(int[][] nodeMatrix, int numNodes) {
        int currNode = 0;
        int[][] distMatrix = new int[numNodes][numNodes];
        for (int i = 0; i < nodeMatrix.length; i++) {
            for (int j = i + 1; j < nodeMatrix[0].length && currNode < numNodes - 1; j++) {
                distMatrix[currNode][++currNode] = Terminal.rand.nextInt(5) + 4;
            }
        }
        currNode = 0;
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
        for (int i = 0; i < distMatrix.length; i++) {
            for (int j = 0; j < distMatrix[0].length; j++) {
                System.out.print(distMatrix[i][j] + ", ");
            }
            System.out.println();
        }
        return distMatrix;
    }

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
        return (char) ('A' + --numLabeled);
    }

    private static int[][] generateNodeMatrix(int numNodes) {
        int[][] nodeMatrix = new int[5][6];
        nodeList.add("00");
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

    void insert(QueueElement element) {
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

    public void addNeighbors(int i, int[][] distMatrix, int offset) {
        for (int j = 0; j < distMatrix.length; j++) {
            if (distMatrix[i][j] != 0) {
                insert(new QueueElement(j, distMatrix[i][j] + offset));
            }
        }
    }
}
