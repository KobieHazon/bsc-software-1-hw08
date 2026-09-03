package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;

public class FileIndexTester {
    public static final String INPUT_FOLDER = "resources/hw8/input";

    public static void main(String[] args) {
        testRankedWordComparator();
        testFileIndex();
        System.out.println("done!");
    }

    public static void testRankedWordComparator() {
        Map<String, Integer> ranks1 = new HashMap<>();
        ranks1.put("file1", 1);
        ranks1.put("file2", 120);
        ranks1.put("file3", 8);
        RankedWord rankedWord1 = new RankedWord("julia", ranks1);

        Map<String, Integer> ranks2 = new HashMap<>();
        ranks2.put("file1", 70);
        ranks2.put("file2", 50);
        ranks2.put("file3", 3);
        RankedWord rankedWord2 = new RankedWord("eleanor", ranks2);
        RankedWordComparator minComparator = new RankedWordComparator(rankType.min);
        RankedWordComparator maxComparator = new RankedWordComparator(rankType.max);
        RankedWordComparator averageComparator = new RankedWordComparator(rankType.average);

        if (minComparator.compare(rankedWord1, rankedWord2) >= 0) {
            printErrorNum(1);
        }
        if (maxComparator.compare(rankedWord1, rankedWord2) <= 0) {
            printErrorNum(2);
        }
        if (averageComparator.compare(rankedWord1, rankedWord2) <= 0) {
            printErrorNum(3);
        }
        System.out.println("finished RankedWord Test!");
    }

    public static void testFileIndex() {
        FileIndex fileIndex = new FileIndex();
        fileIndex.indexDirectory(INPUT_FOLDER);
        try {
            if (fileIndex.getCountInFile("doc1.txt", "Alpha") != 3) {
                printErrorNum(4);
            }
            if (fileIndex.getRankForWordInFile("doc2.txt", "beta") != 1) {
                printErrorNum(5);
            }
        } catch (FileIndexException e) {
            printErrorNum(6);
        }
        try {
            fileIndex.getRankForWordInFile("missing.txt", "alpha");
            printErrorNum(7);
        } catch (FileIndexException e) {
            // expected
        }

        if (fileIndex.getAverageRankForWord("alpha") != 2) {
            printErrorNum(8);
        }
        List<String> topByMin = fileIndex.getWordsBelowMinRank(1);
        if (!topByMin.contains("alpha") || !topByMin.contains("beta") || !topByMin.contains("gamma")) {
            printErrorNum(9);
        }

        List<String> topByMax = fileIndex.getWordsAboveMaxRank(2);
        if (!topByMax.contains("alpha") || !topByMax.contains("beta") || topByMax.contains("gamma")) {
            printErrorNum(10);
        }
        System.out.println("finished fileIndex Test!");
    }

    private static void printErrorNum(int num) {
        System.out.println("ERROR " + num);
    }
}
