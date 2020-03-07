package Algorithms.Sorting.QuickSort;

import DataStructures.Terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    StringBuilder quickSortExerciseStringBuilder;
    StringBuilder quickSortSolutionStringBuilder;

    public static void main(String[] args) {
        QuickSort s = new QuickSort();
        s.generateExercise();
        try {
            Process process = Runtime.getRuntime().exec("pdflatex -output-directory=src/Algorithms/Sorting/QuickSort src/Algorithms/Sorting/QuickSort/QuickSortExercise.tex");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();

        } catch (IOException e) {
        }
    }

    public void generateExercise() {
        QuickSort s = new QuickSort();
        int[] a = s.randomArray();

        quickSortExerciseStringBuilder = Terminal.readFile("src/Algorithms/Sorting/QuickSort/QuickSortExerciseTemplate.tex");
        quickSortSolutionStringBuilder = Terminal.readFile("src/Algorithms/Sorting/QuickSort/QuickSortSolutionTemplate.tex");

        Terminal.replaceinSB(quickSortExerciseStringBuilder, "INITARRAY", s.printArray(a));
        Terminal.saveToFile("src/Algorithms/Sorting/QuickSort/QuickSortExercise.tex", quickSortExerciseStringBuilder);
    }

    private int[] randomArray() {
        int arraySize = new Random().nextInt(3) + 8;
        ArrayList<Integer> list = new ArrayList<>(arraySize);
        for (int i = 0; i <= 10; i++) {
            list.add(i);
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

    private void quickSort(int[] a, int l, int r) {
        if (l < r) {
            int p = a[r];
            System.out.println("pivot: " + p);
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
            System.out.println(printArray(a));
            quickSort(a, l, i - 1);
            quickSort(a, i + 1, r);
        }
    }

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private String printArray(int[] a) {
        String ret = "" + a[0];
        for (int i = 1; i < a.length; i++) {
            ret += ", " + a[i];
        }
        return ret;
    }
}
