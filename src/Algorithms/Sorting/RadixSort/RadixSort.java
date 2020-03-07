package Algorithms.Sorting.RadixSort;

import DataStructures.Terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static DataStructures.Terminal.printArray;

public class RadixSort {
    private static final int DIGITS = 10;
    static StringBuilder radixSortExerciseStringBuilder;
    static StringBuilder radixSortSolutionStringBuilder;

    private static int key(Integer element, int decPlace) throws IllegalArgumentException {
        if (element < 0) {
            throw new IllegalArgumentException("Negative numbers not supported!");
        }
        int kPowered = 1;
        for (int i = 0; i < decPlace; i++) {
            kPowered *= DIGITS;
        }
        return (element / kPowered) % DIGITS;
    }

    private static void concatenate(ArrayList<Integer>[] buckets, Integer[] elements) {
        int k = 0;
        for (int i = 0; i < buckets.length; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
                elements[k++] = buckets[i].get(j);
            }
        }
    }

    private static void kSort(Integer[] elements, int decPlace) {
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] buckets = (ArrayList<Integer>[]) Array.newInstance(ArrayList.class, DIGITS);
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        for (int i = 0; i < elements.length; i++) {
            buckets[key(elements[i], decPlace)].add(elements[i]);
        }
        concatenate(buckets, elements);
    }

    private static int getMaxDecimalPlaces(Integer[] elements) {
        int max = 0;
        int digits = 0;
        for (Integer e : elements) {
            if (max < e) {
                max = e;
            }
        }
        do {
            digits++;
            max /= 10;
        } while (max != 0);
        return digits;
    }

    public static void sort(Integer[] elements) {
        int decPlaces = getMaxDecimalPlaces(elements);
        for (int decPlace = 0; decPlace < decPlaces; decPlace++) {
            kSort(elements, decPlace);

            Terminal.replaceinSB(radixSortSolutionStringBuilder, "$LISTROUND" + (decPlace + 1) + "$", "\\color{tumgadRed}" + Terminal.printArray(elements) + "\\color{black}");
            Terminal.replaceinSB(radixSortSolutionStringBuilder, "$LSDROUND" + (decPlace + 1) + "$", sortToTex(elements, decPlace));
        }
    }

    public static void generateExercise() {
        Integer[] a = Terminal.generateRandomIntegerArray(8, 12);

        radixSortExerciseStringBuilder = Terminal.readFile("src/Algorithms/Sorting/RadixSort/RadixSortExerciseTemplate.tex");
        radixSortSolutionStringBuilder = Terminal.readFile("src/Algorithms/Sorting/RadixSort/RadixSortSolutionTemplate.tex");

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(radixSortExerciseStringBuilder, "$LSDARRAY$", printArray(a));
        Terminal.replaceinSB(radixSortSolutionStringBuilder, "$LSDARRAY$", printArray(a));

        sort(a);

        Terminal.replaceinSB(exerciseStringBuilder, "%$RadixSort$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$RadixSort$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$RADIXSORT$", "\\newpage\n" + radixSortExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$RADIXSORT$", "\\newpage\n" + radixSortSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    private static String sortToTex(Integer[] a, int decPlace) {
        Integer[] b = new Integer[a.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = a[i];
        }

        String ret = "";
        int bucket0, bucket1, bucket2, bucket3, bucket4, bucket5, bucket6, bucket7, bucket8, bucket9;
        for (int i = 0; i < 7; i++) {
            bucket0 = bucket1 = bucket2 = bucket3 = bucket4 = bucket5 = bucket6 = bucket7 = bucket8 = bucket9 = -1;
            for (int j = 0; j < a.length; j++) {
                char numberCharAtRound = a[j].toString().charAt(2 - decPlace);
                // if we have the matching bucket, and it is empty, write element into bucket
                if (numberCharAtRound == '0' && bucket0 == -1) {
                    bucket0 = b[j];
                    b[j] = -1;
                } else if (numberCharAtRound == '1' && bucket1 == -1) {
                    bucket1 = b[j];
                    b[j] = -1;

                } else if (numberCharAtRound == '2' && bucket2 == -1) {
                    bucket2 = b[j];
                    b[j] = -1;

                } else if (numberCharAtRound == '3' && bucket3 == -1) {
                    bucket3 = b[j];
                    b[j] = -1;

                } else if (numberCharAtRound == '4' && bucket4 == -1) {
                    bucket4 = b[j];
                    b[j] = -1;

                } else if (numberCharAtRound == '5' && bucket5 == -1) {
                    bucket5 = b[j];
                    b[j] = -1;

                } else if (numberCharAtRound == '6' && bucket6 == -1) {
                    bucket6 = b[j];
                    b[j] = -1;

                } else if (numberCharAtRound == '7' && bucket7 == -1) {
                    bucket7 = b[j];
                    b[j] = -1;

                } else if (numberCharAtRound == '8' && bucket8 == -1) {
                    bucket8 = b[j];
                    b[j] = -1;

                } else if (numberCharAtRound == '9' && bucket9 == -1) {
                    bucket9 = b[j];
                    b[j] = -1;

                }
            }
            ret += (bucket0 + " & " + bucket1 + " & " + bucket2 + " & " + bucket3 + " & " + bucket4 + " & "
                    + bucket5 + " & " + bucket6 + " & " + bucket7 + " & " + bucket8 + " & " + bucket9 + "\\\\").replace("-1", "");
        }
        return ret;
    }

    public static void main(String[] args) {
        generateExercise();
        generateLatex();
    }

    private static void generateLatex() {
        try {
            Process process = Runtime.getRuntime().exec("pdflatex -output-directory=src/Algorithms/Sorting/RadixSort src/Algorithms/Sorting/RadixSort/RadixSortExercise.tex");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();

            Process process2 = Runtime.getRuntime().exec("pdflatex -output-directory=src/Algorithms/Sorting/RadixSort src/Algorithms/Sorting/RadixSort/RadixSortSolution.tex");
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
            String line2;

            while ((line2 = reader2.readLine()) != null) {
                System.out.println(line2);
            }

            reader2.close();

        } catch (IOException e) {
        }
    }
}