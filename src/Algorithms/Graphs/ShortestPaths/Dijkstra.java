package Algorithms.Graphs.ShortestPaths;

import Util.Terminal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra {

    private static StringBuilder dijkstraExerciseStringBuilder;
    private static StringBuilder dijkstraSolutionStringBuilder;
    private static List<String> nodeList = new ArrayList<>();

    public static void generateExercise() {
        dijkstraExerciseStringBuilder = Terminal.readFile("src/Algorithms/Graphs/ShortestPaths/DijkstraExerciseTemplate.tex");
        dijkstraSolutionStringBuilder = Terminal.readFile("src/Algorithms/Graphs/ShortestPaths/DijkstraSolutionTemplate.tex");

        int numNodes = Terminal.rand.nextInt(3) + 10;

        int[][] nodeMatrix = generateNodeMatrix(numNodes);
        Collections.sort(nodeList);
        for (int i = 0; i < nodeMatrix.length; i++) {
            for (int j = 0; j < nodeMatrix[0].length; j++) {
                System.out.print(nodeMatrix[i][j] + ", ");
            }
            System.out.println();
        }
        char maxChar = generateGraphNodes(nodeMatrix);
        int[][] distMatrix = generateDistMatrix(nodeMatrix, numNodes + 2);
        generateGraphEdges(distMatrix);

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

    private static void generateGraphEdges(int[][] distMatrix) {
        int index = 0;
        for (int i = 0; i < distMatrix.length; i++) {
            for (int j = i + 2; j < distMatrix[0].length; j++) {
                if (distMatrix[i][j] != 0) {
                    Terminal.replaceinSB(dijkstraSolutionStringBuilder, "%$CONNECTIONS$", "\\path (" + nodeList.get(i)
                            + ") edge node[pos=0.25] {" + distMatrix[i][j] + "} (" + nodeList.get(j) + ");\n%$CONNECTIONS$");
                    Terminal.replaceinSB(dijkstraExerciseStringBuilder, "%$CONNECTIONS$", "\\path (" + nodeList.get(i)
                            + ") edge node[pos=0.25] {" + distMatrix[i][j] + "} (" + nodeList.get(j) + ");\n%$CONNECTIONS$");
                }
            }
        }
        index = 0;
        for (int i = 0; i < distMatrix.length - 1; i++) {
            if (distMatrix[i][i + 1] != 0 && index < nodeList.size() - 1) {
                Terminal.replaceinSB(dijkstraSolutionStringBuilder, "%$CONNECTIONS$", "\\path (" + nodeList.get(index)
                        + ") edge node[pos=0.2] {" + distMatrix[i][i + 1] + "} (" + nodeList.get(index + 1) + ");\n%$CONNECTIONS$");
                Terminal.replaceinSB(dijkstraExerciseStringBuilder, "%$CONNECTIONS$", "\\path (" + nodeList.get(index++)
                        + ") edge node[pos=0.2] {" + distMatrix[i][i + 1] + "} (" + nodeList.get(index) + ");\n%$CONNECTIONS$");
            }
        }
    }

    private static int[][] generateDistMatrix(int[][] nodeMatrix, int numNodes) {
        int currNode = 0;
        int[][] distMatrix = new int[numNodes][numNodes];
        for (int i = 0; i < nodeMatrix.length; i++) {
            for (int j = i + 1; j < nodeMatrix[0].length && currNode < numNodes - 1; j++) {
                distMatrix[currNode][++currNode] = Terminal.rand.nextInt(5) + 3;
            }
        }
        currNode = 0;
        for (int i = 0; i < nodeMatrix.length - 1; i++) {
            for (int j = 0; j < nodeMatrix[0].length; j++) {
                if (nodeMatrix[i][j] == 1) {
                    if (nodeMatrix[i + 1][j] == 1 && distMatrix[currNode][getNodeNum(nodeMatrix, i + 1, j)] == 0) {
                        distMatrix[currNode][getNodeNum(nodeMatrix, i + 1, j)] = Terminal.rand.nextInt(5) + 7;
                    }
                    currNode++;
                }
            }
        }
        for (int i = 0; i < distMatrix.length; i++) {
            for (int j = 0; j < distMatrix.length; j++) {
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
                    System.out.println("i: " + i + "\nj: " + j + "\nNodeNum: " + nodeNum);
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
