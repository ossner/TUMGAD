package Algorithms.Graphs.APSP.FloydWarshall;

import Util.Terminal;

public class FloydWarshall {

    private static StringBuilder fwExerciseStringBuilder;
    private static StringBuilder fwSolutionStringBuilder;

    public static void generateExercise() {
        fwExerciseStringBuilder = Terminal.readFile("src/Algorithms/Graphs/APSP/FloydWarshall/FWExerciseTemplate.tex");
        fwSolutionStringBuilder = Terminal.readFile("src/Algorithms/Graphs/APSP/FloydWarshall/FWSolutionTemplate.tex");



        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$APSPFWCELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$APSPFWCELL$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$FLOYDWARSHALL$", "\\newpage\n" + fwExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$FLOYDWARSHALL$", "\\newpage\n" + fwSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }
}
