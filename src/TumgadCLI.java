import DataStructures.Sequences.Arrays.DynamicArray;

import java.io.*;
import java.util.Date;

public class TumgadCLI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void main(String[] args) {
        TumgadCLI cli = new TumgadCLI();
        cli.print(ANSI_PURPLE +
                "  _______ _    _ __  __  _____          _____  \n" +
                " |__   __| |  | |  \\/  |/ ____|   /\\   |  __ \\ \n" +
                "    | |  | |  | | \\  / | |  __   /  \\  | |  | |\n" +
                "    | |  | |  | | |\\/| | | |_ | / /\\ \\ | |  | |\n" +
                "    | |  | |__| | |  | | |__| |/ ____ \\| |__| |\n" +
                "    |_|   \\____/|_|  |_|\\_____/_/    \\_\\_____/" + ANSI_RESET);
        // TODO 06/03/2020 sebas: insert check if user wants to generate this
        try {
            DynamicArray.generateExercise();
        } catch (IndexOutOfBoundsException e) {
            DynamicArray.generateExercise();
        }

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
            e.printStackTrace();
        }
    }

    private void say(String toSay) {
        System.out.println(new Date().toString() + ": " + toSay);
    }

    private void error(String errorText) {
        System.err.println(new Date().toString() + ": " + errorText);
    }

    private void print(String text) {
        System.out.println(text);
    }

    public static StringBuilder readFile(String fileName) {
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.err.println("could not read File (" + fileName + ") (" + e + ")");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            String line = bufferedReader.readLine();

            while (line != null) {
                sb.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println("could not read File (" + fileName + ") (" + e + ")");
            return null;
        }
        return sb;
    }

    public static void saveToFile(String fileName, StringBuilder toSave) {
        try {
            PrintWriter out = new PrintWriter(fileName);
            out.println(toSave);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * replaces placeholders in the latex StringBuilders
     */
    public static void replaceinSB(StringBuilder sb, String toReplace, String replaceWith) {
        int start = sb.indexOf(toReplace);
        sb.replace(start, start + toReplace.length(), replaceWith);
    }
}
