package Algorithms.Graphs.MinimalSpanningTrees.Prim;

import Util.Terminal;

import java.util.ArrayList;
import java.util.Collections;

import static Algorithms.Graphs.ShortestPaths.Dijkstra.getNodeNum;

public class Prim {
    static StringBuilder primExerciseStringBuilder;
    static StringBuilder primSolutionStringBuilder;

    public static void generateExercise() {
        primExerciseStringBuilder = Terminal.readFile("src/Algorithms/Graphs/MinimalSpanningTrees/Prim/PrimExerciseTemplate.tex");
        primSolutionStringBuilder = Terminal.readFile("src/Algorithms/Graphs/MinimalSpanningTrees/Prim/PrimSolutionTemplate.tex");

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$PRIMCELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$PRIMCELL$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$PRIMALGORITHM$", "\\newpage\n" + primExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$PRIMALGORITHM$", "\\newpage\n" + primSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }
}
