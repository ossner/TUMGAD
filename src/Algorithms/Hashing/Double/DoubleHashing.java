package Algorithms.Hashing.Double;

import DataStructures.Terminal;

import java.util.Random;

public class DoubleHashing {
    static StringBuilder doubleHashingExerciseStringBuilder;
    static StringBuilder doubleHashingSolutionStringBuilder;


    public static void main(String[] args) {
        System.out.println(Terminal.printArray(generateH2Function()));
    }

    static int[] generateH1Function() {
        Random rand = new Random();
        int[] a = new int[2];
        a[0] = (rand.nextInt(10) + 1);
        a[1] = (rand.nextInt(10));
        return a;
    }

    //h2(x) = prime1 - (x % prime2)
    static int[] generateH2Function() {
        int[] primes = {5,7,11};
        Random rand = new Random();
        int[] a = new int[2];
        a[0] = primes[rand.nextInt(3)];
        a[1] = primes[rand.nextInt(3)];
        return a;
    }

    static void generateExercise() {
        doubleHashingExerciseStringBuilder = Terminal.readFile("src/Algorithms/Hashing/Double/DoubleHashingExerciseTemplate.tex");
        doubleHashingSolutionStringBuilder = Terminal.readFile("src/Algorithms/Hashing/Double/DoubleHashingSolutionTemplate.tex");

        int[] a = generateH1Function();

        Terminal.replaceinSB(doubleHashingExerciseStringBuilder, "NORMALFUNCTION", "h(x) = (" + a[0] + "x + " + a[1] + ") \\mod 11");
        Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "NORMALFUNCTION", "h(x) = (" + a[0] + "x + " + a[1] + ") \\mod 11");

        Terminal.saveToFile("src/Algorithms/Hashing/Double/DoubleHashingExercise.tex", doubleHashingExerciseStringBuilder);
        Terminal.saveToFile("src/Algorithms/Hashing/Double/DoubleHashingSolution.tex", doubleHashingSolutionStringBuilder);
    }
}
