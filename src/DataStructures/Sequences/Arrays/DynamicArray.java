package DataStructures.Sequences.Arrays;

import Util.Terminal;

/**
 * Class represents Dynamic Array (read about it in the md),
 * an unbounded Array that changes its size based on its current size
 * and some other factors
 */
public class DynamicArray {
    private static StringBuilder arrayExerciseStringBuilder;
    private static StringBuilder arraySolutionStringBuilder;

    /**
     * Generates an exercise and the matching solution for dynamic Arrays
     * First a random dynamic Array is generated, filled with dummy values
     * then the exercises and solutions for a,b are generated
     * lastly, a new array for the exercise c is generated and empty tables for the exercise
     * or (as the case may be) the solution tables
     */
    public static void generateExercise() {
        arrayExerciseStringBuilder = Terminal.readFile("src/DataStructures/Sequences/Arrays/ArraysExerciseTemplate.tex");
        arraySolutionStringBuilder = Terminal.readFile("src/DataStructures/Sequences/Arrays/ArraysSolutionTemplate.tex");

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$DYNAMICARRAYS$", "\\newpage\n" + arrayExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$DYNAMICARRAYS$", "\\newpage\n" + arraySolutionStringBuilder.toString());

        Terminal.replaceinSB(exerciseStringBuilder, "%$ARRAYSCELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$ARRAYSCELL$", "\\cellcolor{tumgadRed}");

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }
}
