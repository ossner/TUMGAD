package DataStructures.Sequences.Lists;

/**
 * Class represents a Dynamic double-chained List (read about it in the md),
 * a list of sequenced elements with a dummy element as a head.
 */
public class List {
    /**
     * head element. Functions as a dummy to assist in quickly navigating the list
     */
    private Element h;

    /**
     * The head is initialized and points to itself in both directions
     */
    public List() {
        this.h = new Element();
        h.setNext(h);
        h.setPrev(h);
    }

    /**
     * Gets the head element
     * @return the head element h
     */
    private Element head() {
        return h;
    }

    /**
     * Check's whether the list is empty
     * @return boolean of whether the list is empty
     */
    private boolean isEmpty() {
        return (h.getNext() == h);
    }

    /**
     * Gets first element
     * @return first element
     */
    private Element first() {
        return h.getNext();
    }

    /**
     * Gets last element
     * @return last element
     */
    private Element last() {
        return h.getPrev();
    }

    /**
     * The sequence of elements in the list <a,...,b> is removed and inserted
     * after the element t
     * @param a the start of the sequence to be moved
     * @param b the end of the sequence to be moved
     * @param t the element after which the sequence is moved
     */
    private void splice(Element a, Element b, Element t) {
        //cut out <a,...,b>
        Element ap = a.getPrev();
        Element bn = b.getNext();
        ap.setNext(bn);
        bn.setPrev(ap);

        //insert <a,...,b> next to t
        Element tn = t.getNext();
        b.setNext(tn);
        a.setPrev(t);
        t.setNext(a);
        tn.setPrev(b);
    }

    /**
     * Removes the element b and places it behind element a
     * @param b element which is moved
     * @param a element which b is placed behind
     */
    private void moveAfter(Element b, Element a) {
        splice(b, b, a);
    }

    /**
     * Element b is placed at the front of the list
     * @param b element which is moved
     */
    private void moveToFront(Element b) {
        moveAfter(b, h);
    }

    /**
     * Element b is placed at the end of the list
     * @param b element which is moved
     */
    private void moveToBack(Element b) {
        moveAfter(b, last());
    }

    /**
     * Removes b from the list and inserts it in a new list which is then
     * removed by the garbage collector. This procedure improves run-time
     * @param b element which is removed
     */
    private void remove(Element b) {
        moveAfter(b, new List().head());
    }

    /**
     * Removes the first element in the list
     */
    public void popFront() {
        remove(first());
    }

    /**
     * Removes the last element in the list
     */
    public void popBack() {
        remove(last());
    }

    /**
     * Inserts a new element with value v behind element a
     * @param v value of new element
     * @param a new element is placed after a
     * @return the new element
     */
    public Element insertAfter(int v, Element a) {
        Element b = new Element();
        b.setValue(v);
        moveAfter(b, a);
        return b;
    }

    /**
     * Inserts a new element with value v before element a
     * @param v value of new element
     * @param a new element is placed before a
     * @return the new element
     */
    public Element insertBefore(int v, Element a) {
        return insertAfter(v, a.getPrev());
    }

    /**
     * Adds new element to the front of the list
     * @param v value of new element
     * @return the new element
     */
    public Element pushFront(int v) {
        return insertAfter(v, h);
    }

    /**
     * Adds new element to the end of the list
     * @param v value of new element
     * @return the new element
     */
    public Element pushBack(int v) {
        return insertAfter(v, last());
    }

    /**
     * Searches for the next Element with the value x starting from element from
     * @param x value of the element being searched for
     * @param from start position for search
     * @return Element with the value x starting from element from
     */
    private Element findNext(int x, Element from) {
        h.setValue(x);
        while(from.getValue() != x) {
            from = from.getNext();
        }
        h.setValue(0);
        return from;
    }
}
