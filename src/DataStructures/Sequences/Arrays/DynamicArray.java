package DataStructures.Sequences.Arrays;

/**
 * Class represents Dynamic Array (read about it in the md),
 * an unbounded Array that changes its size based on its current size
 * and some other factors
 */
public class DynamicArray {
    private int beta; // growth factor
    private int alpha; // storage overhead
    private int n; // current number of elements in array
    private int[] b;

    /**
     * Initially everything needed to know is the
     * growth factor and the storage overhead
     */
    public DynamicArray(int beta, int alpha) {
        this.beta = beta;
        this.alpha = alpha;
        this.n = 0;
        this.b = new int[beta]; // initial arbitrary capacity
        // TODO 05/03/2020 sebas: check if initial capacity of beta is legit
    }

    /**
     * Insert an element into the Dynamic Array
     *
     * @param x the integer element you want to insert into the dynamic array
     */
    public void push(int x) {
        if (n == b.length) {
            int[] btick = new int[beta * b.length];
            copyValues(b, btick);
            b = btick;
        }
        b[n++] = x;
    }

    public int pop() {
        if (n-1 <= b.length / alpha) {
            int[] btick = new int[b.length/beta];
            copyValues(b, btick);
            b = btick;
        }
        return b[--n];
    }

    /**
     * copies the values from the first array into the second array
     *
     * @param from the origin array
     * @param to   the destination array
     */
    private void copyValues(int[] from, int[] to) {
        for (int i = 0; i < n; i++) {
            to[i] = from[i];
        }
    }

    public int[] getB() {
        return b;
    }

    public int getN() {
        return n;
    }
}
