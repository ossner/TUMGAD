package DataStructures.SearchStructures.ABTrees;

import Util.Terminal;

import java.util.ArrayList;

abstract class ABNode {
    /**
     * The lower bound of the node degree.
     */
    protected int a;

    /**
     * The upper bound of the node degree.
     */
    protected int b;

    ABNode(int a, int b) {
        this.a = a;
        this.b = b;
    }

    /**
     * inserts a key into the tree.
     *
     * @param key the key to be inserted
     */
    abstract void insert(int key);

    /**
     * decides, whether you can steal out of this node or not.
     *
     * @return 'true' if you can steam, 'false' else
     */
    abstract boolean stealable();

    /**
     * looks for key in the tree.
     *
     * @param key the key in question
     * @return 'true' if the key is in the tree, 'false' else
     */
    abstract boolean find(int key);

    /**
     * removes a key 'key' in the subtree, id it is present.
     *
     * @param key the key to be removed
     * @return 'true' if the key was found and removed, 'false' else
     */
    abstract boolean remove(int key);

    /**
     * calculated the height of the subtree
     *
     * @return the found height
     */
    abstract int height();

    /**
     * finds the minimal element in the sub tree
     *
     * @return the minimum
     */
    abstract Integer min();

    /**
     * Finds the maximum element in the sub tree
     *
     * @return the max element
     */
    abstract Integer max();

    /**
     * Checks if this is a valid ab tree
     *
     * @return 'true' if it is a valid (a,b)-Tree, 'false' else
     */
    abstract boolean isValid(boolean root);

    /**
     * Converts a (a,b) Tree into a Tikzpicture
     */
    abstract String toTex();
}

public class ABTree {
    static StringBuilder abTreeExerciseStringBuilder;
    static StringBuilder abTreeSolutionStringBuilder;
    private final int a;
    private final int b;
    private ABNestedNode root = null;

    ABTree(int a, int b) {
        this.a = a;
        this.b = b;
    }

