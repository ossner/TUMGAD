package Algorithms.Sorting.MergeSort;

import Util.Terminal;

import java.util.ArrayList;

/**
 * Please note that this class offers no actual implementation of the MergeSort
 * Algorithm at this time (09/03/20) this is due to the fact that generating
 * an exercise with the actual sorting algorithm is way too complex than is needed
 */
public class MergeSort {
    static StringBuilder mergeSortExerciseStringBuilder;
    static StringBuilder mergeSortSolutionStringBuilder;

    public static void generateExercise() {
        int[] a = generateRandomMSArray();
        mergeSortExerciseStringBuilder = Terminal.readFile("src/Algorithms/Sorting/MergeSort/MergeSortExerciseTemplate.tex");
        mergeSortSolutionStringBuilder = Terminal.readFile("src/Algorithms/Sorting/MergeSort/MergeSortSolutionTemplate.tex");

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");


        Terminal.replaceinSB(mergeSortExerciseStringBuilder, "$INITARRAY$", Terminal.printArray(a));
        Terminal.replaceinSB(mergeSortSolutionStringBuilder, "$INITARRAY$", Terminal.printArray(a));

        printSplit(a, a.length);
        printMerge(a);

        Terminal.replaceinSB(exerciseStringBuilder, "%$MSCELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$MSCELL$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$MERGESORT$", "\\newpage\n" + mergeSortExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$MERGESORT$", "\\newpage\n" + mergeSortSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    static String printSplit(int[] a, int m) {
        if (m == 0) {
            return "";
        }
        if (m == a.length) {
            return printSplit(a, m / 2);
        }
        String ret = "" + a[0];
        for (int i = 1; i < a.length; i++) {
            if (i % m == 0) {
                ret += " | " + a[i];
            } else {
                ret += ", " + a[i];
            }
        }
        Terminal.replaceinSB(mergeSortSolutionStringBuilder, "%$SORTINGARRAY$", "\n" +
                "\\underline{Split:}\n" +
                "    \\begin{center}\n" +
                "       \\color{tumgadRed}" +
                "        \\begin{tabular}{|c|}\n" +
                "            \\hline\n" +
                ret.replace("|", "$|$") + "\\\\\n" +
                "            \\hline\n" +
                "        \\end{tabular}\n" +
                "        \\vspace{10px}\n" +
                "    \\end{center}\n" +
                "%$SORTINGARRAY$");
        return ret + "\n" + printSplit(a, m / 2);
    }

    public static int[] generateRandomMSArray() {
        int arraySize = 16;
        ArrayList<Integer> list = new ArrayList<>(arraySize);
        for (int i = 0; i <= 49; i++) {
            list.add(i);
        }
        int[] a = new int[arraySize];
        for (int count = 0; count < arraySize; count++) {
            a[count] = list.remove(Terminal.rand.nextInt(list.size()));
        }
        return a;
    }

    static String printMerge(int[] a) {
        String result = "";
        for (int i = 0; i <= a.length / Math.pow(2, i) + 2; i++) {
            result = printMerge(a, i + 1) + "\n";
            Terminal.replaceinSB(mergeSortSolutionStringBuilder, "%$SORTINGARRAY$", "\n" +
                    "\\underline{Merge:}\n" +
                    "    \\begin{center}\n" +
                    "       \\color{tumgadRed}" +
                    "        \\begin{tabular}{|c|}\n" +
                    "            \\hline\n" +
                    result.replace("|", "$|$") + "\\\\\n" +
                    "            \\hline\n" +
                    "        \\end{tabular}\n" +
                    "        \\vspace{10px}\n" +
                    "    \\end{center}\n" +
                    "%$SORTINGARRAY$");
        }
        return result;
    }

    static String printMerge(int[] a, int depth) {
        //depth starts at 1 for pairs. 2 for groups of 4's
        int level = (int) Math.pow(2, depth);
        for (int i = 0, r = level; i < r; i = r, r += level) {
            if (i >= a.length) {
                break;
            }
            if (r > a.length) {
                r = a.length;
            }

            for (int k = i; k < r; k++) {
                for (int j = i; j < r - 1; j++) {
                    if (a[j] > a[j + 1]) {
                        // swap temp and arr[i]
                        int temp = a[j];
                        a[j] = a[j + 1];
                        a[j + 1] = temp;
                    }
                }
            }

        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < a.length - 1; i++) {
            if (i % level == level - 1) {
                result.append(a[i] + " ");
                result.append("| ");
                continue;
            }
            result.append(a[i] + ", ");
        }
        result.append(a[a.length - 1]);
        return result.toString();
    }
}
