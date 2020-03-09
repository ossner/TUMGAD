package Algorithms.Hashing.Double;

import DataStructures.Terminal;

import java.util.*;

public class DoubleHashing {
    static StringBuilder doubleHashingExerciseStringBuilder;
    static StringBuilder doubleHashingSolutionStringBuilder;
    static String tableTemplate = "Operation: \\underline{\\color{tumgadRed}$DHOPERATION$} \\hspace{10px} Position(s): \\underline{\\color{tumgadRed}$DHPOSITIONS$}\n" +
            "        \\begin{center}\n" +
            "            \\begin{tabular}{|P{0.75cm}|P{0.75cm}|P{0.75cm}|P{0.75cm}|P{0.75cm}|P{0.75cm}|P{0.75cm}|P{0.75cm}|P{0.75cm}|P{0.75cm}|P{0.75cm}|}\n" +
            "                \\hline\n" +
            "                0 & 1 & 2 & 3 & 4 & 5 & 6 & 7 & 8 & 9 & 10  \\\\\n" +
            "                \\hline\n" +
            "                $DHTABLEROW$ \\\\\n" +
            "                \\hline\n" +
            "            \\end{tabular}\n" +
            "        \\end{center}\n" +
            "%$DHTABLE$";

    public static void main(String[] args) {
        generateExercise();
    }

    static int[] generateH1Function() {
        Random rand = new Random();
        int[] a = new int[2];
        a[0] = (rand.nextInt(10) + 1);
        a[1] = (rand.nextInt(10));
        return a;
    }

    //h2(x) = prime1 - (x % prime2)
    static int[] generateH2Function() {
        int[] primes = {5, 7, 11};
        Random rand = new Random();
        int[] a = new int[2];
        a[0] = primes[rand.nextInt(3)];
        a[1] = primes[rand.nextInt(3)];
        return a;
    }

    public static void generateExercise() {
        doubleHashingExerciseStringBuilder = Terminal.readFile("src/Algorithms/Hashing/Double/DoubleHashingExerciseTemplate.tex");
        doubleHashingSolutionStringBuilder = Terminal.readFile("src/Algorithms/Hashing/Double/DoubleHashingSolutionTemplate.tex");

        int[] hash1 = generateH1Function();
        int[] hash2 = generateH2Function();

        Terminal.replaceinSB(doubleHashingExerciseStringBuilder, "NORMALFUNCTION", "h(x) = (" + hash1[0] + "x + " + hash1[1] + ") \\mod 11");
        Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "NORMALFUNCTION", "h(x) = (" + hash1[0] + "x + " + hash1[1] + ") \\mod 11");

