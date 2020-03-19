package DataStructures.PriorityQueues.BinomialHeaps;

import Util.Terminal;

public class BinomialHeap {

    static StringBuilder binomialHeapExerciseStringbuilder;
    static StringBuilder binomialHeapSolutionStringbuilder;

    public static void generateExercise() {

        binomialHeapExerciseStringbuilder = Terminal.readFile("src/DataStructures/PriorityQueues/BinomialHeaps/BinomialHeapExerciseTemplate.tex");
        binomialHeapSolutionStringbuilder = Terminal.readFile("src/DataStructures/PriorityQueues/BinomialHeaps/BinomialHeapSolutionTemplate.tex");

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$BINOMIALHEAPCELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$BINOMIALHEAPCELL$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$BINOMIALHEAPS$", "\\newpage\n" + binomialHeapExerciseStringbuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$BINOMIALHEAPS$", "\\newpage\n" + binomialHeapSolutionStringbuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }
}
