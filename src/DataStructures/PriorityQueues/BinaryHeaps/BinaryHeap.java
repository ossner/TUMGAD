package DataStructures.PriorityQueues.BinaryHeaps;

import java.util.ArrayList;

public class BinaryHeap {
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

    public static void main(String[] args) {
        BinaryHeap binaryHeap = new BinaryHeap(false);
        binaryHeap.insert(90);
        binaryHeap.insert(89);
        binaryHeap.insert(70);
        binaryHeap.insert(36);
        binaryHeap.insert(75);
        binaryHeap.insert(63);
        binaryHeap.insert(65);
        binaryHeap.insert(21);
        binaryHeap.insert(18);
        binaryHeap.insert(15);
        binaryHeap.insert(12);
        binaryHeap.insert(72);
        binaryHeap.removeRoot();
        System.out.println(binaryHeap.heapList);
    }

    private void removeRoot() {
        swap(0, heapList.size() - 1);
        siftDown(0);
        heapList.remove(heapList.size() - 1);
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
            if (heapList.get(index) > heapList.get(2 * index + 1)) {
                swap(index, 2 * index + 1);
                siftDown(2 * index + 1);
            } else if (heapList.get(index) > heapList.get(2 * index + 2)) {
                swap(index, 2 * index + 2);
                siftDown(2 * index + 2);
            }
        } else {
            if (heapList.get(index) < heapList.get(2 * index + 1)) {
                swap(index, 2 * index + 1);
                siftDown(2 * index + 1);
            } else if (heapList.get(index) < heapList.get(2 * index + 2)) {
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

    static void generateExercise() {
        
    }

    private void heapToTex() {

    }
}
