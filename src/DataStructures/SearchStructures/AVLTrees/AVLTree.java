package DataStructures.SearchStructures.AVLTrees;

import Util.Terminal;

import java.util.ArrayList;

class Node {
    int key, height;
    Node left, right;

    Node(int d) {
        key = d;
        height = 1;
    }
}

public class AVLTree {
    private static StringBuilder avlTreeExerciseStringBuilder;
    private static StringBuilder avlTreeSolutionStringBuilder;
    private static boolean checkBoxWritten;
    private Node root;

    public static void generateExercise() {
        checkBoxWritten = true;
        avlTreeExerciseStringBuilder = Terminal.readFile("src/DataStructures/SearchStructures/AVLTrees/AVLTreeExerciseTemplate.tex");
        avlTreeSolutionStringBuilder = Terminal.readFile("src/DataStructures/SearchStructures/AVLTrees/AVLTreeSolutionTemplate.tex");

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$AVLCELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$AVLCELL$", "\\cellcolor{tumgadRed}");

        int[] values = Terminal.generateRandomArray(12, 12);
        int[] initValues = new int[5];
        int[] firstInserts = new int[5];
        int[] secondInserts = new int[2];

        System.arraycopy(values, 0, initValues, 0, 5);
        System.arraycopy(values, 5, firstInserts, 0, 5);
        System.arraycopy(values, 10, secondInserts, 0, 2);

        int[] deletions = new int[]{initValues[1], firstInserts[3]};

        AVLTree tree = new AVLTree();
        for (int i = 0; i < initValues.length; i++) {
            tree.root = tree.insert(tree.root, initValues[i]);
        }
        tree.treeToTex("%$INITTREE$");
        Terminal.replaceinSB(avlTreeExerciseStringBuilder, "$FIRSTINSERTS$", Terminal.printArray(firstInserts));
        Terminal.replaceinSB(avlTreeExerciseStringBuilder, "$DELETIONS$", Terminal.printArray(deletions));
        Terminal.replaceinSB(avlTreeExerciseStringBuilder, "$SECONDINSERTS$", Terminal.printArray(secondInserts));

        Terminal.replaceinSB(avlTreeSolutionStringBuilder, "$FIRSTINSERTS$", Terminal.printArray(firstInserts));
        Terminal.replaceinSB(avlTreeSolutionStringBuilder, "$DELETIONS$", Terminal.printArray(deletions));
        Terminal.replaceinSB(avlTreeSolutionStringBuilder, "$SECONDINSERTS$", Terminal.printArray(secondInserts));
        checkBoxWritten = false;
        for (int i = 0; i < firstInserts.length; i++) {
            tree.root = tree.insert(tree.root, firstInserts[i]);
            if (!checkBoxWritten) {
                writeCheckboxNoRotation(firstInserts[i], true);
            }
            checkBoxWritten = false;
            tree.treeToTex("%$AVLTREE$");
        }
        for (int i = 0; i < deletions.length; i++) {
            tree.root = tree.deleteNode(tree.root, deletions[i]);
            if (!checkBoxWritten) {
                writeCheckboxNoRotation(deletions[i], false);
            }
            checkBoxWritten = false;
            tree.treeToTex("%$AVLTREE$");
        }
        Terminal.replaceinSB(avlTreeSolutionStringBuilder, "%$AVLTREE$", "\\newpage\\noindent%$AVLTREE$");
        for (int i = 0; i < secondInserts.length; i++) {
            tree.root = tree.insert(tree.root, secondInserts[i]);
            if (!checkBoxWritten) {
                writeCheckboxNoRotation(secondInserts[i], true);
            }
            checkBoxWritten = false;
            tree.treeToTex("%$AVLTREE$");
        }

        if (Terminal.rand.nextBoolean()) {
            Terminal.replaceinSB(avlTreeExerciseStringBuilder, "$PRINTORDER$", "Post-Order");
            Terminal.replaceinSB(avlTreeSolutionStringBuilder, "$PRINTORDER$", "Post-Order");
            Terminal.replaceinSB(avlTreeSolutionStringBuilder, "%$AVLVISITSEQUENCE$", Terminal.printArrayList(tree.postOrder(tree.root)));
        } else {
            Terminal.replaceinSB(avlTreeExerciseStringBuilder, "$PRINTORDER$", "Pre-Order");
            Terminal.replaceinSB(avlTreeSolutionStringBuilder, "$PRINTORDER$", "Pre-Order");
            Terminal.replaceinSB(avlTreeSolutionStringBuilder, "%$AVLVISITSEQUENCE$", Terminal.printArrayList(tree.preOrder(tree.root)));
        }

        Terminal.replaceinSB(exerciseStringBuilder, "%$AVLTREES$", "\\newpage\n" + avlTreeExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$AVLTREES$", "\\newpage\n" + avlTreeSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    private static void writeCheckboxNoRotation(int number, boolean insert) {
        Terminal.replaceinSB(avlTreeSolutionStringBuilder, "%$AVLTREE$", "\n" + (insert ? "Insert" : "Delete") +
                ": \\underline{\\color{tumgadRed}" + number + "\\color{black}}\n" +
                "\\hspace{.1cm}\n" +
                "\\makebox[2.3cm][l]{$\\square$ l rotation}\n" +
                "\\makebox[2.3cm][l]{$\\square$ r rotation}\n" +
                "\\makebox[2.5cm][l]{$\\square$ l-r rotation}\n" +
                "\\makebox[2.5cm][l]{$\\square$ r-l rotation}\n" +
                "\\makebox[2.3cm][l]{$\\boxtimes$ no rotation}\n%$AVLTREE$");
    }

    /**
     * returns the height of the tree
     */
    int height(Node n) {
        if (n == null) {
            return 0;
        }
        return n.height;
    }

    // A utility function to get maximum of two integers
    int max(int a, int b) {
        return Math.max(a, b);
    }

    /**
     * rotates the tree right at a given node
     *
     * @param y the central node affected by the rotation
     */
    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    /**
     * rotates the tree left at a given node
     *
     * @param x the central node affected by the rotation
     */
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    /**
     * returns the balance of a node (discrepancy between left height and right height)
     */
    int getBalance(Node N) {
        if (N == null) {
            return 0;
        }
        return height(N.left) - height(N.right);
    }

    /**
     * insert a node into the tree
     *
     * @param key  the key which has to be inserted
     * @param node the node at which the key has to be inserted (usually root when
     *             called from outside)
     */
    Node insert(Node node, int key) {
        if (node == null) {
            return (new Node(key));
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            return node;
        }

        node.height = 1 + max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // perform rotations in case of imbalance
        if (balance > 1 && key < node.left.key) {
            if (!checkBoxWritten) {
                Terminal.replaceinSB(avlTreeSolutionStringBuilder, "%$AVLTREE$", "\n" +
                        "Insert: \\underline{\\color{tumgadRed}" + key + "\\color{black}}\n" +
                        "\\hspace{.1cm}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\boxtimes$ r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\square$ l-r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\square$ r-l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ no rotation}\n%$AVLTREE$");
            }
            checkBoxWritten = true;
            return rightRotate(node);
        } else if (balance < -1 && key > node.right.key) {
            if (!checkBoxWritten) {
                Terminal.replaceinSB(avlTreeSolutionStringBuilder, "%$AVLTREE$", "\n" +
                        "Insert: \\underline{\\color{tumgadRed}" + key + "\\color{black}}\n" +
                        "\\hspace{.1cm}\n" +
                        "\\makebox[2.3cm][l]{$\\boxtimes$ l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\square$ l-r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\square$ r-l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ no rotation}\n%$AVLTREE$");
            }
            checkBoxWritten = true;
            return leftRotate(node);
        } else if (balance > 1 && key > node.left.key) {
            if (!checkBoxWritten) {
                Terminal.replaceinSB(avlTreeSolutionStringBuilder, "%$AVLTREE$", "\n" +
                        "Insert: \\underline{\\color{tumgadRed}" + key + "\\color{black}}\n" +
                        "\\hspace{.1cm}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\boxtimes$ l-r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\square$ r-l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ no rotation}\n%$AVLTREE$");
            }
            checkBoxWritten = true;
            node.left = leftRotate(node.left);
            return rightRotate(node);
        } else if (balance < -1 && key < node.right.key) {
            if (!checkBoxWritten) {
                Terminal.replaceinSB(avlTreeSolutionStringBuilder, "%$AVLTREE$", "\n" +
                        "Insert: \\underline{\\color{tumgadRed}" + key + "\\color{black}}\n" +
                        "\\hspace{.1cm}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\square$ l-r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\boxtimes$ r-l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ no rotation}\n%$AVLTREE$");
            }
            checkBoxWritten = true;
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * returns a reference to the minimal node in the given tree
     */
    Node minValueNode(Node node) {
        Node current = node;

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    Node deleteNode(Node root, int key) {
        if (root == null) {
            return null;
        }

        if (key < root.key) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.key) {
            root.right = deleteNode(root.right, key);
        } else {

            // node with only one child or no child
            if (root.left == null || root.right == null) {
                Node temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }
                // No child case
                if (temp == null) {
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = deleteNode(root.right, temp.key);
            }
        }

        // If the tree had only one node then return
        if (root == null) {
            return root;
        }

        root.height = max(height(root.left), height(root.right)) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0) {
            if (!checkBoxWritten) {
                Terminal.replaceinSB(avlTreeSolutionStringBuilder, "%$AVLTREE$", "\n" +
                        "Delete: \\underline{\\color{tumgadRed}" + key + "\\color{black}}\n" +
                        "\\hspace{.1cm}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\boxtimes$ r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\square$ l-r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\square$ r-l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ no rotation}\n%$AVLTREE$");
            }
            checkBoxWritten = true;
            return rightRotate(root);
        } else if (balance > 1 && getBalance(root.left) < 0) {
            if (!checkBoxWritten) {
                Terminal.replaceinSB(avlTreeSolutionStringBuilder, "%$AVLTREE$", "\n" +
                        "Delete: \\underline{\\color{tumgadRed}" + key + "\\color{black}}\n" +
                        "\\hspace{.1cm}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\boxtimes$ l-r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\square$ r-l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ no rotation}\n%$AVLTREE$");
            }
            root.left = leftRotate(root.left);
            checkBoxWritten = true;
            return rightRotate(root);
        } else if (balance < -1 && getBalance(root.right) <= 0) {
            if (!checkBoxWritten) {
                Terminal.replaceinSB(avlTreeSolutionStringBuilder, "%$AVLTREE$", "\n" +
                        "Delete: \\underline{\\color{tumgadRed}" + key + "\\color{black}}\n" +
                        "\\hspace{.1cm}\n" +
                        "\\makebox[2.3cm][l]{$\\boxtimes$ l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\square$ l-r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\square$ r-l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ no rotation}\n%$AVLTREE$");
            }
            checkBoxWritten = true;
            return leftRotate(root);
        } else if (balance < -1 && getBalance(root.right) > 0) {
            if (!checkBoxWritten) {
                Terminal.replaceinSB(avlTreeSolutionStringBuilder, "%$AVLTREE$", "\n" +
                        "Delete: \\underline{\\color{tumgadRed}" + key + "\\color{black}}\n" +
                        "\\hspace{.1cm}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\square$ l-r rotation}\n" +
                        "\\makebox[2.5cm][l]{$\\boxtimes$ r-l rotation}\n" +
                        "\\makebox[2.3cm][l]{$\\square$ no rotation}\n%$AVLTREE$");
            }
            root.right = rightRotate(root.right);
            checkBoxWritten = true;
            return leftRotate(root);
        }

        return root;
    }

    private ArrayList<Integer> postOrder(Node node) {
        ArrayList<Integer> postOrder = new ArrayList<>();
        if (node != null) {
            postOrder.addAll(postOrder(node.left));
            postOrder.addAll(postOrder(node.right));
            postOrder.add(node.key);
        }
        return postOrder;
    }

    private ArrayList<Integer> preOrder(Node node) {
        ArrayList<Integer> preOrder = new ArrayList<>();
        if (node != null) {
            preOrder.add(node.key);
            preOrder.addAll(preOrder(node.left));
            preOrder.addAll(preOrder(node.right));
        }
        return preOrder;
    }

    void treeToTex(String placeholder) {
        if (placeholder.equals("%$INITTREE$")) {
            Terminal.replaceinSB(avlTreeExerciseStringBuilder, placeholder, "\\begin{center}\\begin{tikzpicture}[level/.style={sibling distance=55mm/#1},minimum size=20pt,scale=.8,\n" +
                    "every node/.style={transform shape}]\n" +
                    "\\node[circle,draw](z){" + this.root.key + "}" + treeToTex(this.root) + ";\\end{tikzpicture}\\end{center}" +
                    placeholder);
        }
        Terminal.replaceinSB(avlTreeSolutionStringBuilder, placeholder, "\\begin{center}\\begin{tikzpicture}[level/.style={sibling distance=55mm/#1},minimum size=20pt,scale=.8,\n" +
                "every node/.style={transform shape}]\n" +
                "\\node[circle,draw](z){" + this.root.key + "}" + treeToTex(this.root) + ";\\end{tikzpicture}\\end{center}" +
                placeholder);
    }

    private String treeToTex(Node root) {
        String ret = "";
        if (root.left == null) {
            ret += "child[missing]{}";
        } else {
            ret += "child{node[circle,draw]{" + root.left.key + "}" + treeToTex(root.left) + "}";
        }
        if (root.right == null) {
            ret += "child[missing]{}";
        } else {
            ret += "child{node[circle,draw]{" + root.right.key + "}" + treeToTex(root.right) + "}";
        }
        return ret;
    }
}
