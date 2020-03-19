package DataStructures.PriorityQueues.BinaryHeaps;

import Util.Terminal;

import java.util.ArrayList;
import java.util.Random;

public class BinaryHeap {
    static StringBuilder binaryHeapExerciseStringBuilder;
    static StringBuilder binaryHeapSolutionStringBuilder;

    ArrayList<Integer> heapList;
    boolean min;

    public BinaryHeap(boolean min) {
        this.min = min;
        heapList = new ArrayList<>();
    }

    private void insert(int value) {
        heapList.add(value);
        siftUp();
    }

    private void removeRoot() {
        swap(0, heapList.size() - 1);
        heapList.remove(heapList.size() - 1);
        siftDown(0);
        siftDown(0);
    }

    private void siftUp() {
        int index = heapList.size() - 1;
        if (min) {
            while (heapList.get(index) < heapList.get((index - 1) / 2)) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        } else {
            while (heapList.get(index) > heapList.get((index - 1) / 2)) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }
    }

    private void siftDown(int index) {
        if (min) {
            if (2 * index + 1 < heapList.size() && heapList.get(index) > heapList.get(2 * index + 1)) {
                swap(index, 2 * index + 1);
                siftDown(2 * index + 1);
            } else if (2 * index + 2 < heapList.size() && heapList.get(index) > heapList.get(2 * index + 2)) {
                swap(index, 2 * index + 2);
                siftDown(2 * index + 2);
            }
        } else {
            if (2 * index + 1 < heapList.size() && heapList.get(index) < heapList.get(2 * index + 1)) {
                swap(index, 2 * index + 1);
                siftDown(2 * index + 1);
            } else if (2 * index + 2 < heapList.size() && heapList.get(index) < heapList.get(2 * index + 2)) {
                swap(index, 2 * index + 2);
                siftDown(2 * index + 2);
            }
        }
    }

    private void swap(int index1, int index2) {
        int temp = heapList.get(index1);
        heapList.set(index1, heapList.get(index2));
        heapList.set(index2, temp);
    }

    public static void generateExercise() {
        binaryHeapExerciseStringBuilder = Terminal.readFile("src/DataStructures/PriorityQueues/BinaryHeaps/BinaryHeapExerciseTemplate.tex");
        binaryHeapSolutionStringBuilder = Terminal.readFile("src/DataStructures/PriorityQueues/BinaryHeaps/BinaryHeapSolutionTemplate.tex");

        BinaryHeap heap;
        if (new Random().nextBoolean()) {
            heap = new BinaryHeap(true);
            Terminal.replaceinSB(binaryHeapExerciseStringBuilder, "$MINMAX$", "min-");
            Terminal.replaceinSB(binaryHeapSolutionStringBuilder, "$MINMAX$", "min-");
            Terminal.replaceinSB(binaryHeapExerciseStringBuilder, "$MINMAX$", "Min()");
            Terminal.replaceinSB(binaryHeapSolutionStringBuilder, "$MINMAX$", "Min()");
        } else {
            heap = new BinaryHeap(false);
            Terminal.replaceinSB(binaryHeapSolutionStringBuilder, "$MINMAX$", "max-");
            Terminal.replaceinSB(binaryHeapExerciseStringBuilder, "$MINMAX$", "max-");
            Terminal.replaceinSB(binaryHeapSolutionStringBuilder, "$MINMAX$", "Max()");
            Terminal.replaceinSB(binaryHeapExerciseStringBuilder, "$MINMAX$", "Max()");
        }
        int[] values = Terminal.generateRandomArray(13, 13);
        for (int i = 0; i < 8; i++) {
            heap.insert(values[i]);
        }
        int[] inserts = new int[5];
        Terminal.replaceinSB(binaryHeapExerciseStringBuilder, "%$INITTREE$", heap.heapToTex());
        Terminal.replaceinSB(binaryHeapSolutionStringBuilder, "%$INITTREE$", heap.heapToTex());

        System.arraycopy(values, 8, inserts, 0, 5);

        Terminal.replaceinSB(binaryHeapExerciseStringBuilder, "$INSERTIONS$", Terminal.printArray(inserts));
        Terminal.replaceinSB(binaryHeapSolutionStringBuilder, "$INSERTIONS$", Terminal.printArray(inserts));

        for (int i = 0; i < inserts.length; i++) {
            heap.insert(inserts[i]);
            if (i == 2) {
                Terminal.replaceinSB(binaryHeapSolutionStringBuilder, "%$INSERTIONHEAPS$", "\\newpage\n%$INSERTIONHEAPS$");
            }
            Terminal.replaceinSB(binaryHeapSolutionStringBuilder, "%$INSERTIONHEAPS$", "Insert: \\underline{\\color{tumgadRed}" + inserts[i] + "\\color{black}}" + heap.heapToTex() + "%$INSERTIONHEAPS$");
        }

        for (int i = 0; i < 5; i++) {
            heap.removeRoot();
            Terminal.replaceinSB(binaryHeapSolutionStringBuilder, "%$DELETIONHEAPS$", heap.heapToTex() + "%$DELETIONHEAPS$");
        }

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$BINARYHEAPCELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$BINARYHEAPCELL$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$BINARYHEAPS$", "\\newpage\n" + binaryHeapExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$BINARYHEAPS$", "\\newpage\n" + binaryHeapSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    private String heapToTex() {
        return "\\begin{center}\\begin{tikzpicture}[level/.style={sibling distance=55mm/#1},minimum size=20pt,scale=.8,\n" +
                "every node/.style={transform shape}]\n"
                + heapToTex(0) + ";\n\\end{tikzpicture}\n" +
                "\\end{center}";
    }

    private String heapToTex(int index) {
        String ret;
        if (index == 0) {
            ret = "\\node[circle,draw](z){" + heapList.get(0) + "}";
        } else {
            ret = "child{node[circle,draw]{" + heapList.get(index) + "}";
        }
        if (2 * index + 1 < heapList.size()) {
            ret += heapToTex(2 * index + 1);
        }
        if (2 * index + 2 < heapList.size()) {
            ret += heapToTex(2 * index + 2);
        }
        return ret + (index == 0 ? "" : "}");
    }
}
