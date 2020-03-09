package Algorithms.Hashing.Double;

import DataStructures.Terminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DoubleHashing {
    static StringBuilder doubleHashingExerciseStringBuilder;
    static StringBuilder doubleHashingSolutionStringBuilder;


    public static void main(String[] args) {
        generateExercise();
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
        int[] primes = {5, 7, 11};
        Random rand = new Random();
        int[] a = new int[2];
        a[0] = primes[rand.nextInt(3)];
        a[1] = primes[rand.nextInt(3)];
        return a;
    }

    public static void generateExercise() {
        doubleHashingExerciseStringBuilder = Terminal.readFile("src/Algorithms/Hashing/Double/DoubleHashingExerciseTemplate.tex");
        doubleHashingSolutionStringBuilder = Terminal.readFile("src/Algorithms/Hashing/Double/DoubleHashingSolutionTemplate.tex");

        int[] hash1 = generateH1Function();
        int[] hash2 = generateH2Function();

        Terminal.replaceinSB(doubleHashingExerciseStringBuilder, "NORMALFUNCTION", "h(x) = (" + hash1[0] + "x + " + hash1[1] + ") \\mod 11");
        Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "NORMALFUNCTION", "h(x) = (" + hash1[0] + "x + " + hash1[1] + ") \\mod 11");

        Terminal.replaceinSB(doubleHashingExerciseStringBuilder, "COLLISIONFUNCTION", "h'(x) = " + hash2[0] + " - (x \\mod " + hash2[1] + ") ");
        Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "COLLISIONFUNCTION", "h'(x) = " + hash2[0] + " - (x \\mod " + hash2[1] + ") ");

        int[] numbers = Terminal.generateRandomArray(7, 7);
        int numOfFirstInsertions = new Random().nextInt(3) + 4;
        int numOfSecondInsertions = 7 - numOfFirstInsertions;
        Integer[] firstInsertions = new Integer[numOfFirstInsertions];

        for (int i = 0; i < numOfFirstInsertions; i++) {
            firstInsertions[i] = numbers[i];
        }

        List<Integer> deletions = new ArrayList<>(Arrays.asList(firstInsertions));
        while (deletions.size() > 3) {
            deletions.remove(new Random().nextInt(deletions.size()));
        }
        int[] deletionsArr = new int[3];
        for (int i = 0; i < deletions.size(); i++) {
            deletionsArr[i] = deletions.get(i);
        }
        int[] secondInsertions = new int[numOfSecondInsertions];
        int j = 0;
        for (int i = numOfFirstInsertions + 1; i < numbers.length; i++) {
            secondInsertions[j++] = numbers[i];
        }
        int k = 0;
        for (int i = j; i < numOfSecondInsertions; i++) {
            secondInsertions[i] = deletionsArr[k++];
        }

        Terminal.replaceinSB(doubleHashingExerciseStringBuilder, "$FIRSTINSERTIONS$", Terminal.printArray(firstInsertions));
        Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "$FIRSTINSERTIONS$", Terminal.printArray(firstInsertions));

        Terminal.replaceinSB(doubleHashingExerciseStringBuilder, "$DELETIONS$", Terminal.printArray(deletionsArr));
        Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "$DELETIONS$", Terminal.printArray(deletionsArr));

        Terminal.replaceinSB(doubleHashingExerciseStringBuilder, "$SECONDINSERTIONS$", Terminal.printArray(secondInsertions));
        Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "$SECONDINSERTIONS$", Terminal.printArray(secondInsertions));

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$DoubleHashing$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$DoubleHashing$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$DOUBLEHASHING$", "\\newpage\n" + doubleHashingExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$DOUBLEHASHING$", "\\newpage\n" + doubleHashingSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }
}
