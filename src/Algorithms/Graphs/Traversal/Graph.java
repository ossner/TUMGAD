package Algorithms.Graphs.Traversal;

// Java program to print BFS traversal from a given source vertex.
// BFS(int s) traverses vertices reachable from s.

import DataStructures.Terminal;

import java.util.*;

// This class represents a directed graph using adjacency list
// representation
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

        Terminal.replaceinSB(exerciseStringBuilder, "%$BFS$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$BFS$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$TRAVERSAL$", "\\newpage\n" + traversalExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$TRAVERSAL$", "\\newpage\n" + traversalSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    static Graph generateRandomGraph() {
        // minimum of 10, max of 12
        int numNodes = new Random().nextInt(3) + 10;
        Graph graph = new Graph(numNodes);
        ArrayList<String> nodes = generateNodes(numNodes);
        HashMap<String, Integer> nodeNumMap = assignNodeNumbers(nodes);
        nodeNumMap.put("00", 0);
        nodesToLatex(nodes, nodeNumMap);
        generateVertices(nodes, graph, nodeNumMap);
        return graph;
    }

    private static HashMap<String, Integer> assignNodeNumbers(ArrayList<String> nodes) {
        HashMap<String, Integer> numMap = new HashMap<>();
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < nodes.size(); i++) {
            numbers.add(i);
        }
        for (int i = 1; i < nodes.size(); i++) {
            int rand = new Random().nextInt(numbers.size());
            numMap.put(nodes.get(i), numbers.get(rand));
            numbers.remove(rand);
        }
        return numMap;
    }

    private static void nodesToLatex(ArrayList<String> nodes, HashMap<String, Integer> nodeNumMap) {
        for (int i = 1; i < nodes.size(); i++) {
            Terminal.replaceinSB(traversalExerciseStringBuilder, ", draw=none] (" + nodes.get(i), "] (" + nodes.get(i));
            Terminal.replaceinSB(traversalExerciseStringBuilder, "%$" + nodes.get(i), "$" + nodeNumMap.get(nodes.get(i)));
            Terminal.replaceinSB(traversalSolutionStringBuilder, ", draw=none] (" + nodes.get(i), "] (" + nodes.get(i));
            Terminal.replaceinSB(traversalSolutionStringBuilder, "%$" + nodes.get(i), "$" + nodeNumMap.get(nodes.get(i)));
        }
    }

    private static ArrayList<String> generateNodes(int numNodes) {
        int numRemovals = 16 - numNodes;
        ArrayList<String> nodes = new ArrayList<>(Arrays.asList("00", "01", "02", "03", "10", "11", "12", "13", "20", "21", "22", "23", "30", "31", "32", "33"));

        // remove numremoval many nodes, but never the node 00
        while (numRemovals > 0) {
            nodes.remove(new Random().nextInt(nodes.size() - 1) + 1);
            numRemovals--;
        }
        return nodes;
    }

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

    private static void addVertex(String from, String to) {
        Terminal.replaceinSB(traversalExerciseStringBuilder, "%$CONNECTIONS$", "\\path[->] (" + from + ") edge node {} (" + to + ");\n%$CONNECTIONS$");
        Terminal.replaceinSB(traversalSolutionStringBuilder, "%$CONNECTIONS$", "\\path[->] (" + from + ") edge node {} (" + to + ");\n%$CONNECTIONS$");
    }

    // Function to add an edge into the graph
    void addEdge(int v, int w) {
        adj[v].add(w);
    }

    void BFS() {
        for (int i = 0; i < adj.length; i++) {
            Collections.sort(adj[i]);
        }
        Terminal.replaceinSB(traversalSolutionStringBuilder, "%$BFSVISITSEQUENCE$", "" + 0 + BFS(0));
    }

    // prints BFS traversal from a given source s
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
}