package DataStructures.Sequences.Arrays;

import DataStructures.Terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class represents Dynamic Array (read about it in the md),
 * an unbounded Array that changes its size based on its current size
 * and some other factors
 */
public class DynamicArray {
    // An empty table with the maximum number of entries (i.e. print template for solutions)
    private static final String EMPTY_MAX_TABLE =
            "\\begin{tabular}{|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|} \\hline \n" +
                    "0& 1& 2& 3& 4& 5& 6& 7& 8& 9& 10& 11& 12& 13\\\\ \\hline \n" +
                    " & & & & & & & & & & & & & \\\\ \\hline \n" +
                    "\\end{tabular}";
    private int beta; // growth factor
    private int alpha; // storage overhead
    private int n; // current number of elements in array
    private int[] b;

    /**
     * Initially everything needed to know is the
     * growth factor and the storage overhead
     */
    public DynamicArray(int beta, int alpha, int initialSize) {
        this.beta = beta;
        this.alpha = alpha;
        this.n = 0;
        this.b = new int[initialSize];
    }

    /**
     * Generates a new dynamic array with random values for alpha, beta and the initialSize
     */
    public static DynamicArray generateRandomArray() {
        int alpha = new Random().nextInt(2) + 3;
        int beta = new Random().nextInt(2) + 2;
        int initialSize = new Random().nextInt(5) + 3;
        return new DynamicArray(beta, alpha, initialSize);
    }

    // TODO 06/03/2020 sebas: remove main and implement exercise generation with the CLI
    public static void main(String[] args) {
        generateExercise();
        try {
            Process process = Runtime.getRuntime().exec("pdflatex -output-directory=src/DataStructures/Sequences/Arrays src/DataStructures/Sequences/Arrays/ArraysExercise.tex");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates an exercise and the matching solution for dynamic Arrays
     * First a random dynamic Array is generated, filled with dummy values
     * then the exercises and solutions for a,b are generated
     * lastly, a new array for the exercise c is generated and empty tables for the exercise
     * or (as the case may be) the solution tables
     */
    public static void generateExercise() {
        // Generating the initial Array
        DynamicArray exerciseArray = generateRandomArray();
        exerciseArray.fillArrayRandom();

        // Writing the initial Array into the tex file
        StringBuilder exerciseStringBuilder = Terminal.readFile("src/DataStructures/Sequences/Arrays/ArraysExerciseTemplate.tex");
        Terminal.replaceinSB(exerciseStringBuilder, "$INITARRAY$", exerciseArray.arrayToTable());

        // generate the initial sequence of pop operations on the array
        int numPops = exerciseArray.getN() - 1;
        for (int i = 0; i < numPops; i++) {
            System.out.println(exerciseArray.alpha + " " + exerciseArray.beta);
            exerciseArray.pop();
            // TODO 06/03/2020 sebas: Fix indexoutofbounds after pop operations reallocate
            Terminal.replaceinSB(exerciseStringBuilder, "$EXERCISEBGENERATION$", exerciseArray.arrayToTable() + "\\vspace{10px}\\\\\n" + "$EXERCISEBGENERATION$\n");
        }

        for (int i = 0; i < 3; i++) {
            exerciseArray.push(new Random().nextInt(100));
            Terminal.replaceinSB(exerciseStringBuilder, "$EXERCISEBGENERATION$", exerciseArray.arrayToTable() + "\\vspace{10px}\\\\\n" + "$EXERCISEBGENERATION$\n");
        }
        exerciseArray.push(new Random().nextInt(100));
        Terminal.replaceinSB(exerciseStringBuilder, "$EXERCISEBGENERATION$", exerciseArray.arrayToTable() + "\\vspace{10px}\\\\\n");

        // Generating the Array for exercise c
        exerciseArray = generateRandomArray();
        exerciseArray.fillArrayRandom();

        Terminal.replaceinSB(exerciseStringBuilder, "BETACVALUE", "" + exerciseArray.getBeta());
        Terminal.replaceinSB(exerciseStringBuilder, "ALPHACVALUE", "" + exerciseArray.getAlpha());
        Terminal.replaceinSB(exerciseStringBuilder, "$ARRAYCGENERATION$", exerciseArray.arrayToTable());

        // The number of elements in the array determines the number of operations we will generate
        numPops = exerciseArray.getN() - 1;
        String exerciseCOperations = "";
        for (int i = 0; i < numPops; i++) {
            // TODO 06/03/2020 sebas: simulate pop for solution
            exerciseCOperations += "pop(), ";
        }
        for (int i = 0; i < 4; i++) {
            int pushValue = new Random().nextInt(100);
            // TODO 06/03/2020 sebas: simulate push of values in exerciseArray for solution
            exerciseCOperations += "push(" + pushValue + "), ";
        }
        exerciseCOperations += "pop()";
        Terminal.replaceinSB(exerciseStringBuilder, "$OPERATIONSCGENERATION$", exerciseCOperations);

        for (int i = 0; i < 9; i++) {
            Terminal.replaceinSB(exerciseStringBuilder, "$ARRAYSCGENERATION$", EMPTY_MAX_TABLE + "\\vspace{10px}\\\\\n" + "$ARRAYSCGENERATION$\n");
        }

        Terminal.replaceinSB(exerciseStringBuilder, "$ARRAYSCGENERATION$", EMPTY_MAX_TABLE + "\\\\");
        Terminal.saveToFile("src/DataStructures/Sequences/Arrays/ArraysExercise.tex", exerciseStringBuilder);
    }

    /**
     * Insert an element into the Dynamic Array
     *
     * @param x the integer element you want to insert into the dynamic array
     */
    public void push(int x) {
        if (n == b.length) {
            int[] btick = new int[beta * b.length];
            copyValues(b, btick);
            b = btick;
        }
        b[n++] = x;
    }

    /**
     * removes and element from the array and decrements the value of n
     *
     * @return the element at index n-1
     */
    public int pop() {
        n--;
        if (n *alpha <= b.length) {
            int[] btick = new int[b.length / beta];
            copyValues(b, btick);
            b = btick;
        }
        return b[n];
    }

    /**
     * copies the values from the first array into the second array
     *
     * @param from the origin array
     * @param to   the destination array
     */
    private void copyValues(int[] from, int[] to) {
        for (int i = 0; i < n && i < to.length; i++) {
            to[i] = from[i];
        }
    }

    /**
     * generates a LaTeX table from the current Array
     */
    private String arrayToTable() {
        String ret = "\\begin{tabular}{|"; // Initial setup and begin of the table
        for (int i = 0; i < b.length; i++) { // Defining the number and spacing of columns (number = b.length)
            ret += "P{0.65cm}|";
        }
        ret += "} \\hline \n";
        ret += 0;
        if (b.length == 1) { // If the array is too small it generates a latex error after a pop operation
            ret += " & ";
        }
        for (int i = 1; i < b.length; i++) {
            ret += " & " + i;
        }
        ret += "\\\\ \\hline \n";
        ret += b[0];
        for (int i = 1; i < n; i++) {
            ret += " & " + b[i];
        }
        for (int i = n; i < b.length; i++) {
            ret += " & ";
        }
        ret += "\\\\ \\hline \n";
        return ret + "\\end{tabular}";
    }

    public void fillArrayRandom() {
        int numInserts = new Random().nextInt(5) + 2;
        for (int i = 0; i < numInserts; i++) {
            push(new Random().nextInt(100));
        }
    }

    public int[] getB() {
        return b;
    }

    public int getBeta() {
        return beta;
    }

    public int getAlpha() {
        return alpha;
    }

    public int getN() {
        return n;
    }
}
