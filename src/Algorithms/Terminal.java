package Algorithms;

import java.io.*;

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
