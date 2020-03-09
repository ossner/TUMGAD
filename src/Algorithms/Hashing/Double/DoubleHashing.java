package Algorithms.Hashing.Double;

import DataStructures.Terminal;

import java.util.Random;

public class DoubleHashing {
    static StringBuilder doubleHashingExerciseStringBuilder;
    static StringBuilder doubleHashingSolutionStringBuilder;


    public static void main(String[] args) {
        generateExercise();
    }

    static int[] generateHashfunction() {
        Random rand = new Random();
        int[] a = new int[2];
        a[0] = (rand.nextInt(10) + 1);
        a[1] = (rand.nextInt(10));
        return a;
    }

    static void generateExercise() {
        doubleHashingExerciseStringBuilder = Terminal.readFile("src/Algorithms/Hashing/Double/DoubleHashingExerciseTemplate.tex");
        doubleHashingSolutionStringBuilder = Terminal.readFile("src/Algorithms/Hashing/Double/DoubleHashingSolutionTemplate.tex");

        int[] a = generateHashfunction();

        Terminal.replaceinSB(doubleHashingExerciseStringBuilder, "NORMALFUNCTION", "h(x) = (" + a[0] + "x + " + a[1] + ") \\mod 11");
        Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "NORMALFUNCTION", "h(x) = (" + a[0] + "x + " + a[1] + ") \\mod 11");

        Terminal.saveToFile("src/Algorithms/Hashing/Double/DoubleHashingExercise.tex", doubleHashingExerciseStringBuilder);
        Terminal.saveToFile("src/Algorithms/Hashing/Double/DoubleHashingSolution.tex", doubleHashingSolutionStringBuilder);
    }
}
