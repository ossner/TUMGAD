package DataStructures.SearchStructures.ABTrees;

import Util.Terminal;

import java.util.Arrays;
import java.util.Iterator;

class BTreeNode {
    int[] keys;
    int minDeg;
    BTreeNode[] children;
    int numKeys;
    boolean isLeaf;

    BTreeNode(int minDeg, boolean isLeaf) {
        this.minDeg = minDeg;
        this.isLeaf = isLeaf;
        this.keys = new int[2 * minDeg - 1];
        this.children = new BTreeNode[2 * minDeg];
        numKeys = 0;
    }

    void traverse() {
        int i;
        for (i = 0; i < numKeys; i++) {
            if (!isLeaf) {
                children[i].traverse();
            }
            System.out.print(" " + keys[i]);
        }
        if (!isLeaf) {
            children[i].traverse();
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
        return children[i].search(k);
    }

    public void splitChild(int i, BTreeNode parent) {
        BTreeNode newNode = new BTreeNode(parent.minDeg, parent.isLeaf);
        newNode.numKeys = minDeg - 1;
        for (int j = 0; j < minDeg - 1; j++) {
            newNode.keys[j] = parent.keys[j + minDeg];
        }
        if (!parent.isLeaf) {
            for (int j = 0; j < minDeg; j++) {
                newNode.children[j] = parent.children[j + minDeg];
            }
        }

        parent.numKeys = minDeg - 1;
        for (int j = numKeys; j >= i + 1; j--) {
            children[j + 1] = children[j];
        }
        children[i + 1] = newNode;
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
            if (children[i + 1].numKeys == 2 * minDeg - 1) {
                splitChild(i + 1, children[i + 1]);
                if (keys[i + 1] < k) {
                    i++;
                }
            }
            children[i + 1].insertNonFull(k);
        }
    }

    String rootToTex() {
        String print = "\\node[draw,rectangle split,rectangle split parts=" + numKeys + "] {" + keys[0];
        if (numKeys == 1) {
            print += "}";
        } else {
            int number = 2;
            for (int i = 1; i < numKeys; i++) {
                print += " \\nodepart{" + Terminal.numberString(number++) + "} " + keys[i];
            }
            print += "} [->]\n";
        }
        for (int i = 0; i < children.length; i++) {
            if (children[i] != null) {
                print += children[i].nodeToTex();
            }
        }
        return print;
    }

    /**
     * translates a BTree node to its LaTeX representation (partly)
     * returns a String similar to {34 \nodepart{two} 38 \nodepart{three} 44 \nodepart{four} 47}
     * obviously depending on the values in the key array
     */
    String nodeToTex() {
        String ret = "child {node[draw,rectangle split,rectangle split parts=" + numKeys + "]{" + keys[0];
        if (numKeys == 1) {
            ret += "}";
        } else {
            int number = 2;
            for (int i = 1; i < numKeys; i++) {
                ret += " \\nodepart{" + Terminal.numberString(number++) + "} " + keys[i];
            }
            ret += "}";
        }
        if (!isLeaf) {
            for (int i = 0; i < children.length; i++) {
                if (children[i] != null) {
                    ret += children[i].nodeToTex();
                }
            }
        }
        return ret + "}";
    }

    int findKey(int k) {
        int idx = 0;
        while (idx < numKeys && keys[idx] < k) {
            ++idx;
        }
        return idx;
    }

    void remove(int k) {
        int idx = findKey(k);
        if (idx < numKeys && keys[idx] == k) {
            if (isLeaf) {
                removeFromLeaf(idx);
            } else {
                removeFromNonLeaf(idx);
            }
        } else {
            boolean flag = (idx == numKeys);

            if (children[idx].numKeys < minDeg) {
                fill(idx);
            }
            if (flag && idx > numKeys) {
                children[idx - 1].remove(k);
            } else {
                children[idx].remove(k);
            }
        }
    }

    private void removeFromNonLeaf(int idx) {
        int k = keys[idx];
        if (children[idx].numKeys >= minDeg) {
            int pred = getPred(idx);
            keys[idx] = pred;
            children[idx].remove(pred);
        } else if (children[idx + 1].numKeys >= minDeg) {
            int succ = getSucc(idx);
            keys[idx] = succ;
            children[idx + 1].remove(succ);
        } else {
            merge(idx);
            children[idx].remove(k);
        }
    }

    private void fill(int idx) {
        if (idx != numKeys && children[idx + 1].numKeys >= minDeg) {
            borrowFromNext(idx);
        } else if (idx != 0 && children[idx - 1].numKeys >= minDeg) {
            borrowFromPrev(idx);
        } else {
            if (idx != numKeys) {
                merge(idx);
            } else {
                merge(idx - 1);
            }
        }
    }

    private void borrowFromNext(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];

        // keys[idx] is inserted as the last key in C[idx]
        child.keys[(child.numKeys)] = keys[idx];

        // Sibling's first child is inserted as the last child
        // into C[idx]
        if (!(child.isLeaf)) {
            child.children[(child.numKeys) + 1] = sibling.children[0];
        }

        //The first key from sibling is inserted into keys[idx]
        keys[idx] = sibling.keys[0];

        // Moving all keys in sibling one step behind
        for (int i = 1; i < sibling.numKeys; ++i) {
            sibling.keys[i - 1] = sibling.keys[i];
        }

