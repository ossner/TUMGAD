package DataStructures.PriorityQueues.BinomialHeaps;

import Util.Terminal;

import java.util.ArrayList;

public class BinomialHeap {
    static StringBuilder binomialHeapExerciseStringbuilder;
    static StringBuilder binomialHeapSolutionStringbuilder;
    private ArrayList<BinomialTreeNode> trees;
    private BinomialTreeNode minimum;

    /**
     * Constructor creating an empty binary heap
     */
    public BinomialHeap() {
        trees = new ArrayList<BinomialTreeNode>();
        minimum = null;
    }

    /**
     * Finds the minimal in the binomial heap
     *
     * @return the int-value of the minimal node
     */
    public int min() {
        if (minimum == null) {
            throw new RuntimeException("There is no minimal element in the heap (empty)");
        }
        return minimum.min();
    }

    /**
     * Method inserting a key into the heap
     *
     * @param key the key to be inserted
     * @param verbose whether or not to write the insertion to LaTeX
     */
    public void insert(int key, boolean verbose) {
        if (verbose) {
            Terminal.replaceinSB(binomialHeapSolutionStringbuilder, "%$BINHEAPINSERTIONS$", "\\noindent\\fbox{\\parbox{\\textwidth}{\\vspace{5px}Insert: \\underline{\\color{tumgadRed}" + key + "\\color{black}}\n%$BINHEAPINSERTIONS$");
        }
        BinomialTreeNode node = new BinomialTreeNode(key);
        merge(new BinomialTreeNode[]{node});
    }

    /**
     * Merges a number of BinomialTreeNodes to create a new heap and meet the criteria
     *
     * @param heads The array of Nodes (trees) to be merged
     */
    private void merge(BinomialTreeNode[] heads) {
        if (heads.length > 0) {
            ArrayList<BinomialTreeNode> newTrees = new ArrayList<BinomialTreeNode>();
            BinomialTreeNode carry = null;
            int i = 0, j = 0;
            int maxRank = 0;
            if (trees.size() > 0 && trees.get(trees.size() - 1).rank() > maxRank) {
                maxRank = trees.get(trees.size() - 1).rank();
            }
            if (heads[heads.length - 1].rank() > maxRank) {
                maxRank = heads[heads.length - 1].rank();
            }

            for (int r = 0; r <= maxRank; r++) {
                BinomialTreeNode a = null, b = null;
                if (i < trees.size() && trees.get(i).rank() == r) {
                    a = trees.get(i);
                    i++;
                }
                if (j < heads.length && heads[j].rank() == r) {
                    b = heads[j];
                    j++;
                }

                Pair<BinomialTreeNode, BinomialTreeNode> result = binaryAddition(a, b, carry);
                if (result.First != null) {
                    newTrees.add(result.First);
                }
                carry = result.Second;
            }
            if (carry != null) {
                newTrees.add(carry);
            }

            trees = newTrees;
        }

        if (trees.size() == 0) {
            minimum = null;
        } else {
            minimum = trees.get(0);
            for (int i = 1; i < trees.size(); i++) {
                if (trees.get(i).min() < minimum.min()) {
                    minimum = trees.get(i);
                }
            }
        }
    }

    private class Pair<T, S> {
        public T First;
        public S Second;

        public Pair(T t, S s) {
            First = t;
            Second = s;
        }
    }

    /**
     * Executes a binary addition with two TreeNodes and a possible carry
     *
     * @return A Pair of Nodes, the first being the result and the second being the carry
     */
    private Pair<BinomialTreeNode, BinomialTreeNode> binaryAddition(BinomialTreeNode a, BinomialTreeNode b,
                                                                    BinomialTreeNode carry) {
        int count = 0;
        if (a != null) {
            count++;
        }
        if (b != null) {
            count++;
        }
        if (carry != null) {
            count++;
        }
        Pair<BinomialTreeNode, BinomialTreeNode> result = new Pair<BinomialTreeNode, BinomialTreeNode>(null, null);

        if (count == 0) {
            return result;
        } else if (count == 1) {
            if (a != null) {
                result.First = a;
            } else if (b != null) {
                result.First = b;
            } else if (carry != null) {
                result.First = carry;
            }
            return result;
        } else if (count == 2) {
            if (a == null) {
                result.Second = BinomialTreeNode.merge(b, carry);
            } else if (b == null) {
                result.Second = BinomialTreeNode.merge(a, carry);
            } else if (carry == null) {
                result.Second = BinomialTreeNode.merge(a, b);
            }
            return result;
        } else {
            result.First = carry;
            result.Second = BinomialTreeNode.merge(a, b);
            return result;
        }
    }

    /**
     * Deletes the minimal element of the heap and returns it
     *
     * @return the minimal element that was deleted
     */
    public int deleteMin() {
        int min = min();
        Terminal.replaceinSB(binomialHeapSolutionStringbuilder, "%$BINHEAPDELETIONS$", "\\noindent\\fbox{\\parbox{\\textwidth}{\\vspace{5px}deleteMin(): \\underline{\\color{tumgadRed}min = " + min + "\\color{black}}\n%$BINHEAPDELETIONS$");
        BinomialTreeNode[] children = minimum.deleteMin();
        trees.remove(minimum);
        merge(children);
        return min;
    }

