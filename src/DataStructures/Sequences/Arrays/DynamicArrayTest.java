package DataStructures.Sequences.Arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {
    @Test
    public void testPushLecture() {
        DynamicArray array = new DynamicArray(2, 4);
        array.push(0);
        array.push(1);
        array.push(2);
        array.push(3);
        array.push(4);
        assertEquals(5, array.getN());
        assertEquals(8, array.getB().length);
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 0, 0, 0}, array.getB());
    }

    @Test
    public void testPopLecture() {
        DynamicArray array = new DynamicArray(2, 4);
        array.push(0);
        array.push(1);
        array.push(2);
        array.push(3);
        array.push(4);
        assertEquals(4, array.pop());
        assertEquals(3, array.pop());
        assertEquals(2, array.pop());
        assertEquals(4, array.getB().length);
        assertEquals(2, array.getN());
        assertArrayEquals(new int[]{0, 1, 2, 0}, array.getB());
    }

    @Test
    public void testPushMarkdownExample() {
        DynamicArray array = new DynamicArray(2, 4);
        array.push(15);
        array.push(18);
        array.push(20);
        array.push(45);
        array.push(14);
        assertEquals(5, array.getN());
        assertEquals(8, array.getB().length);
        assertArrayEquals(new int[]{15, 18, 20, 45, 14, 0, 0, 0}, array.getB());
    }
}