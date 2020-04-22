package Algorithms.Graphs.Traversal;

import Util.Terminal;

import java.util.*;

/**
 * A Graph class with adjacency list representation, capable of generating
 * exercises for BFS-traversal
 */
public class Graph {
    static StringBuilder traversalExerciseStringBuilder;
    static StringBuilder traversalSolutionStringBuilder;

    private int numVertices;
    private LinkedList<Integer>[] adj; //Adjacency Lists

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        adj = new LinkedList[numVertices];
        for (int i = 0; i < numVertices; ++i) {
            adj[i] = new LinkedList();
        }
    }

    public static void generateExercise() {
        traversalExerciseStringBuilder = Terminal.readFile("src/Algorithms/Graphs/Traversal/TraversalExerciseTemplate.tex");
        traversalSolutionStringBuilder = Terminal.readFile("src/Algorithms/Graphs/Traversal/TraversalSolutionTemplate.tex");

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Graph graph = generateRandomGraph();

        graph.BFS();
        graph.DFS();

        Terminal.replaceinSB(exerciseStringBuilder, "%$TRAV$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$TRAV$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$TRAVERSAL$", "\\newpage\n" + traversalExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$TRAVERSAL$", "\\newpage\n" + traversalSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    /**
     * Method that generates a random graph with min. 10 nodes and max. 12 nodes
     */
    static Graph generateRandomGraph() {
        // minimum of 10, max of 12
        int numNodes = Terminal.rand.nextInt(3) + 10;
        Graph graph = new Graph(numNodes);
        ArrayList<String> nodes = generateNodes(numNodes);
        HashMap<String, Integer> nodeNumMap = assignNodeNumbers(nodes);
        nodeNumMap.put("00", 0);
        nodesToLatex(nodes, nodeNumMap);
        generateVertices(nodes, graph, nodeNumMap);
        return graph;
    }

    /**
     * Method that assigns every node in the graph a number, depending on how many nodes there are
     * e.g. if there are 12 nodes, they will be numbered from 0-11
     *
     * @param nodes The final list of nodes that the graph will hold
     * @return A HashMap mapping every node-id in the list of nodes to its representational number
     */
    private static HashMap<String, Integer> assignNodeNumbers(ArrayList<String> nodes) {
        HashMap<String, Integer> numMap = new HashMap<>();
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < nodes.size(); i++) {
            numbers.add(i);
        }
        for (int i = 1; i < nodes.size(); i++) {
            int rand = Terminal.rand.nextInt(numbers.size());
            numMap.put(nodes.get(i), numbers.get(rand));
            numbers.remove(rand);
        }
        return numMap;
    }

    /**
     * In the beginning, every node is invisible in the LaTeX template, this method takes the node-ids
     * from the nodes that will be in the graph and makes them visible. It will also assign them
     * the number they should have
     *
     * @param nodes      The list of node-ids that are in the graph
     * @param nodeNumMap A HashMap mapping the node-ids of a graph to its actual number
     */
    private static void nodesToLatex(ArrayList<String> nodes, HashMap<String, Integer> nodeNumMap) {
        for (int i = 1; i < nodes.size(); i++) {
            Terminal.replaceinSB(traversalExerciseStringBuilder, ", draw=none] (" + nodes.get(i), "] (" + nodes.get(i));
            Terminal.replaceinSB(traversalExerciseStringBuilder, "%$" + nodes.get(i), "$" + nodeNumMap.get(nodes.get(i)));
            Terminal.replaceinSB(traversalSolutionStringBuilder, ", draw=none] (" + nodes.get(i), "] (" + nodes.get(i));
            Terminal.replaceinSB(traversalSolutionStringBuilder, "%$" + nodes.get(i), "$" + nodeNumMap.get(nodes.get(i)));
        }
    }

    /**
     * Method that will generate a List of node-ids that will be in the graph (think of
     * them as aligned on a 4x4 grid)
     *
     * @param numNodes The number of nodes the graph should contain
     * @return The list of node-ids that will be in the graph in the end
     */
    private static ArrayList<String> generateNodes(int numNodes) {
        int numRemovals = 16 - numNodes;
        ArrayList<String> nodes = new ArrayList<>(Arrays.asList("00", "01", "02", "03", "10", "11", "12", "13", "20", "21", "22", "23", "30", "31", "32", "33"));

        // remove numremoval many nodes, but never the node 00
        while (numRemovals > 0) {
            nodes.remove(Terminal.rand.nextInt(nodes.size() - 1) + 1);
            numRemovals--;
        }
        return nodes;
    }

    /**
     * Generates connections between nodes of a graph, without making them unreadable on LaTeX while still
     * making sure enough nodes for a (mildly) challenging exercise are generated
     * <p>
     * What it will not do: generate vertices from a node to another node, when the vertex would
     * "go through" another node in the LaTeX graph.
     * <p>
     * What it will do: since our nodes are stationed on a 4x4 grid, with some nodes being invisible,
     * this method will
     * - connect every node with its successor in the Graph
     * - connect a node with its neighbour below if possible
     * - connect a node with its neighbour below to the right if possible
     *
     * @param nodes  The list of node-ids that are actually in the graph
     * @param graph  The graph to which the edges will be added to
     * @param numMap The HashMap mapping every node-id in the graph to its actual number
     */
    private static void generateVertices(ArrayList<String> nodes, Graph graph, HashMap<String, Integer> numMap) {
        for (int i = 1; i < nodes.size(); i++) {
            // connect each node with its successor
            addVertex(nodes.get(i - 1), nodes.get(i));
            graph.addEdge(numMap.get(nodes.get(i - 1)), numMap.get(nodes.get(i)));

            char charOne = nodes.get(i - 1).charAt(0);
            char charTwo = nodes.get(i - 1).charAt(1);
            int intOne = Character.getNumericValue(charOne);
            int intTwo = Character.getNumericValue(charTwo);
            // connect node with the node below if possible
            if (nodes.contains(intOne + 1 + "" + charTwo)) {
                addVertex(nodes.get(i - 1), intOne + 1 + "" + charTwo);
                graph.addEdge(numMap.get(nodes.get(i - 1)), numMap.get(intOne + 1 + "" + charTwo));
            }
            // connect node with node below right node if possible
            if (nodes.contains(intOne + 1 + "" + (intTwo + 1))) {
                addVertex(nodes.get(i - 1), intOne + 1 + "" + (intTwo + 1));
                graph.addEdge(numMap.get(nodes.get(i - 1)), numMap.get(intOne + 1 + "" + (intTwo + 1)));
            }
        }
    }

    /**
     * Adds a LaTeX graph connection from one given node to another
     *
     * @param from The node from which the connection will go out of
     * @param to   The node to which the arrow will point
     */
    private static void addVertex(String from, String to) {
        Terminal.replaceinSB(traversalExerciseStringBuilder, "%$CONNECTIONS$", "\\path[->] (" + from + ") edge node {} (" + to + ");\n%$CONNECTIONS$");
        Terminal.replaceinSB(traversalSolutionStringBuilder, "%$CONNECTIONS$", "\\path[->] (" + from + ") edge node {} (" + to + ");\n%$CONNECTIONS$");
    }

    void addEdge(int v, int w) {
        adj[v].add(w);
    }

    void BFS() {
        for (int i = 0; i < adj.length; i++) {
            Collections.sort(adj[i]);
        }
        Terminal.replaceinSB(traversalSolutionStringBuilder, "%$BFSVISITSEQUENCE$", "0" + BFS(0));
    }

    /**
     * Simulates the Breadth-First-Search algorithm from a starting node s
     *
     * @param s The node from which the BFS-algorithm will start
     * @return A String of node-numbers in the sequence of their visitation (excluding the start node)
     */
    String BFS(int s) {
        String ret = "";
        // Mark all the vertices as not visited(By default
        // set as false)
        boolean[] visited = new boolean[numVertices];

        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<>();

        // Mark the current node as visited and enqueue it
        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            if (s != 0) {
                ret += ", " + s;
            }

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
        return ret;
    }

    void DFS() {
        for (int i = 0; i < adj.length; i++) {
            Collections.sort(adj[i]);
        }
        Terminal.replaceinSB(traversalSolutionStringBuilder, "%$DFSVISITSEQUENCE$", "0" + DFS(0));

    }

    // The function to do DFS traversal. It uses recursive DFSUtil()
    String DFS(int v) {
        // Mark all the vertices as not visited(set as
        // false by default in java)
        boolean[] visited = new boolean[numVertices];

        // Call the recursive helper function to print DFS traversal
        return DFSUtil(v, visited);
    }

    // A function used by DFS
    String DFSUtil(int v, boolean[] visited) {
        String ret = "";
        // Mark the current node as visited and print it
        visited[v] = true;

        if (v != 0) {
            ret += ", " + v;
        }

        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> i = adj[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n]) {
                ret += DFSUtil(n, visited);
            }
        }
        return ret;
    }
}
