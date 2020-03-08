import Algorithms.Sorting.QuickSort.QuickSort;
import Algorithms.Sorting.RadixSort.RadixSort;
import DataStructures.Sequences.Arrays.DynamicArray;
import DataStructures.Terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TumgadCLI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void main(String[] args) {
        templateSetup();
        print(ANSI_PURPLE +
                "  _______ _    _ __  __  _____          _____  \n" +
                " |__   __| |  | |  \\/  |/ ____|   /\\   |  __ \\ \n" +
                "    | |  | |  | | \\  / | |  __   /  \\  | |  | |\n" +
                "    | |  | |  | | |\\/| | | |_ | / /\\ \\ | |  | |\n" +
                "    | |  | |__| | |  | | |__| |/ ____ \\| |__| |\n" +
                "    |_|   \\____/|_|  |_|\\_____/_/    \\_\\_____/" + ANSI_RESET);
        System.out.println("Which exercises should be generated? Please select the corresponding shorthands seperated by SPACES or " + ANSI_PURPLE + "X" + ANSI_RESET + " for everything");
        System.out.println("Reminder: Each exercise can only be generated ONCE");
        chooseExercises();
        generateLatex();
    }

    private static void templateSetup() {
        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/ExerciseTemplate.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/SolutionTemplate.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "$GENERATEDDATE$", new Date().toString());
        Terminal.replaceinSB(solutionStringBuilder, "$GENERATEDDATE$", new Date().toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    private static void generateLatex() {
        final ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(() -> {
            try {
                generateExercises();
            } catch (IOException e) {
                error("There was an error while generating the LaTeX Exercises, please try again");
            }
        });
        pool.execute(() -> {
            try {
                generateSolutions();
            } catch (IOException e) {
                error("There was an error while generating the LaTeX Solutions, please try again");
            }
        });
        pool.shutdown();
    }

    private static void generateExercises() throws IOException {
        Process process = Runtime.getRuntime().exec("pdflatex -output-directory=docs docs/Exercises.tex");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        reader.close();
    }

    private static void generateSolutions() throws IOException {
        Process process = Runtime.getRuntime().exec("pdflatex -output-directory=docs docs/Solutions.tex");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        reader.close();
    }

    private static void error(String errorText) {
        System.err.println(new Date().toString() + ": " + errorText);
    }

    private static void print(String text) {
        System.out.println(text);
    }

    private static void chooseExercises() {
        System.out.println(
                "Dynamic Arrays: " + ANSI_PURPLE + "DA" + ANSI_RESET + "\n" +
                        "MergeSort: " + ANSI_PURPLE + "MS" + ANSI_RESET + "\n" +
                        "QuickSort: " + ANSI_PURPLE + "QS" + ANSI_RESET + "\n" +
                        "RadixSort: " + ANSI_PURPLE + "RS" + ANSI_RESET + "\n" +
                        "Hashing (Chaining): " + ANSI_PURPLE + "HC" + ANSI_RESET + "\n" +
                        "Hashing (Double): " + ANSI_PURPLE + "HD" + ANSI_RESET + "\n" +
                        "Hashing (Perfect): " + ANSI_PURPLE + "HP" + ANSI_RESET + "\n" +
                        "Breadth-First-Search: " + ANSI_PURPLE + "BFS" + ANSI_RESET + "\n" +
                        "Depth-First-Search: " + ANSI_PURPLE + "DFS" + ANSI_RESET + "\n" +
                        "AB Trees: " + ANSI_PURPLE + "ABT" + ANSI_RESET + "\n" +
                        "AVL Trees: " + ANSI_PURPLE + "AVLT" + ANSI_RESET + "\n" +
                        "Binary Heaps: " + ANSI_PURPLE + "BH" + ANSI_RESET + "\n" +
                        "Binomial Heaps: " + ANSI_PURPLE + "BNH" + ANSI_RESET + "\n" +
                        "Dijkstra: " + ANSI_PURPLE + "D" + ANSI_RESET + "\n" +
                        "Prim: " + ANSI_PURPLE + "P" + ANSI_RESET + "\n" +
                        "Floyd-Warshall: " + ANSI_PURPLE + "FW" + ANSI_RESET
        );
        Scanner input = new Scanner(System.in);
        String answer = input.nextLine();
        String[] options;
        try {
            if (answer.equalsIgnoreCase("X")) {
                options = new String[]{"DA", "MS", "QS", "RS", "HC", "HD", "HP", "BFS", "DFS", "ABT", "AVLT", "BH", "BNH", "D", "P", "FW"};
            } else {
                options = answer.split(" ");
                options = new HashSet<String>(Arrays.asList(options)).toArray(new String[0]); // remove duplicates
            }
            for (int i = 0; i < options.length; i++) {
                String option = options[i];
                switch (option) {
                    case "DA":
                        System.out.println(ANSI_PURPLE + "Generating Dynamic Array" + ANSI_RESET);
                        gernerateDynamicArray();
                        break;
                    case "MS":
                        say("MergeSort can not be generated yet, still in development");
                        break;
                    case "QS":
                        System.out.println(ANSI_PURPLE + "Generating QuickSort" + ANSI_RESET);
                        QuickSort.generateExercise();
                        break;
                    case "RS":
                        System.out.println(ANSI_PURPLE + "Generating RadixSort" + ANSI_RESET);
                        RadixSort.generateExercise();
                        break;
                    case "HC":
                        say("Hashing (Chaining) can not be generated yet, still in development");
                        break;
                    case "HD":
                        say("Hashing (Double) can not be generated yet, still in development");
                        break;
                    case "HP":
                        say("Hashing (Perfect) can not be generated yet, still in development");
                        break;
                    case "BFS":
                        say("Breadth First Search can not be generated yet, still in development");
                        break;
                    case "DFS":
                        say("Depth First Search can not be generated yet, still in development");
                        break;
                    case "ABT":
                        say("AB Trees can not be generated yet, still in development");
                        break;
                    case "AVLT":
                        say("AVL Trees can not be generated yet, still in development");
                        break;
                    case "BH":
                        say("Binary Heapy can not be generated yet, still in development");
                        break;
                    case "BNH":
                        say("Binomial Heaps can not be generated yet, still in development");
                        break;
                    case "D":
                        say("Dijkstra's algorithm can not be generated yet, still in development");
                        break;
                    case "P":
                        say("Prim's algorithm can not be generated yet, still in development");
                        break;
                    case "FW":
                        say("Floyd-Warshall can not be generated yet, still in development");
                        break;
                    default:
                        error("There is no exercise with the shorthand " + option);
                        break;
                }
            }
        } catch (Exception e) {
            error("Invalid selection, please follow the steps and try again");
            System.exit(1);
        }
    }

    private static void gernerateDynamicArray() {
        try { // Incredibly ugly, only until we figure out the bug
            DynamicArray.generateExercise();
        } catch (IndexOutOfBoundsException e) {
            try { // But this decreases the chance of failure substantially
                DynamicArray.generateExercise();
            } catch (IndexOutOfBoundsException e2) {
                try {
                    DynamicArray.generateExercise();
                } catch (IndexOutOfBoundsException e3) {
                    try {
                        DynamicArray.generateExercise();
                    } catch (IndexOutOfBoundsException e4) {
                        DynamicArray.generateExercise();
                    }
                }
            }
        }
    }

    private static void generateEverything() {
        DynamicArray.generateExercise();
        // MergeSort
        QuickSort.generateExercise();
        RadixSort.generateExercise();
        // Rest
    }

    private static void say(String toSay) {
        System.out.println(new Date().toString() + ": " + toSay);
    }
}
