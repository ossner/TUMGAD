package Algorithms.Graphs.MinimalSpanningTrees.Prim;

import Util.Terminal;

import java.util.ArrayList;
import java.util.Collections;

import static Algorithms.Graphs.ShortestPaths.Dijkstra.getNodeNum;

public class Prim {
    static StringBuilder primExerciseStringBuilder;
    static StringBuilder primSolutionStringBuilder;
    static ArrayList<String> nodeList = new ArrayList<>();

    public static void generateExercise() {
        primExerciseStringBuilder = Terminal.readFile("src/Algorithms/Graphs/MinimalSpanningTrees/Prim/PrimExerciseTemplate.tex");
        primSolutionStringBuilder = Terminal.readFile("src/Algorithms/Graphs/MinimalSpanningTrees/Prim/PrimSolutionTemplate.tex");

        int numNodes = Terminal.rand.nextInt(2) + 13;
        int[][] nodeMatrix = generateNodeMatrix(numNodes);
        generateGraphNodes(nodeMatrix);
        Collections.sort(nodeList);
        int[][] distMatrix = generateDistMatrix(nodeMatrix, numNodes + 2);
        generateGraphEdges(distMatrix);
        generateSolutionEdges(primAlg(distMatrix));

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$PRIMCELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$PRIMCELL$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$PRIMALGORITHM$", "\\newpage\n" + primExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$PRIMALGORITHM$", "\\newpage\n" + primSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    private static int[][] primAlg(int[][] distMatrix) {
        ArrayList<Integer> addedNodes = new ArrayList<>();
        int[][] newDist = new int[distMatrix.length][distMatrix.length];
        addedNodes.add(0);
        while (addedNodes.size() < distMatrix.length - 1) {
            int[] min = new int[]{9999, 9999};
            int[] newMin;
            int maxJ = 0;
            for (int j = 0; j < addedNodes.size(); j++) {
                newMin = findMin(distMatrix[addedNodes.get(j)]);
                if ((newMin[1] < min[1] || (newMin[1] == min[1] && newMin[0] < min[0])) &&
                        newDist[addedNodes.get(j)][newMin[0]] == 0 && !addedNodes.contains(newMin[0])) {
                    min = newMin;
                    maxJ = j;
                }
            }
            newDist[addedNodes.get(maxJ)][min[0]] = distMatrix[addedNodes.get(maxJ)][min[0]];
            distMatrix[addedNodes.get(maxJ)][min[0]] = 0;
            distMatrix[min[0]][addedNodes.get(maxJ)] = 0;
                addedNodes.add(min[0]);
        }
        return newDist;
    }

    private static int[] findMin(int[] arr) {
        int minVal = 9999;
        int minIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < minVal && arr[i] != 0) {
                minVal = arr[i];
                minIndex = i;
            }
        }
        return new int[]{minIndex, minVal};
    }

    public static int[][] generateDistMatrix(int[][] nodeMatrix, int numNodes) {
        int currNode = 0;
        int[][] distMatrix = new int[numNodes][numNodes];
        // connect each node with the node below it
        for (int i = 0; i < nodeMatrix.length - 1; i++) {
            for (int j = 0; j < nodeMatrix[0].length && currNode < numNodes - 1; j++) {
                if (nodeMatrix[i][j] == 1) {
                    if (nodeMatrix[i + 1][j] == 1) {
                        distMatrix[currNode][getNodeNum(nodeMatrix, i + 1, j)] = Terminal.rand.nextInt(8) + 1;
                    }
                    currNode++;
                }
            }
        }
        currNode = 0;
        // connect each node with the node to the right of it
        for (int i = 0; i < nodeMatrix.length; i++) {
            for (int j = 0; j < nodeMatrix[0].length - 1 && currNode < numNodes - 1; j++) {
                currNode = getNodeNum(nodeMatrix, i, j);
                if (nodeMatrix[i][j] == 1 && nodeMatrix[i][j + 1] == 1) {
                    distMatrix[currNode][currNode + 1] = Terminal.rand.nextInt(8) + 1;
                }
            }
        }

        for (int i = 0; i < distMatrix.length; i++) {
            for (int j = 0; j < i; j++) {
                distMatrix[i][j] = distMatrix[j][i];
            }
        }
        return distMatrix;
    }

    private static void generateGraphNodes(int[][] nodeMatrix) {
        int numLabeled = 0;
        for (int i = 0; i < nodeMatrix.length; i++) {
            for (int j = 0; j < nodeMatrix[0].length; j++) {
                if (nodeMatrix[i][j] == 1) {
                    Terminal.replaceinSB(primExerciseStringBuilder, ", draw=none] (" + i + j + ")", "](" + i + j + ")");
                    Terminal.replaceinSB(primSolutionStringBuilder, ", draw=none] (" + i + j + ")", "](" + i + j + ")");
                    Terminal.replaceinSB(primExerciseStringBuilder, "%" + i + j, "" + Character.toString('A' + numLabeled));
                    Terminal.replaceinSB(primSolutionStringBuilder, "%" + i + j, "" + Character.toString('A' + numLabeled));

                    Terminal.replaceinSB(primExerciseStringBuilder, ", draw=none] (" + i + j + ")", "](" + i + j + ")");
                    Terminal.replaceinSB(primExerciseStringBuilder, "%" + i + j, "" + Character.toString('A' + numLabeled));

                    Terminal.replaceinSB(primSolutionStringBuilder, ", draw=none] (" + i + j + ")", "](" + i + j + ")");
                    Terminal.replaceinSB(primSolutionStringBuilder, "%" + i + j, "" + Character.toString('A' + numLabeled++));


                }
            }
        }
    }

    private static int[][] generateNodeMatrix(int numNodes) {
        int[][] nodeMatrix = new int[4][4];
        nodeList.add("00");
        nodeMatrix[0][0] = 1; // first node
        while (numNodes > 0) {
            int x = Terminal.rand.nextInt(nodeMatrix.length);
            int y = Terminal.rand.nextInt(nodeMatrix[0].length);
            if (nodeMatrix[x][y] != 1) {
                nodeList.add("" + x + y);
                nodeMatrix[x][y] = 1;
                numNodes--;
            }
        }
        return nodeMatrix;
    }

    private static void generateGraphEdges(int[][] distMatrix) {
        for (int i = 0; i < distMatrix.length; i++) {
            for (int j = i + 1; j < distMatrix[0].length; j++) {
                if (distMatrix[i][j] != 0) {
                    Terminal.replaceinSB(primExerciseStringBuilder, "%$CONNECTIONS$", "\\path (" + nodeList.get(i)
                            + ") edge node[pos=0.5] {" + distMatrix[i][j] + "} (" + nodeList.get(j) + ");\n%$CONNECTIONS$");
                    Terminal.replaceinSB(primSolutionStringBuilder, "%$CONNECTIONS$", "\\path (" + nodeList.get(i)
                            + ") edge node[pos=0.5] {" + distMatrix[i][j] + "} (" + nodeList.get(j) + ");\n%$CONNECTIONS$");
                }
            }
        }
    }

    private static void generateSolutionEdges(int[][] distMatrix) {
        for (int i = 0; i < distMatrix.length; i++) {
            for (int j = 0; j < distMatrix[0].length; j++) {
                if (distMatrix[i][j] != 0) {
                    Terminal.replaceinSB(primSolutionStringBuilder, "%$PRIMCONNECTIONS$", "\\path (" + nodeList.get(i)
                            + ") edge node[pos=0.5] {" + distMatrix[i][j] + "} (" + nodeList.get(j) + ");\n%$PRIMCONNECTIONS$");
                }
            }
        }
    }
}
