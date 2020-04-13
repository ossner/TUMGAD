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

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$PRIMCELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$PRIMCELL$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$PRIMALGORITHM$", "\\newpage\n" + primExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$PRIMALGORITHM$", "\\newpage\n" + primSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
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
