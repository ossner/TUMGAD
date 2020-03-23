package Algorithms.Graphs.APSP.FloydWarshall;

import Util.Terminal;

public class FloydWarshall {
    private static StringBuilder fwExerciseStringBuilder;
    private static StringBuilder fwSolutionStringBuilder;
    final static int INF = 99999;

    public static void generateExercise() {
        fwExerciseStringBuilder = Terminal.readFile("src/Algorithms/Graphs/APSP/FloydWarshall/FWExerciseTemplate.tex");
        fwSolutionStringBuilder = Terminal.readFile("src/Algorithms/Graphs/APSP/FloydWarshall/FWSolutionTemplate.tex");

        int[][] dist = generateDistMatrix();
        generateConnections(dist);
        Terminal.replaceinSB(fwSolutionStringBuilder, "%$DISTTABLE$", "\\noindent\\fbox{\\parbox{7cm}{\\vspace{7px}Initial Matrix: \\begin{center}" + distToTable(dist) + "\\end{center}}}%$DISTTABLE$");
        floydWarshallAlgorithm(dist);

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$APSPFWCELL$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$APSPFWCELL$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$FLOYDWARSHALL$", "\\newpage\n" + fwExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$FLOYDWARSHALL$", "\\newpage\n" + fwSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    private static void floydWarshallAlgorithm(int[][] dist) {
        for (int k = 0; k < dist.length; k++) {
            for (int i = 0; i < dist.length; i++) {
                for (int j = 0; j < dist.length; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
            if (k % 2 == 0) {
                Terminal.replaceinSB(fwSolutionStringBuilder, "%$DISTTABLE$", "\\\\" +
                        "\\vspace{20px}%$DISTTABLE$");
            } else {
                Terminal.replaceinSB(fwSolutionStringBuilder, "%$DISTTABLE$", "\\hspace{20px}%$DISTTABLE$");
            }
            Terminal.replaceinSB(fwSolutionStringBuilder, "%$DISTTABLE$",
                    "\\noindent\\fbox{\\parbox{7cm}{\\vspace{7px}Matrix for k = \\underline{\\color{tumgadRed}" +
                            Character.toString('a' + k) + "\\color{black}}: \\begin{center}" + distToTable(dist) + "\\end{center}}}%$DISTTABLE$");
        }
    }

    private static void generateConnections(int[][] dist) {
        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist.length; j++) {
                if (dist[i][j] != INF) {
                    Terminal.replaceinSB(fwExerciseStringBuilder, "%$EDGES$", "\\path[->] (" + Character.toString('a' +
                            i) + ") edge[bend right=20] node[pos=0.25] {" + dist[i][j] + "} (" + Character.toString('a' + j) + ");\n%$EDGES$");

                    Terminal.replaceinSB(fwSolutionStringBuilder, "%$EDGES$", "\\path[->] (" + Character.toString('a' +
                            i) + ") edge[bend right=20] node[pos=0.25] {" + dist[i][j] + "} (" + Character.toString('a' + j) + ");\n%$EDGES$");
                }
            }
        }
    }

    private static String distToTable(int[][] dist) {
        String ret = "\\begin{tabular}{P{0.75cm}|P{0.75cm}|P{0.75cm}|P{0.75cm}|P{0.75cm}}";
        ret += "\n&a&b&c&d\\\\[2ex]";
        for (int i = 0; i < dist.length; i++) {
            ret += "\n\\hline\n" + Character.toString('a' + i);
            for (int j = 0; j < dist.length; j++) {
                ret += "&";
                if (i == j) {
                    ret += 0;
                } else if (dist[i][j] >= INF) {
                    ret += "$\\infty$";
                } else {
                    ret += dist[i][j];
                }
            }
            ret += "\\\\[2ex]\n";
        }
        return ret + "\n\\end{tabular}";
    }

    private static int[][] generateDistMatrix() {
        int[][] dist = new int[4][4];
        int available = 6;
        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist.length; j++) {
                dist[i][j] = INF;
            }
        }
        while (available > 0) {
            int row = Terminal.rand.nextInt(4);
            int col = Terminal.rand.nextInt(4);
            if (row != col && dist[row][col] == INF) {
                dist[row][col] = Terminal.rand.nextInt(16);
                available--;
            }
        }
        return dist;
    }
}
