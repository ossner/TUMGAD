package DataStructures.Sequences.Arrays;

import DataStructures.Terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Class represents Dynamic Array (read about it in the md),
 * an unbounded Array that changes its size based on its current size
 * and some other factors
 */
public class DynamicArray {
    // An empty table with the maximum number of entries (i.e. print template for solutions)
    private final String EMPTY_MAX_TABLE = "" +
            "\\begin{tabular}{|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|P{0.65cm}|} \\hline \n" +
            "0& 1& 2& 3& 4& 5& 6& 7& 8& 9& 10& 11& 12& 13\\\\ \\hline \n" +
            "58& 20& 47& 73& 63& 80& & & & & & & & \\\\ \\hline \n" +
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

    public static DynamicArray generateRandomArray() {
        int beta = new Random().nextInt(2) + 2;
        int alpha = new Random().nextInt(3) + 2;
        int initialSize = new Random().nextInt(5) + 3;
        return new DynamicArray(beta, alpha, initialSize);
    }

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

    public static void generateExercise() {
        // Generating the initial Array
        DynamicArray exerciseArray = generateRandomArray();
        exerciseArray.fillArrayRandom();

        // Writing the initial Array into the tex file
        StringBuilder sb = Terminal.readFile("src/DataStructures/Sequences/Arrays/ArraysExerciseTemplate.tex");
        int start = sb.indexOf("$INITARRAY$");
        sb.replace(start, start + 11, exerciseArray.arrayToTable());

        // Generating the Array for exercise c
        exerciseArray = generateRandomArray();
        exerciseArray.fillArrayRandom();

        start = sb.indexOf("BETACVALUE");
        sb.replace(start, start + 10, "" + exerciseArray.getBeta());

        start = sb.indexOf("ALPHACVALUE");
        sb.replace(start, start + 11, "" + exerciseArray.getAlpha());

        start = sb.indexOf("$ARRAYCGENERATION$");
        sb.replace(start, start + 18, "" + exerciseArray.arrayToTable());

        Terminal.saveToFile("src/DataStructures/Sequences/Arrays/ArraysExercise.tex", sb);
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

    public int pop() {
        if (n - 1 <= b.length / alpha) {
            int[] btick = new int[b.length / beta];
            copyValues(b, btick);
            b = btick;
        }
        return b[--n];
    }

    /**
     * copies the values from the first array into the second array
     *
     * @param from the origin array
     * @param to   the destination array
     */
    private void copyValues(int[] from, int[] to) {
        for (int i = 0; i < n; i++) {
            to[i] = from[i];
        }
    }

    /**
     * generates a LaTeX table from the current Array
     */
    private String arrayToTable() {
        String ret = "\\begin{tabular}{|";
        for (int i = 0; i < b.length; i++) {
            ret += "P{0.65cm}|";
        }
        ret += "} \\hline \n";
        ret += 0;
        for (int i = 1; i < b.length; i++) {
            ret += "& " + i;
        }
        ret += "\\\\ \\hline \n";
        ret += b[0];
        for (int i = 1; i < n; i++) {
            ret += "& " + b[i];
        }
        for (int i = n; i < b.length; i++) {
            ret += "& ";
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
