package DataStructures.Sequences.Arrays;

import Util.Terminal;

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
        int alpha = Terminal.rand.nextInt(2) + 3;
        int beta;
        while (alpha == (beta = Terminal.rand.nextInt(2) + 2)) {
        }
        int initialSize = Terminal.rand.nextInt(5) + 4;
        return new DynamicArray(beta, alpha, initialSize);
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
        StringBuilder arrayExerciseStringBuilder = Terminal.readFile("src/DataStructures/Sequences/Arrays/ArraysExerciseTemplate.tex");
        StringBuilder arraySolutionStringBuilder = Terminal.readFile("src/DataStructures/Sequences/Arrays/ArraysSolutionTemplate.tex");

        Terminal.replaceinSB(arrayExerciseStringBuilder, "$INITARRAY$", exerciseArray.arrayToTable());
        Terminal.replaceinSB(arraySolutionStringBuilder, "$INITARRAY$", exerciseArray.arrayToTable());

        Terminal.replaceinSB(arraySolutionStringBuilder, "INITIALWVALUE", "" + exerciseArray.getB().length);
        Terminal.replaceinSB(arraySolutionStringBuilder, "INITIALNVALUE", "" + exerciseArray.getN());
        Terminal.replaceinSB(arraySolutionStringBuilder, "INITIALBETAVALUE", "" + exerciseArray.getBeta());

        // generate the initial sequence of pop operations on the array
        int numPops = exerciseArray.getN() - 1;
        for (int i = 0; i < numPops; i++) {
            exerciseArray.pop();
            // TODO 06/03/2020 sebas: Fix indexoutofbounds after pop operations reallocate
            Terminal.replaceinSB(arrayExerciseStringBuilder, "$EXERCISEBGENERATION$", exerciseArray.arrayToTable() + "\\vspace{10px}\\\\\n" + "$EXERCISEBGENERATION$\n");
            Terminal.replaceinSB(arraySolutionStringBuilder, "$EXERCISEBGENERATION$", exerciseArray.arrayToTable() + "\\vspace{10px}\\\\\n" + "$EXERCISEBGENERATION$\n");
        }

        for (int i = 0; i < 3; i++) {
            exerciseArray.push(Terminal.rand.nextInt(100));
            Terminal.replaceinSB(arrayExerciseStringBuilder, "$EXERCISEBGENERATION$", exerciseArray.arrayToTable() + "\\vspace{10px}\\\\\n" + "$EXERCISEBGENERATION$\n");
            Terminal.replaceinSB(arraySolutionStringBuilder, "$EXERCISEBGENERATION$", exerciseArray.arrayToTable() + "\\vspace{10px}\\\\\n" + "$EXERCISEBGENERATION$\n");
        }
        exerciseArray.push(Terminal.rand.nextInt(100));
        Terminal.replaceinSB(arrayExerciseStringBuilder, "$EXERCISEBGENERATION$", exerciseArray.arrayToTable() + "\\vspace{10px}\\\\\n");
        Terminal.replaceinSB(arraySolutionStringBuilder, "$EXERCISEBGENERATION$", exerciseArray.arrayToTable() + "\\vspace{10px}\\\\\n");

        // Generating the Array for exercise c
        exerciseArray = generateRandomArray();
        exerciseArray.fillArrayRandom();

        Terminal.replaceinSB(arrayExerciseStringBuilder, "BETACVALUE", "" + exerciseArray.getBeta());
        Terminal.replaceinSB(arrayExerciseStringBuilder, "ALPHACVALUE", "" + exerciseArray.getAlpha());
        Terminal.replaceinSB(arrayExerciseStringBuilder, "$ARRAYCGENERATION$", exerciseArray.arrayToTable());

        Terminal.replaceinSB(arraySolutionStringBuilder, "BETACVALUE", "" + exerciseArray.getBeta());
        Terminal.replaceinSB(arraySolutionStringBuilder, "ALPHACVALUE", "" + exerciseArray.getAlpha());
        Terminal.replaceinSB(arraySolutionStringBuilder, "$ARRAYCGENERATION$", exerciseArray.arrayToTable());

        // The number of elements in the array determines the number of operations we will generate
        numPops = exerciseArray.getN() - 1;
        String exerciseCOperations = "";
        for (int i = 0; i < numPops; i++) {
            Terminal.replaceinSB(arraySolutionStringBuilder, "$OPERATIONSCGENERATION$", "\\vspace{10px}pop():\n");
            exerciseArray.pop();
            Terminal.replaceinSB(arraySolutionStringBuilder, "$ARRAYSCGENERATION$", exerciseArray.arrayToTable()
                    + "\\\\\n$OPERATIONSCGENERATION$\\\\\n$ARRAYSCGENERATION$");
            exerciseCOperations += "pop(), ";
        }
        for (int i = 0; i < 4; i++) {
            int pushValue = Terminal.rand.nextInt(100);
            Terminal.replaceinSB(arraySolutionStringBuilder, "$OPERATIONSCGENERATION$", "\\vspace{10px}push(" + pushValue + "):\n");
            exerciseArray.push(pushValue);
            Terminal.replaceinSB(arraySolutionStringBuilder, "$ARRAYSCGENERATION$", exerciseArray.arrayToTable()
                    + "\\\\\n$OPERATIONSCGENERATION$\\\\\n$ARRAYSCGENERATION$");
            exerciseCOperations += "push(" + pushValue + "), ";
        }
        Terminal.replaceinSB(arraySolutionStringBuilder, "$OPERATIONSCGENERATION$", "\\vspace{10px}pop():\n");
        exerciseArray.pop();
        Terminal.replaceinSB(arraySolutionStringBuilder, "$ARRAYSCGENERATION$", exerciseArray.arrayToTable());

        exerciseCOperations += "pop()";
        Terminal.replaceinSB(arrayExerciseStringBuilder, "$OPERATIONSCGENERATION$", exerciseCOperations);

        for (int i = 0; i < 9; i++) {
            Terminal.replaceinSB(arrayExerciseStringBuilder, "$ARRAYSCGENERATION$", EMPTY_MAX_TABLE + "\\vspace{10px}\\\\\n" + "$ARRAYSCGENERATION$\n");
        }

        Terminal.replaceinSB(arrayExerciseStringBuilder, "$ARRAYSCGENERATION$", EMPTY_MAX_TABLE + "\\\\");

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        // Placeholder is commented, so that it doesn't show if the exercise is not selected
        Terminal.replaceinSB(exerciseStringBuilder, "%$DYNAMICARRAYS$", "\\newpage\n" + arrayExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$DYNAMICARRAYS$", "\\newpage\n" + arraySolutionStringBuilder.toString());
        Terminal.replaceinSB(exerciseStringBuilder, "%$ARRAYSCELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$ARRAYSCELL$", "\\cellcolor{tumgadRed}");
        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    /**
     * Insert an element into the Dynamic Array
     *
     * @param x the integer element you want to insert into the dynamic array
     */
    public void push(int x) {
        if (n == b.length) {
            reallocate(beta * b.length);
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
        if (n * alpha <= b.length) {
            reallocate(b.length / beta);
        }
        return b[n];
    }

    /**
     * creates a new array with the updated length and copies the values to that array
     */
    private void reallocate(int newLength) {
        int[] btick = new int[newLength];
        for (int i = 0; i < n; i++) {
            btick[i] = b[i];
        }
        b = btick;
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
        int numInserts = Terminal.rand.nextInt(5) + 2;
        for (int i = 0; i < numInserts; i++) {
            push(Terminal.rand.nextInt(100));
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
