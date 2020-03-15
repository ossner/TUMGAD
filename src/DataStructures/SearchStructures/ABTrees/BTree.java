package DataStructures.SearchStructures.ABTrees;

class BTreeNode {
    int[] keys;
    int minDeg;
    BTreeNode[] chidren;
    int numKeys;
    boolean isLeaf;

    BTreeNode(int minDeg, boolean isLeaf) {
        this.minDeg = minDeg;
        this.isLeaf = isLeaf;
        this.keys = new int[2 * minDeg - 1];
        this.chidren = new BTreeNode[2 * minDeg];
        numKeys = 0;
    }

    void traverse() {
        int i;
        for (i = 0; i < numKeys; i++) {
            if (!isLeaf) {
                chidren[i].traverse();
            }
            System.out.print(" " + keys[i]);
        }
        if (!isLeaf) {
            chidren[i].traverse();
        }
    }

    BTreeNode search(int k) {
        int i = 0;
        // find first key greater than k
        while (i < numKeys && k > keys[i]) {
            i++;
        }
        if (k == keys[i]) {
            return this;
        }
        // No key found and leaf node
        if (isLeaf) {
            return null;
        }
        return chidren[i].search(k);
    }

    public void splitChild(int i, BTreeNode parent) {
        BTreeNode newNode = new BTreeNode(parent.minDeg, parent.isLeaf);
        newNode.numKeys = minDeg - 1;
        for (int j = 0; j < minDeg - 1; j++) {
            newNode.keys[j] = parent.keys[j + minDeg];
        }
        if (!parent.isLeaf) {
            for (int j = 0; j < minDeg; j++) {
                newNode.chidren[j] = parent.chidren[j + minDeg];
            }
        }

        parent.numKeys = minDeg - 1;
        for (int j = numKeys; j >= i + 1; j--) {
            chidren[j + 1] = chidren[j];
        }
        chidren[i + 1] = newNode;
        for (int j = numKeys - 1; j >= i; j--) {
            keys[j + 1] = keys[j];
        }
        keys[i] = parent.keys[minDeg - 1];
        numKeys++;
    }

    public void insertNonFull(int k) {
        int i = numKeys - 1;
        if (isLeaf) {
            while (i >= 0 && keys[i] > k) {
                keys[i + 1] = keys[i--];
            }
            keys[i + 1] = k;
            numKeys++;
        } else {
            while (i >= 0 && keys[i] > k) {
                i--;
            }
            if (chidren[i + 1].numKeys == 2 * minDeg - 1) {
                splitChild(i + 1, chidren[i + 1]);
                if (keys[i + 1] < k) {
                    i++;
                }
            }
            chidren[i + 1].insertNonFull(k);
        }
    }
}

public class BTree {
    BTreeNode root;
    int minDeg;

    public BTree(int minDeg) {
        root = null;
        this.minDeg = minDeg;
    }

    void insert(int k) {
        if (root == null) {
            root = new BTreeNode(minDeg, true);
            root.keys[0] = k;
            root.numKeys = 1;
        } else {
            if (root.numKeys == 2 * minDeg - 1) {
                BTreeNode newNode = new BTreeNode(minDeg, false);
                newNode.chidren[0] = root;
                newNode.splitChild(0, root);
                int i = 0;
                if (newNode.keys[0] < k) {
                    i++;
                }
                newNode.chidren[i].insertNonFull(k);
                root = newNode;
            } else {
                root.insertNonFull(k);
            }
        }
    }

    public void traverse() {
        if (root != null) {
            root.traverse();
            System.out.println();
        }
    }

    public BTreeNode search(int k) {
        return root == null ? null : root.search(k);
    }

    public static void main(String[] args) {
        BTree tree = new BTree(3);
        tree.insert(1);
        tree.insert(3);
        tree.insert(7);
        tree.insert(10);
        tree.insert(11);
        tree.insert(13);
        tree.insert(14);
        tree.insert(15);
        tree.insert(18);
        tree.insert(16);
        tree.insert(19);
        tree.insert(24);
        tree.insert(25);
        tree.insert(26);
        tree.insert(21);
        tree.insert(4);
        tree.insert(5);
        tree.insert(20);
        tree.insert(22);
        tree.insert(2);
        tree.insert(17);
        tree.insert(12);
        tree.insert(6);
        System.out.print("traversal: ");
        tree.traverse();
    }
}
