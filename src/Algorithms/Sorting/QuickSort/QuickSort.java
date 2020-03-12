package Algorithms.Sorting.QuickSort;

import Util.Terminal;

import static Util.Terminal.printArray;

public class QuickSort {
    static StringBuilder quickSortExerciseStringBuilder;
    static StringBuilder quickSortSolutionStringBuilder;

    /**
     * generates a quicksort exercise and matching solutions
     * by reading and writing to the templates
     */
    public static void generateExercise() {
        QuickSort s = new QuickSort();
        int[] a = Terminal.generateRandomArray(8, 12);

        quickSortExerciseStringBuilder = Terminal.readFile("src/Algorithms/Sorting/QuickSort/QuickSortExerciseTemplate.tex");
        quickSortSolutionStringBuilder = Terminal.readFile("src/Algorithms/Sorting/QuickSort/QuickSortSolutionTemplate.tex");

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(quickSortExerciseStringBuilder, "INITARRAY", printArray(a));
        Terminal.replaceinSB(quickSortSolutionStringBuilder, "INITARRAY", printArray(a));

        s.sort(a);

        Terminal.replaceinSB(exerciseStringBuilder, "%$QuickSort$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$QuickSort$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$QUICKSORT$", "\\newpage\n" + quickSortExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$QUICKSORT$", "\\newpage\n" + quickSortSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);

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
            // TODO 07/03/2020 sebas: perhaps find a good way to indicate subarrays in the solution
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

    public void setQuickSortSolutionStringBuilder(StringBuilder quickSortSolutionStringBuilder) {
        QuickSort.quickSortSolutionStringBuilder = quickSortSolutionStringBuilder;
    }
}
