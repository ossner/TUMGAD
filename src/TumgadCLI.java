import Algorithms.Sorting.QuickSort.QuickSort;
import Algorithms.Sorting.RadixSort.RadixSort;
import DataStructures.Sequences.Arrays.DynamicArray;
import DataStructures.Terminal;

import java.io.*;
import java.util.Date;
import java.util.Scanner;

public class TumgadCLI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        templateSetup();
        print(ANSI_PURPLE +
                "  _______ _    _ __  __  _____          _____  \n" +
                " |__   __| |  | |  \\/  |/ ____|   /\\   |  __ \\ \n" +
                "    | |  | |  | | \\  / | |  __   /  \\  | |  | |\n" +
                "    | |  | |  | | |\\/| | | |_ | / /\\ \\ | |  | |\n" +
                "    | |  | |__| | |  | | |__| |/ ____ \\| |__| |\n" +
                "    |_|   \\____/|_|  |_|\\_____/_/    \\_\\_____/" + ANSI_RESET);
        // TODO 06/03/2020 sebas: insert interactive check if user wants to generate the specific exercise

        System.out.println("Which exercizes should be generated?");
        chooseExercizes(input);
        /*try { // Incredibly ugly, only until we figure out the bug
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
        QuickSort.generateExercise();
        RadixSort.generateExercise();
        */
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
        try {
            Process process = Runtime.getRuntime().exec("pdflatex -output-directory=docs docs/Exercises.tex");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();

            Process process2 = Runtime.getRuntime().exec("pdflatex -output-directory=docs docs/Solutions.tex");
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
            String line2;

            while ((line2 = reader2.readLine()) != null) {
                System.out.println(line2);
            }

            reader2.close();

        } catch (IOException e) {
            error("There was an error while generating the LaTeX, please try again");
        }
    }

    private void say(String toSay) {
        System.out.println(new Date().toString() + ": " + toSay);
    }

    private static void error(String errorText) {
        System.err.println(new Date().toString() + ": " + errorText);
    }

    private static void print(String text) {
        System.out.println(text);
    }

    private static void chooseExercizes(Scanner input) {
        StringBuilder temp = new StringBuilder();
        temp.append("0. Finish selection\n");
        temp.append("1. Dynamic Arrays\n");
        temp.append("2. Quicksort\n");
        temp.append("3. Radixsort\n");
        //TODO: add further exercize
        while(true) {
            System.out.println(temp.toString());
            switch (input.nextInt()) {
                case 1:
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
                    break;
                case 2:
                    QuickSort.generateExercise();
                    break;
                case 3:
                    RadixSort.generateExercise();
                    break;
                case 0:
                    return;
            }
        }
    }
}
