package DataStructures;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Terminal {
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

    /**
     * prints an array without parentheses for the pdf
     */
    public static String printArray(int[] a) {
        String ret = "" + a[0];
        for (int i = 1; i < a.length; i++) {
            ret += ", " + a[i];
        }
        return ret;
    }

    /**
     * prints an array without parentheses for the pdf
     */
    public static String printArray(Integer[] a) {
        String ret = "" + a[0];
        for (int i = 1; i < a.length; i++) {
            ret += ", " + a[i];
        }
        return ret;
    }

    /**
     * generates an Integer array with random length and fills it with random values
     * generated array size: minsize <= arraysize <= maxsize
     */
    public static Integer[] generateRandomIntegerArray(int minsize, int maxsize) {
        int arraySize = new Random().nextInt(maxsize - minsize + 1) + minsize;
        ArrayList<Integer> list = new ArrayList<>(arraySize);
        for (int i = 0; i <= arraySize; i++) {
            list.add(new Random().nextInt(400) + 100 + new Random().nextInt(400) + 100);
        }
        Integer[] a = new Integer[arraySize];
        for (int count = 0; count < arraySize; count++) {
            a[count] = list.remove((int) (Math.random() * list.size()));
        }
        return a;
    }

    /**
     * generates an array with random length and fills it with random values
     * generated array size: minsize <= arraysize <= maxsize
     */
    public static int[] generateRandomArray(int minsize, int maxsize) {
        int arraySize = new Random().nextInt(maxsize - minsize + 1) + minsize;
        ArrayList<Integer> list = new ArrayList<>(arraySize);
        for (int i = 0; i <= arraySize * 2; i++) {
            list.add(i);
        }
        int[] a = new int[arraySize];
        for (int count = 0; count < arraySize; count++) {
            a[count] = list.remove((int) (Math.random() * list.size()));
        }
        return a;
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