        Terminal.replaceinSB(doubleHashingExerciseStringBuilder, "COLLISIONFUNCTION", "h'(x) = " + hash2[0] + " - (x \\mod " + hash2[1] + ") ");
        Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "COLLISIONFUNCTION", "h'(x) = " + hash2[0] + " - (x \\mod " + hash2[1] + ") ");

        // TODO 09/03/2020 sebas: generate more numbers because of small collision rate
        // The next couple of lines generate the numbers the students will have to work with
        int[] numbers = Terminal.generateRandomArray(7, 7);
        int numOfFirstInsertions = new Random().nextInt(3) + 4;
        int numOfSecondInsertions = 7 - numOfFirstInsertions;
        Integer[] firstInsertions = new Integer[numOfFirstInsertions];

        for (int i = 0; i < numOfFirstInsertions; i++) {
            firstInsertions[i] = numbers[i];
        }

        List<Integer> deletions = new ArrayList<>(Arrays.asList(firstInsertions));
        while (deletions.size() > 3) {
            deletions.remove(new Random().nextInt(deletions.size()));
        }
        int[] deletionsArr = new int[3];
        for (int i = 0; i < deletions.size(); i++) {
            deletionsArr[i] = deletions.get(i);
        }
        int[] secondInsertions = new int[numOfSecondInsertions];
        int j = 0;
        for (int i = numOfFirstInsertions + 1; i < numbers.length; i++) {
            secondInsertions[j++] = numbers[i];
        }
        int k = 0;
        for (int i = j; i < numOfSecondInsertions; i++) {
            secondInsertions[i] = deletionsArr[k++];
        }

        ArrayList allInsertions = new ArrayList(Arrays.asList(firstInsertions));
        for (int i = 0; i < numOfSecondInsertions; i++) {
            allInsertions.add(secondInsertions[i]);
        }

        int[][] collisionTable = generateCollisionTable(new HashSet<Integer>(allInsertions).toArray(new Integer[0]), hash1, hash2);
        generateSteps(hash1, hash2, firstInsertions, deletionsArr, secondInsertions);

        Terminal.replaceinSB(doubleHashingExerciseStringBuilder, "$FIRSTINSERTIONS$", Terminal.printArray(firstInsertions));
        Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "$FIRSTINSERTIONS$", Terminal.printArray(firstInsertions));

        Terminal.replaceinSB(doubleHashingExerciseStringBuilder, "$DELETIONS$", Terminal.printArray(deletionsArr));
        Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "$DELETIONS$", Terminal.printArray(deletionsArr));

        Terminal.replaceinSB(doubleHashingExerciseStringBuilder, "$SECONDINSERTIONS$", Terminal.printArray(secondInsertions));
        Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "$SECONDINSERTIONS$", Terminal.printArray(secondInsertions));

        StringBuilder exerciseStringBuilder = Terminal.readFile("docs/Exercises.tex");
        StringBuilder solutionStringBuilder = Terminal.readFile("docs/Solutions.tex");

        Terminal.replaceinSB(exerciseStringBuilder, "%$DoubleHashing$", "\\cellcolor{tumgadPurple}");
        Terminal.replaceinSB(solutionStringBuilder, "%$DoubleHashing$", "\\cellcolor{tumgadRed}");

        Terminal.replaceinSB(exerciseStringBuilder, "%$DOUBLEHASHING$", "\\newpage\n" + doubleHashingExerciseStringBuilder.toString());
        Terminal.replaceinSB(solutionStringBuilder, "%$DOUBLEHASHING$", "\\newpage\n" + doubleHashingSolutionStringBuilder.toString());

        Terminal.saveToFile("docs/Exercises.tex", exerciseStringBuilder);
        Terminal.saveToFile("docs/Solutions.tex", solutionStringBuilder);
    }

    private static void insertToTable(int[] hashTable, int value, int[] h1, int[] h2) {
        int firstHash, hashValue;
        firstHash = hashValue = ((h1[0] * value + h1[1]) % 11);
        String positionString = "" + hashValue;
        int i = 1;
        while (hashTable[hashValue] != -1) {
            hashValue = Math.floorMod((firstHash + i * (h2[0] - Math.floorMod(value, h2[1]))), 11);
            positionString += ", " + hashValue;
            i++;
        }
        hashTable[hashValue] = value;
        Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "%$DHTABLE$", tableTemplate
                .replace("$DHOPERATION$", "Insert(" + value +")")
                .replace("$DHPOSITIONS$", positionString)
                .replace("$DHTABLEROW$", arrayToRow(hashTable))
        );
    }

    private static void deleteFromTable(int[] hashTable, int value, int[] h1, int[] h2) {
        int firstHash, hashValue;
        firstHash = hashValue = ((h1[0] * value + h1[1]) % 11);
        String positionString = "" + hashValue;
        int i = 1;
        while (hashTable[hashValue] != value) {
            hashValue = Math.floorMod((firstHash + i * (h2[0] - Math.floorMod(value, h2[1]))), 11);
            positionString += ", " + hashValue;
            i++;
        }
        hashTable[hashValue] = -1;
        Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "%$DHTABLE$", tableTemplate
                .replace("$DHOPERATION$", "Insert(" + value +")")
                .replace("$DHPOSITIONS$", positionString)
                .replace("$DHTABLEROW$", arrayToRow(hashTable))
        );
    }

    private static String arrayToRow(int[] hashTable) {
        String ret = hashTable[0] == -1 ? "" : "" + hashTable[0];
        for (int i = 1; i < hashTable.length; i++) {
            ret += hashTable[i] == -1 ? "&" : "&" + hashTable[i];
        }
        return ret;
    }

    private static void generateSteps(int[] h1, int[] h2, Integer[] firstInsertions, int[] deletionsArr, int[] secondInsertions) {
        int[] hashTable = new int[11];
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = -1;
        }
        for (int i = 0; i < firstInsertions.length; i++) {
            //Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "$DHOPERATION" + (i + 1) + "$", "Insert(" + firstInsertions[i] + ")");
            insertToTable(hashTable, firstInsertions[i], h1, h2);
        }
        for (int i = 0; i < deletionsArr.length; i++) {
            //Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "$DHOPERATION" + (firstInsertions.length + i + 1) + "$", "Delete(" + deletionsArr[i] + ")");
            deleteFromTable(hashTable, deletionsArr[i], h1, h2);
        }
        for (int i = 0; i < secondInsertions.length; i++) {
            //Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "$DHOPERATION" + (firstInsertions.length + deletionsArr.length + i + 1) + "$", "Insert(" + secondInsertions[i] + ")");
            insertToTable(hashTable, secondInsertions[i], h1, h2);
        }
    }

    private static int[][] generateCollisionTable(Integer[] numbers, int[] h1, int[] h2) {
        int[][] collisionTable = new int[numbers.length][9];

        for (int i = 0; i < numbers.length; i++) {
            int hash1 = Math.floorMod(h1[0] * numbers[i] + h1[1], 11);
            int hash2 = h2[0] - (numbers[i] % h2[1]);
            String collisionRow = "" + numbers[i] + " & " + hash1 + " & " + hash2;
            collisionTable[i][0] = numbers[i];
            collisionTable[i][1] = hash1;
            collisionTable[i][2] = hash2;

            int[] collisionHash = new int[6];
            for (int j = 0; j < 6; j++) {
                collisionHash[j] = Math.floorMod((hash1 + j * hash2), 11);
                if (collisionHash[j] < 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                collisionTable[i][j + 3] = collisionHash[j];
                collisionRow += " & " + collisionHash[j];
            }
            Terminal.replaceinSB(doubleHashingSolutionStringBuilder, "%$COLLISIONTABLE$", collisionRow + "\\\\\n\\hline\n%$COLLISIONTABLE$");
        }
        return collisionTable;
    }
}