    static public void generateExercise() {
        abTreeExerciseStringBuilder = Terminal.readFile("src/DataStructures/SearchStructures/ABTrees/ABTreeExerciseTemplate.tex");
        abTreeSolutionStringBuilder = Terminal.readFile("src/DataStructures/SearchStructures/ABTrees/ABTreeSolutionTemplate.tex");

        int a = Terminal.rand.nextInt(2) + 2;
        int b = 2 * a + Terminal.rand.nextInt(2) - 1;
        ABTree tree = new ABTree(a, b);
        int[] values = Terminal.generateRandomArray(19, 19);
        for (int i = 0; i < 14; i++) {
            tree.insert(values[i]);
        }

        Terminal.replaceinSB(abTreeExerciseStringBuilder, "$ABVALUES$", "a = " + a + " and b = " + b);
        Terminal.replaceinSB(abTreeSolutionStringBuilder, "$ABVALUES$", "a = " + a + " and b = " + b);

        Terminal.replaceinSB(abTreeSolutionStringBuilder, "%$INITTREE$", tree.toTex());
        Terminal.replaceinSB(abTreeExerciseStringBuilder, "%$INITTREE$", tree.toTex());

        int[] deletions = new int[]{values[0], values[5], values[8], values[12], values[3]};
        int[] insertions = new int[5];

        System.arraycopy(values, 14, insertions, 0, 5);

        Terminal.replaceinSB(abTreeExerciseStringBuilder, "$DELETIONS$", Terminal.printArray(deletions));
        Terminal.replaceinSB(abTreeSolutionStringBuilder, "$DELETIONS$", Terminal.printArray(deletions));

        Terminal.replaceinSB(abTreeExerciseStringBuilder, "$INSERTIONS$", Terminal.printArray(insertions));
        Terminal.replaceinSB(abTreeSolutionStringBuilder, "$INSERTIONS$", Terminal.printArray(insertions));

        for (int i = 0; i < deletions.length; i++) {
            Terminal.replaceinSB(abTreeSolutionStringBuilder, "%$ABTREE$", "\\begin{center}\n" +
                    "    \\noindent\\fbox{\n" +
                    "        \\parbox{\\textwidth}{Delete: \\underline{" +
                    "\\color{tumgadRed}" + deletions[i] + "\\color{black}}\n%$ABTREE$");
            tree.remove(deletions[i]);
            Terminal.replaceinSB(abTreeSolutionStringBuilder, "%$ABTREE$", tree.toTex() + "}}\\end{center}%$ABTREE$");
        }

        for (int i = 0; i < insertions.length; i++) {
            Terminal.replaceinSB(abTreeSolutionStringBuilder, "%$ABTREE$", "\\begin{center}\n" +
                    "    \\noindent\\fbox{\n" +
                    "        \\parbox{\\textwidth}{Insert: \\underline{" +
                    "\\color{tumgadRed}" + insertions[i] + "\\color{black}}\n%$ABTREE$");
            tree.insert(insertions[i]);
            Terminal.replaceinSB(abTreeSolutionStringBuilder, "%$ABTREE$", tree.toTex() + "}}\\end{center}%$ABTREE$");
        }


        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        assert exerciseStringBuilder != null;
        assert solutionStringBuilder != null;
        Terminal.replaceinSB(exerciseStringBuilder, "%$ABCELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$ABCELL$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$ABTREES$", "\\newpage\n" + abTreeExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$ABTREES$", "\\newpage\n" + abTreeSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);

    }

    void insert(int key) {
        if (root == null) {
            root = new ABNestedNode(key, a, b);
            return;
        }
        root.insert(key);
        if (root.getChildren().size() > b) {
            ArrayList<Integer> keysL = new ArrayList<>(), keysR = new ArrayList<>();
            ArrayList<ABNode> childL = new ArrayList<>(), childR = new ArrayList<>();

            int middle = b / 2;

            for (int i = 0; i < root.getKeys().size(); i++) {
                if (i < middle) {
                    keysL.add(root.getKeys().get(i));
                    childL.add(root.getChildren().get(i));
                } else if (i > middle) {
                    keysR.add(root.getKeys().get(i));
                    childR.add(root.getChildren().get(i));
                } else {
                    childL.add(root.getChildren().get(i));
                }
            }
            childR.add(root.getChildren().get(root.getChildren().size() - 1));

            ABNestedNode leftNew = new ABNestedNode(keysL, childL, a, b);
            ABNestedNode rightNew = new ABNestedNode(keysR, childR, a, b);

            root = new ABNestedNode(root.getKeys().get(middle), leftNew, rightNew, a, b);
        }
    }

    boolean remove(int key) {
        if (root != null) {
            boolean result = root.remove(key);
            if (root.getChildren().size() < 2 && root.getChildren().get(0) instanceof ABNestedNode) {
                root = (ABNestedNode) root.getChildren().get(0);
            } else if (root.getChildren().size() < 2) {
                root = null;
            }
            return result;
        }
        return false;
    }

    private String toTex() {
        int l1Dist = root.height() == 3 ? 55 : 25;
        if (b >= 5) { // small issue with overlapping nodes at this value
            l1Dist = 35;
        }
        int l2Dist = 20;
        String ret = "\\begin{center}\\begin{tikzpicture}\n\\tikzstyle{bplus}=[rectangle split, rectangle split horizontal,rectangle split ignore empty parts,draw]\n" +
                "\\tikzstyle{every node}=[bplus]\n" +
                "\\tikzstyle{level 1}=[sibling distance=" + l1Dist + "mm]\n" +
                "\\tikzstyle{level 2}=[sibling distance=" + l2Dist + "mm]\n" +
                "\\node {" + root.getKeys().get(0);

        for (int i = 1; i < root.getKeys().size(); i++) {
            ret += "\\nodepart{" + Terminal.numberString(i + 1) + "}" + root.getKeys().get(i);
        }

        ret += "} [->]";
        for (int i = 0; i < root.getChildren().size(); i++) {
            ret += root.getChildren().get(i).toTex();
        }
        return ret + ";\n\\end{tikzpicture}\\end{center}\n";
    }
}


class ABNestedNode extends ABNode {

