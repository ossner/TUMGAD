package Algorithms.Hashing.Double;

import java.util.Random;

public class DoubleHashing {


    public static void main(String[] args) {
        System.out.println(DoubleHashing.generateHashfunction());
    }

    static int[] generateHashfunction() {
        Random rand = new Random();
        int[] a = new int[2];
        a[0] = (rand.nextInt(10) + 1);
        a[1] = (rand.nextInt(10));
        String h1 = "h(x) = (" + a[0] + "x + " + a[1]+") mod 11";
        return a;
    }

}
