package Algorithms.Sorting.QuickSort;

import DataStructures.Terminal;

import java.util.ArrayList;
import java.util.Random;

public class QuickSort {
    StringBuilder quickSortExerciseStringBuilder;
    StringBuilder quickSortSolutionStringBuilder;


    /**
     * prints an array without parentheses for the pdf
     */
    private static String printArray(int[] a) {
        String ret = "" + a[0];
        for (int i = 1; i < a.length; i++) {
            ret += ", " + a[i];
        }
        return ret;
    }

    /**
     * generates a quicksort exercise and matching solutions
     * by reading and writing to the templates
     */
    public void generateExercise() {
        QuickSort s = new QuickSort();
        int[] a = s.randomArray();

        quickSortExerciseStringBuilder = Terminal.readFile("src/Algorithms/Sorting/QuickSort/QuickSortExerciseTemplate.tex");
        quickSortSolutionStringBuilder = Terminal.readFile("src/Algorithms/Sorting/QuickSort/QuickSortSolutionTemplate.tex");

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(quickSortExerciseStringBuilder, "INITARRAY", s.printArray(a));
        Terminal.replaceinSB(quickSortSolutionStringBuilder, "INITARRAY", s.printArray(a));
        s.setQuickSortSolutionStringBuilder(quickSortSolutionStringBuilder);
        s.sort(a);

        Terminal.replaceinSB(exerciseStringBuilder, "%$QuickSort$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$QuickSort$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$QUICKSORT$", "\\newpage\n" + quickSortExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$QUICKSORT$", "\\newpage\n" + quickSortSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);

    }

    /**
     * generates an array with random length and fills it with random values
     */
    private int[] randomArray() {
        int arraySize = new Random().nextInt(5) + 8; // min 8 max 12
        ArrayList<Integer> list = new ArrayList<>(arraySize);
        for (int i = 0; i <= arraySize; i++) {
            list.add(i * 2);
        }
        int[] a = new int[arraySize];
        for (int count = 0; count < arraySize; count++) {
            a[count] = list.remove((int) (Math.random() * list.size()));
        }
        return a;
    }

    public void sort(int[] a) {
        quickSort(a, 0, a.length - 1);
    }

    /**
     * sorting algorithm, selects the rightmost element of the array as pivot
     */
    private void quickSort(int[] a, int l, int r) {
        if (l < r) {
            int p = a[r];
            int i = l - 1;
            int j = r;
            do {
                do {
                    i++;
                } while (a[i] < p);
                do {
                    j--;
                } while (j >= l && a[j] > p);
                if (i < j) {
                    swap(a, i, j);
                }
            } while (i < j);
            swap(a, i, r);
            // TODO 07/03/2020 sebas: Find a good way to indicate subarrays in the solution
            Terminal.replaceinSB(quickSortSolutionStringBuilder, "%$SORTINGSTEP$", sortToTex(p, a));
            quickSort(a, l, i - 1);
            quickSort(a, i + 1, r);
        }
    }

    /**
     * creates a prettyprint of the sortng step with pivot and array
     */
    private String sortToTex(int pivot, int[] a) {
        return "pivot: \\underline{\\color{tumgadRed}" + pivot + "\\color{tumgadPurple}}\\\\\n" +
                "\\\\\n" +
                "New Array: \\underline{\\color{tumgadRed}" + printArray(a) + "\\color{black}}\\\\\n" +
                "\\\\" +
                "\n%$SORTINGSTEP$";
    }

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public void setQuickSortExerciseStringBuilder(StringBuilder quickSortExerciseStringBuilder) {
        this.quickSortExerciseStringBuilder = quickSortExerciseStringBuilder;
    }

    public void setQuickSortSolutionStringBuilder(StringBuilder quickSortSolutionStringBuilder) {
        this.quickSortSolutionStringBuilder = quickSortSolutionStringBuilder;
    }
}