    final ArrayList<Integer> keys; // keys stored in the node and subnodes are saved in this list
    final ArrayList<ABNode> children;

    ABNestedNode(ArrayList<Integer> keys, ArrayList<ABNode> children, int a, int b) {
        super(a, b);
        this.keys = keys;
        this.children = children;
    }

    ABNestedNode(int key, ABNode left, ABNode right, int a, int b) {
        super(a, b);
        keys = new ArrayList<>();
        children = new ArrayList<>();
        keys.add(key);
        children.add(left);
        children.add(right);
    }

    ABNestedNode(int key, int a, int b) {
        this(key, new ABLeafNode(a, b), new ABLeafNode(a, b), a, b);
    }

    ArrayList<ABNode> getChildren() {
        return children;
    }

    ArrayList<Integer> getKeys() {
        return keys;
    }

    @Override
    void insert(int key) {
        int index;
        for (index = 0; index < keys.size(); index++) {
            if (keys.get(index) == key) {
                return;
            }
            if (keys.get(index) > key) {
                break;
            }
        }
        ABNode child = children.get(index);
        child.insert(key);

        if (child instanceof ABLeafNode) {
            ABLeafNode newLeaf = new ABLeafNode(a, b);
            keys.add(index, key);
            children.add(index, newLeaf);
        } else {
            ABNestedNode innerChild = (ABNestedNode) child;
            if (innerChild.children.size() > b) {
                ArrayList<Integer> keysLeft = new ArrayList<>(), keysRight = new ArrayList<>();
                ArrayList<ABNode> childrenLeft = new ArrayList<>(), childrenRight = new ArrayList<>();

                int middle = b / 2;

                for (int i = 0; i < innerChild.keys.size(); i++) {
                    if (i < middle) {
                        keysLeft.add(innerChild.keys.get(i));
                        childrenLeft.add(innerChild.children.get(i));
                    } else if (i == middle) {
                        childrenLeft.add(innerChild.children.get(i));
                    } else {
                        keysRight.add(innerChild.keys.get(i));
                        childrenRight.add(innerChild.children.get(i));
                    }
                }
                childrenRight.add(innerChild.children.get(innerChild.children.size() - 1));

                ABNestedNode leftNew = new ABNestedNode(keysLeft, childrenLeft, a, b);
                ABNestedNode rightNew = new ABNestedNode(keysRight, childrenRight, a, b);

                keys.add(index, innerChild.keys.get(middle));
                children.set(index, leftNew);
                children.add(index + 1, rightNew);
            }
        }
    }

    @Override
    boolean stealable() {
        return children.size() > a;
    }

