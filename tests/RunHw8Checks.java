import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.IHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.IllegalItem;
import il.ac.tau.cs.sw1.ex8.histogram.IllegalKValue;
import il.ac.tau.cs.sw1.ex8.wordsRank.FileIndex;
import il.ac.tau.cs.sw1.ex8.wordsRank.FileIndexException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class RunHw8Checks {
    public static void main(String[] args) throws Exception {
        testHistogram();
        testFileIndex();
        System.out.println("All HW8 checks passed");
    }

    private static void testHistogram() throws IllegalKValue, IllegalItem {
        IHistogram<String> histogram = new HashMapHistogram<>();
        histogram.addAll(Arrays.asList("gamma", "alpha", "gamma", "beta", "alpha", "alpha"));
        checkEquals(3, histogram.getCountForItem("alpha"), "alpha count");
        checkEquals(2, histogram.getCountForItem("gamma"), "gamma count");
        checkEquals(0, histogram.getCountForItem("missing"), "missing count");

        Iterator<String> iterator = histogram.iterator();
        List<String> ordered = new ArrayList<>();
        while (iterator.hasNext()) {
            ordered.add(iterator.next());
        }
        checkEquals(Arrays.asList("alpha", "gamma", "beta"), ordered, "histogram order");

        histogram.removeItemKTimes("alpha", 2);
        checkEquals(1, histogram.getCountForItem("alpha"), "remove two alpha items");
        histogram.removeItem("alpha");
        checkEquals(0, histogram.getCountForItem("alpha"), "remove last alpha item");
        check(!histogram.getItemsSet().contains("alpha"), "zero-count item removed from key set");

        boolean negativeKFailed = false;
        try {
            histogram.addItemKTimes("beta", -1);
        } catch (IllegalKValue expected) {
            negativeKFailed = true;
        }
        check(negativeKFailed, "negative k is rejected");

        boolean excessRemoveFailed = false;
        try {
            histogram.removeItemKTimes("beta", 2);
        } catch (IllegalKValue expected) {
            excessRemoveFailed = true;
        }
        check(excessRemoveFailed, "removing more than count is rejected");
    }

    private static void testFileIndex() throws FileIndexException {
        FileIndex index = new FileIndex();
        index.indexDirectory("resources/hw8/input");

        checkEquals(3, index.getCountInFile("doc1.txt", "ALPHA"), "case-insensitive count lookup");
        checkEquals(1, index.getRankForWordInFile("doc2.txt", "BETA"), "case-insensitive rank lookup");
        checkEquals(2, index.getRankForWordInFile("doc3.txt", "alpha"), "alpha rank in doc3");
        checkEquals(2, index.getAverageRankForWord("alpha"), "average alpha rank");
        checkEquals(0, index.getAverageRankForWord("missing"), "unranked word average");

        List<String> topByMin = index.getWordsBelowMinRank(1);
        check(topByMin.contains("alpha") && topByMin.contains("beta") && topByMin.contains("gamma"),
                "min-rank query includes each document leader");

        List<String> topByMax = index.getWordsAboveMaxRank(2);
        check(topByMax.contains("alpha") && topByMax.contains("beta") && !topByMax.contains("gamma"),
                "max-rank query is bounded by worst rank");

        boolean missingFileFailed = false;
        try {
            index.getRankForWordInFile("missing.txt", "alpha");
        } catch (FileIndexException expected) {
            missingFileFailed = true;
        }
        check(missingFileFailed, "missing files are rejected");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void checkEquals(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + ": expected " + expected + " but got " + actual);
        }
    }
}