    /**
     * generates a LaTeX tikzpicture with the heap
     */
    private String heapToTex() {
        String ret = "\\begin{center}\n" +
                "\\begin{tikzpicture}[node distance = 2cm, semithick, baseline={([yshift={-\\ht\\strutbox}]current bounding box.north)},outer sep=0pt,inner sep=0pt]";
        ret += trees.get(0).treeToTex();
        for (int i = 1; i < trees.size(); i++) {
            ret += "\\end{tikzpicture}\n\\hspace{10px}\n" +
                    "\\begin{tikzpicture}[node distance = 2cm, semithick, baseline={([yshift={-\\ht\\strutbox}]current bounding box.north)},outer sep=0pt,inner sep=0pt]";
            ret += trees.get(i).treeToTex();
        }
        return ret + "\\end{tikzpicture}\n" +
                "\\end{center}";
    }

    public static void generateExercise() {
        binomialHeapExerciseStringbuilder = Terminal.readFile("src/DataStructures/PriorityQueues/BinomialHeaps/BinomialHeapExerciseTemplate.tex");
        binomialHeapSolutionStringbuilder = Terminal.readFile("src/DataStructures/PriorityQueues/BinomialHeaps/BinomialHeapSolutionTemplate.tex");

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$BINOMIALHEAPCELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$BINOMIALHEAPCELL$", "\\cellcolor{tumgadRed}");

        int[] values = Terminal.generateRandomArray(16, 16);
        BinomialHeap heap = generateInitHeap(values);

        Terminal.replaceinSB(binomialHeapSolutionStringbuilder, "%$INITHEAP$", heap.heapToTex());
        Terminal.replaceinSB(binomialHeapExerciseStringbuilder, "%$INITHEAP$", heap.heapToTex());

        for (int i = 0; i < 4; i++) {
            heap.deleteMin();
            Terminal.replaceinSB(binomialHeapSolutionStringbuilder, "%$BINHEAPDELETIONS$", heap.heapToTex() + "}}\\vspace{10px}\n%$BINHEAPDELETIONS$");
        }

        int[] insertions = new int[5];
        System.arraycopy(values, 11, insertions, 0, 5);
        Terminal.replaceinSB(binomialHeapExerciseStringbuilder, "$INSERTIONS$", Terminal.printArray(insertions));
        Terminal.replaceinSB(binomialHeapSolutionStringbuilder, "$INSERTIONS$", Terminal.printArray(insertions));

        for (int i = 0; i < insertions.length; i++) {
            heap.insert(insertions[i], true);
            Terminal.replaceinSB(binomialHeapSolutionStringbuilder, "%$BINHEAPINSERTIONS$", heap.heapToTex() + "}}\\vspace{10px}\n%$BINHEAPINSERTIONS$");
        }

        Terminal.replaceinSB(exerciseStringBuilder, "%$BINOMIALHEAPS$", "\\newpage\n" + binomialHeapExerciseStringbuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$BINOMIALHEAPS$", "\\newpage\n" + binomialHeapSolutionStringbuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    /**
     * generates the initial heap on which the rest of the operations will be executed
     *
     * @param values the int-array of initial values
     */
    private static BinomialHeap generateInitHeap(int[] values) {
        BinomialHeap heap = new BinomialHeap();
        for (int i = 0; i < 11; i++) {
            heap.insert(values[i], false);
        }
        return heap;
    }
}

class BinomialTreeNode {
    private int key;
    private BinomialTreeNode[] children;

    public BinomialTreeNode(int key) {
        this.key = key;
        this.children = new BinomialTreeNode[0];
    }

    public int min() {
        return key;
    }

    public int rank() {
        return children.length;
    }

    public BinomialTreeNode[] deleteMin() {
        return children;
    }

    /**
     * Merges two Binomial Trees of the same rank.
     *
     * @param a first tree
     * @param b second tree
     * @return The tree that results after the merge
     */
    public static BinomialTreeNode merge(BinomialTreeNode a, BinomialTreeNode b) {
        if (a.rank() != b.rank()) {
            throw new RuntimeException("Unable to merge trees of different rank!");
        }

        BinomialTreeNode[] children = new BinomialTreeNode[a.children.length + 1];
        BinomialTreeNode bigger;
        BinomialTreeNode smaller;
        if (a.min() <= b.min()) {
            smaller = a;
            bigger = b;
        } else {
            smaller = b;
            bigger = a;
        }
        for (int i = 0; i < children.length - 1; i++) {
            children[i] = smaller.children[i];
        }
        children[children.length - 1] = bigger;
        smaller.children = children;
        return smaller;
    }

    private void print(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("\t");
        }
        System.out.print("[rank: " + rank() + " key: " + min() + "]\n");
        for (BinomialTreeNode c : children) {
            c.print(depth + 1);
        }
    }

    public String treeToTex() {
        return treeToTex(null, null);
    }

    private String treeToTex(BinomialTreeNode sibling, BinomialTreeNode parent) {
        String ret = "";
        if (parent == null) {
            ret += "\\node[state] (" + this.key + ") {$" + this.key + "$};";
        } else if (sibling == null) {
            ret += "\\node[state] (" + key + ") [below of=" + parent.key + "] {$" + key + "$};";
            ret += "\n\\path (" + parent.key + ") edge node {} (" + key + ");";
        } else {
            ret += "\\node[state] (" + key + ") [left of=" + sibling.key + "] {$" + key + "$};";
            ret += "\n\\path (" + parent.key + ") edge node {} (" + key + ");";
        }
        BinomialTreeNode s = null;
        for (BinomialTreeNode c : children) {
            ret += c.treeToTex(s, this);
            s = c;
        }
        return ret;
    }
}