    @Override
    boolean find(int key) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i) == key) {
                return true;
            } else if (keys.get(i) > key) {
                return children.get(i).find(key);
            }
        }
        return children.get(children.size() - 1).find(key);
    }

    @Override
    boolean remove(int key) {
        for (int index = 0; index < children.size(); index++) {
            ABNode child = children.get(index);
            if (index == keys.size() || keys.get(index) > key) {
                boolean result = child.remove(key);
                if (result) {
                    balanceChildren(index);
                }
                return result;
            } else if (keys.get(index) == key) {
                if (child instanceof ABLeafNode) {
                    keys.remove(index);
                    children.remove(index);
                } else {
                    keys.set(index, ((ABNestedNode) child).removeBiggestChild());
                    balanceChildren(index);
                }
                return true;
            }
        }
        return false;
    }

    void balanceChildren(int index) {
        assert (children.get(index) instanceof ABNestedNode);
        ABNestedNode innerChild = (ABNestedNode) children.get(index);
        if (innerChild.children.size() < a) {
            if (index - 1 >= 0 && children.get(index - 1).stealable()) {
                ABNestedNode leftNeighbor = (ABNestedNode) children.get(index - 1);
                innerChild.children.add(0, leftNeighbor.children.get(leftNeighbor.children.size() - 1));
                leftNeighbor.children.remove(leftNeighbor.children.size() - 1);

                innerChild.keys.add(0, keys.get(index - 1));
                keys.set(index - 1, leftNeighbor.keys.get(leftNeighbor.keys.size() - 1));
                leftNeighbor.keys.remove(leftNeighbor.keys.size() - 1);
            } else if (index + 1 < children.size() && children.get(index + 1).stealable()) {
                ABNestedNode rightNeighbor = (ABNestedNode) children.get(index + 1);
                innerChild.children.add(rightNeighbor.children.get(0));
                rightNeighbor.children.remove(0);

                innerChild.keys.add(keys.get(index));
                keys.set(index, rightNeighbor.keys.get(0));
                rightNeighbor.keys.remove(0);
            } else // Merge node with neighbor
                if (index + 1 < children.size()) {
                    mergeChildren(index);
                } else {
                    mergeChildren(index - 1);
                }
        }
    }

    void mergeChildren(int keyIndex) {
        ABNestedNode node0 = (ABNestedNode) children.get(keyIndex);
        ABNestedNode node1 = (ABNestedNode) children.get(keyIndex + 1);

        ArrayList<Integer> keys = new ArrayList<>();
        keys.addAll(node0.keys);
        keys.add(this.keys.get(keyIndex));
        keys.addAll(node1.keys);

        ArrayList<ABNode> children = new ArrayList<>();
        children.addAll(node0.children);
        children.addAll(node1.children);

        ABNestedNode newNode = new ABNestedNode(keys, children, a, b);

        this.children.remove(keyIndex + 1);
        this.children.set(keyIndex, newNode);
        this.keys.remove(keyIndex);
    }

    int removeBiggestChild() {
        ABNode child = children.get(children.size() - 1);
        if (child instanceof ABLeafNode) {
            children.remove(children.size() - 1);
            int key = keys.get(keys.size() - 1);
            keys.remove(keys.size() - 1);
            return key;
        } else {
            int key = ((ABNestedNode) child).removeBiggestChild();
            balanceChildren(children.size() - 1);
            return key;
        }
    }

    @Override
    int height() {
        return 1 + children.get(0).height();
    }

    @Override
    Integer min() {
        Integer result = children.get(0).min();
        if (result == null) {
            result = keys.get(0);
        }
        return result;
    }

    @Override
    Integer max() {
        Integer result = children.get(children.size() - 1).max();
        if (result == null) {
            result = keys.get(keys.size() - 1);
        }
        return result;
    }

    @Override
    boolean isValid(boolean root) {
        if (children.size() < (root ? 2 : a)) {
            return false;
        }
        if (children.size() > b) {
            return false;
        }
        int h = height();
        for (int i = 0; i < children.size(); i++) {
            if (!children.get(i).isValid(false)) {
                return false;
            }
            if (h != children.get(i).height() + 1) {
                return false;
            }
        }
        if (h == 1) {
            return true;
        }
        for (int i = 0; i < keys.size(); i++) {
            Integer min = children.get(i).min();
            Integer max = children.get(i).max();
            if (max != null && max >= keys.get(i)) {
                return false;
            }
            if (min != null && min >= keys.get(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    String toTex() {

        String ret = "child {node[draw,rectangle split,rectangle split parts=" + this.getKeys().size() + "]{" +
                this.getKeys().get(0);

        for (int i = 1; i < this.getKeys().size(); i++) {
            ret += "\\nodepart{" + Terminal.numberString(i + 1) + "}" + this.getKeys().get(i);
        }
        ret += "}";

        for (int i = 0; i < children.size(); i++) {
            ret += children.get(i).toTex();
        }
        return ret + "}";
    }
}


class ABLeafNode extends ABNode {

    ABLeafNode(int a, int b) {
        super(a, b);
    }

    @Override
    void insert(int key) {
        // do nothing
    }

    boolean stealable() {
        return false;
    }

    boolean find(int key) {
        return false;
    }

    boolean remove(int key) {
        return false;
    }

    int height() {
        return 0;
    }

    Integer min() {
        return null;
    }

    Integer max() {
        return null;
    }

    boolean isValid(boolean root) {
        return true;
    }

    String toTex() {
        return "";
    }
}
