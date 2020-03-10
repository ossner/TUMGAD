package Algorithms.Hashing.Chaining;

import Algorithms.Hashing.Double.DoubleHashing;
import DataStructures.Terminal;

public class HashingChaining {
    static StringBuilder hashingChainingExerciseStringBuilder;
    static StringBuilder hashingChainingSolutionStringBuilder;
    static int[] hashFunction = DoubleHashing.generateH1Function();
    public static void main(String[] args) {

    }

    public static void generateExercise() {
        hashingChainingExerciseStringBuilder = Terminal.readFile("src/Algorithms/Hashing/Double/HashingChainingExerciseTemplate.tex");
        hashingChainingSolutionStringBuilder = Terminal.readFile("src/Algorithms/Hashing/Double/HashingChainingSolutionTemplate.tex");

        hashFunction = DoubleHashing.generateH1Function();

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$HashingChaining$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$HashingChaining$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$HASHINGCHAINING$", "\\newpage\n" + hashingChainingExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$HASHINGCHAINING$", "\\newpage\n" + hashingChainingSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }
}
