package Algorithms.Sorting.MergeSort;

import DataStructures.Terminal;

public class MergeSort {
    static String[] mergeString = new String[4];

    protected static void sort(int[] numbers, int l, int r, int depth) {
        if (l >= r) {
            return;
        }

        int m = (l + r) / 2;
        if (mergeString[depth] == null) {
            mergeString[depth] = mergePrint(numbers, l, m) + " | ";
        } else {
            mergeString[depth] += " | " + mergePrint(numbers, m + 1, r);
        }
        sort(numbers, l, m, depth + 1);
        sort(numbers, m + 1, r, depth + 1);
        merge(numbers, l, m, r);
        System.out.println(Terminal.printArray(numbers));
    }

    private static String mergePrint(int[] numbers, int l, int r) {
        String ret = "" + numbers[l++];
        for (; l <= r; l++) {
            ret += ", " + numbers[l];
        }
        return ret;
    }

    protected static void merge(int[] numbers, int l, int m, int r) {
        // Beide Hälften verschmelzen
        int j = l, k = m + 1;
        int[] buffer = new int[r - l + 1];
        for (int i = 0; i <= r - l; i++) {
            if (j > m) { // linker Teil leer
                buffer[i] = numbers[k];
                k++;
            } else if (k > r) { // rechter Teil leer
                buffer[i] = numbers[j];
                j++;
            } else if (numbers[j] <= numbers[k]) {
                buffer[i] = numbers[j];
                j++;
            } else {
                buffer[i] = numbers[k];
                k++;
            }
        }

        // zurückkopieren
        for (int i = 0; i <= r - l; i++) {
            numbers[l + i] = buffer[i];
        }
    }

    public static void sort(int[] a) {
        sort(a, 0, a.length - 1, 0);
    }

    public static void main(String[] args) {
        int[] a = new int[]{21, 82, 2, 34, 79, 9, 65, 60, 95, 38, 62, 88, 90, 7, 94, 49};
        System.out.println(printSplit(a, a.length));
        sort(a);
    }

    static String printSplit(int[] a, int m) {
        if (m == 0) {
            return "";
        }
        String ret = "" + a[0];
        for (int i = 1; i < a.length; i++) {
            if (i % m == 0) {
                ret += " | " + a[i];
            } else {
                ret += ", " + a[i];
            }
        }
        return ret + "\n" + printSplit(a, m / 2);
    }
}
