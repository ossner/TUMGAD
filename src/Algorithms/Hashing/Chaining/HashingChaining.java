package Algorithms.Hashing.Chaining;

import Algorithms.Hashing.Double.DoubleHashing;
import Util.Terminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HashingChaining {
    static StringBuilder hashingChainingExerciseStringBuilder;
    static StringBuilder hashingChainingSolutionStringBuilder;
    static int[] hashFunction = DoubleHashing.generateH1Function();
    static List<Integer>[] hashTable;

    public static void generateExercise() {
        hashingChainingExerciseStringBuilder = Terminal.readFile("src/Algorithms/Hashing/Chaining/HashingChainingExerciseTemplate.tex");
        hashingChainingSolutionStringBuilder = Terminal.readFile("src/Algorithms/Hashing/Chaining/HashingChainingSolutionTemplate.tex");

        hashFunction = DoubleHashing.generateH1Function();
        int hashTableSize = 7;
        if (new Random().nextBoolean()) {
            hashTableSize = 11;
        }
        hashTable = new ArrayList[hashTableSize];
        for (int i = 0; i < hashTableSize; i++) {
            hashTable[i] = new ArrayList<>();
        }

        Terminal.replaceinSB(hashingChainingExerciseStringBuilder, "HCTABLESIZE", "" + hashTableSize);
        Terminal.replaceinSB(hashingChainingSolutionStringBuilder, "HCTABLESIZE", "" + hashTableSize);
        Terminal.replaceinSB(hashingChainingExerciseStringBuilder, "NORMALFUNCTION",
                "h(x) = (" + hashFunction[0] + "x + " + hashFunction[1] + ") " + " \\mod " + hashTableSize);
        Terminal.replaceinSB(hashingChainingSolutionStringBuilder, "NORMALFUNCTION",
                "h(x) = (" + hashFunction[0] + "x + " + hashFunction[1] + ") " + " \\mod " + hashTableSize);

        int[] numbers = Terminal.generateRandomArray(hashTableSize, hashTableSize + 1);

        Terminal.replaceinSB(hashingChainingExerciseStringBuilder, "$INSERTIONS$", Terminal.printArray(numbers));
        Terminal.replaceinSB(hashingChainingSolutionStringBuilder, "$INSERTIONS$", Terminal.printArray(numbers));

        for (int i = 0; i < numbers.length; i++) {
            insertIntoTable(numbers[i]);
        }

        Arrays.sort(numbers);
        createMappingTable(numbers);
        for (int i = 0; i < numbers.length; i++) {
            generateTablePrePrints();
        }

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$HashingChaining$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$HashingChaining$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$HASHINGCHAINING$", "\\newpage\n" + hashingChainingExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$HASHINGCHAINING$", "\\newpage\n" + hashingChainingSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    /**
     * generates the mapping table, a table that shows the value a number maps
     * to when inserting it into the hashfunction
     *
     * @param numbers the sorted array of all the numbers that will be mapped
     */
    private static void createMappingTable(int[] numbers) {
        String template = "\\begin{tabular}{P{1.2cm}|";
        for (int i = 0; i < numbers.length; i++) {
            template += "|P{0.6cm}";
        }
        template += "}\n$x$";
        for (int i = 0; i < numbers.length; i++) {
            template += " & " + numbers[i];
        }
        template += "\\\\\n\\hline\n$h(x)$";
        String prePrint = template;
        for (int i = 1; i < numbers.length; i++) {
            prePrint += "&";
        }
        for (int i = 0; i < numbers.length; i++) {
            template += " & " + Math.floorMod((hashFunction[0] * numbers[i] + hashFunction[1]), hashTable.length);
        }
        template += "\\\\\n";
        template += "\\end{tabular}";

        prePrint += "\\\\\n";
        prePrint += "\\end{tabular}";
        Terminal.replaceinSB(hashingChainingExerciseStringBuilder, "$MAPPINGTABLEPREPRINT$", prePrint);
        Terminal.replaceinSB(hashingChainingSolutionStringBuilder, "$MAPPINGTABLESOLUTION$", template);
    }

    /**
     * Insert a value into a table and generate the new table and add it to the LaTeX
     * template
     *
     * @param value the value to be inserted into the hashtable
     */
    private static void insertIntoTable(int value) {
        hashTable[Math.floorMod(hashFunction[0] * value + hashFunction[1], hashTable.length)].add(value);
        String template = "\\color{black}Insert: \\underline{\\color{tumgadRed}" + value + "}\\begin{center}\\begin{tabular}{|P{0.6cm}";
        for (int i = 0; i < hashTable.length - 1; i++) {
            template += "|P{0.6cm}";
        }
        template += "|}\n\\hline\n0";
        for (int i = 1; i < hashTable.length; i++) {
            template += " & " + i;
        }
        template += "\\\\\n\\hline\n";
        int max = 0;
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i].size() > max) {
                max = hashTable[i].size();
            }
        }

        for (int i = 0; i < max; i++) {
            template += (hashTable[0].size() > i ? hashTable[0].get(i) : "");
            for (int j = 1; j < hashTable.length; j++) {
                template += "&" + (hashTable[j].size() > i ? hashTable[j].get(i) : "");
            }
            template += "\\\\";
        }
        template += "\n\\hline";
        template += "\\end{tabular}\\end{center}";
        Terminal.replaceinSB(hashingChainingSolutionStringBuilder, "%$HASHTABLE$", template + "\n%$HASHTABLE$");
    }

    /**
     * generate the empty hashtable preprints, one for every insert operation
     */
    private static void generateTablePrePrints() {
        String template = "Insert: \\underline{\\hspace{1cm}}\\begin{center}\\begin{tabular}{|P{0.6cm}";
        for (int i = 0; i < hashTable.length - 1; i++) {
            template += "|P{0.6cm}";
        }
        template += "|}\n\\hline\n0";
        for (int i = 1; i < hashTable.length; i++) {
            template += " & " + i;
        }
        template += "\\\\\n\\hline\n";
        for (int i = 1; i < hashTable.length; i++) {
            template += " & ";
        }
        template += "\\\\[7ex]\n\\hline";
        template += "\\end{tabular}\\end{center}";
        template += "%$HASHTABLE$";

        Terminal.replaceinSB(hashingChainingExerciseStringBuilder, "%$HASHTABLE$", template);
    }
}