        // Moving the child pointers one step behind
        if (!sibling.isLeaf) {
            for (int i = 1; i <= sibling.numKeys; ++i) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }

        // Increasing and decreasing the key count of C[idx] and C[idx+1]
        // respectively
        child.numKeys += 1;
        sibling.numKeys -= 1;
    }

    private void borrowFromPrev(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx - 1];
        for (int i = child.numKeys - 1; i >= 0; --i) {
            child.keys[i + 1] = child.keys[i];
        }
        if (!child.isLeaf) {
            for (int i = child.numKeys; i >= 0; --i) {
                child.children[i + 1] = child.children[i];
            }
        }
        child.keys[0] = keys[idx - 1];
        if (!child.isLeaf) {
            child.children[0] = sibling.children[sibling.numKeys];
        }
        keys[idx - 1] = sibling.keys[sibling.numKeys - 1];
        child.numKeys += 1;
        sibling.numKeys -= 1;
    }

    private void merge(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];
        child.keys[minDeg - 1] = keys[idx];
        if (sibling.numKeys >= 0) {
            System.arraycopy(sibling.keys, 0, child.keys, minDeg, sibling.numKeys);
        }

        if (!child.isLeaf) {
            if (sibling.numKeys + 1 >= 0) {
                System.arraycopy(sibling.children, 0, child.children, minDeg, sibling.numKeys + 1);
            }
        }

        if (numKeys - idx + 1 >= 0) {
            System.arraycopy(keys, idx + 1, keys, idx + 1 - 1, numKeys - idx + 1);
        }

        if (numKeys + 1 - idx + 2 >= 0) {
            System.arraycopy(children, idx + 2, children, idx + 2 - 1, numKeys + 1 - idx + 2);
        }
        child.numKeys += sibling.numKeys + 1;
        numKeys--;
    }

    private int getSucc(int idx) {
        BTreeNode node = children[idx + 1];
        while (!node.isLeaf) {
            node = node.children[0];
        }
        return node.keys[0];
    }

    private int getPred(int idx) {
        BTreeNode node = children[idx];
        while (!node.isLeaf) {
            node = node.children[node.numKeys];
        }
        return node.keys[node.numKeys - 1];
    }

    private void removeFromLeaf(int idx) {
        for (int i = idx + 1; i < numKeys; ++i) {
            keys[i - 1] = keys[i];
        }
        numKeys--;
    }

    @Override
    public String toString() {
        return "BTreeNode{" +
                "keys=" + Arrays.toString(keys) +
                ", numKeys=" + numKeys +
                ", isLeaf=" + isLeaf +
                "}\n";
    }
}

public class BTree {
    static StringBuilder abTreeExerciseStringBuilder;
    static StringBuilder abTreeSolutionStringBuilder;
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
                newNode.children[0] = root;
                newNode.splitChild(0, root);
                int i = 0;
                if (newNode.keys[0] < k) {
                    i++;
                }
                newNode.children[i].insertNonFull(k);
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

    void delete(int k) {
        root.remove(k);

        if (root.numKeys == 0) {
            BTreeNode tmp = root;
            if (root.isLeaf) {
                root = null;
            } else {
                root = root.children[0];
            }
        }
    }

    static public void generateExercise() {
        abTreeExerciseStringBuilder = Terminal.readFile("src/DataStructures/SearchStructures/ABTrees/ABTreeExerciseTemplate.tex");
        abTreeSolutionStringBuilder = Terminal.readFile("src/DataStructures/SearchStructures/ABTrees/ABTreeSolutionTemplate.tex");

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$ABCELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$ABCELL$", "\\cellcolor{tumgadRed}");

        int[] values = Terminal.generateRandomArray(10, 10);
        // (a,b) tree to BTree mappings:
        // 2,3
        // 3,5
        // etc.
        BTree tree = new BTree(2);
        tree.insert(8);
        tree.insert(19);
        tree.insert(11);
        tree.insert(18);
        tree.insert(13);
        tree.insert(1);
        tree.insert(6);
        tree.insert(22);
        tree.treeToTex();
        tree.delete(11);
        tree.treeToTex();
        System.out.println(tree.root);
        for (int i = 0; i < tree.root.children.length; i++) {
            System.out.println(tree.root.children[i]);
        }
        tree.traverse();

        Terminal.replaceinSB(exerciseStringBuilder, "%$ABTREES$", "\\newpage\n" + abTreeExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$ABTREES$", "\\newpage\n" + abTreeSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);

    }

    private void treeToTex() {
        Terminal.replaceinSB(abTreeExerciseStringBuilder, "%$ABTREE$", "\\begin{center}\\begin{tikzpicture}\n\\tikzstyle{bplus}=[rectangle split, rectangle split horizontal,rectangle split ignore empty parts,draw]\n" +
                "\\tikzstyle{every node}=[bplus]\n" +
                "\\tikzstyle{level 1}=[sibling distance=45mm]\n" +
                "\\tikzstyle{level 2}=[sibling distance=20mm]\n" +
                "\\tikzstyle{level 3}=[sibling distance=15mm]\n" +
                this.root.rootToTex() + ";\n\\end{tikzpicture}\\end{center}\n%$ABTREE$");
    }
}
