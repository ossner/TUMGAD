package DataStructures.Sequences.Lists;

/**
 * Class represents an element of the dynamic list. It is able to chain itself to other
 * elements. Element contains a value.
 */
public class Element {
    private int value;
    private Element next;
    private Element prev;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Element getNext() {
        return next;
    }

    public void setNext(Element next) {
        this.next = next;
    }

    public Element getPrev() {
        return prev;
    }

    public void setPrev(Element prev) {
        this.prev = prev;
    }
}
