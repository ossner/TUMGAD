package DataStructures.Sequences.Lists;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {

    @Test
    void test1_pushFront() {
        List list = new List();

        assertEquals(2,list.pushFront(2));
        assertEquals(4,list.pushFront(4));
        assertEquals(6,list.pushFront(6));
        assertEquals(8,list.pushFront(8));
        assertEquals(10,list.pushFront(10));
    }
}