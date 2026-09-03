package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class HashMapHistogramTester {
    public static void main(String[] args) {
        List<Integer> values = Arrays.asList(1, 2, 1, 2, 3, 4, 3, 1);
        IHistogram<Integer> histogram = new HashMapHistogram<>();
        for (int value : values) {
            histogram.addItem(value);
        }
        if (histogram.getCountForItem(1) != 3) {
            printError(1);
        }
        if (histogram.getCountForItem(5) != 0) {
            printError(2);
        }
        Iterator<Integer> iterator = histogram.iterator();
        List<Integer> ordered = new ArrayList<>();
        while (iterator.hasNext()) {
            ordered.add(iterator.next());
        }
        if (ordered.get(0) != 1) {
            printError(3);
        }
        if (ordered.size() != 4) {
            printError(4);
        }

        IHistogram<String> stringHistogram = new HashMapHistogram<>();
        try {
            stringHistogram.addItemKTimes("bb", 5);
            stringHistogram.addItemKTimes("aa", 5);
        } catch (IllegalKValue e) {
            printError(5);
        }
        stringHistogram.addItem("abc");
        stringHistogram.addItem("de");
        stringHistogram.addItem("abc");
        stringHistogram.addItem("de");
        stringHistogram.addItem("abc");
        stringHistogram.addItem("de");
        stringHistogram.addItem("de");
        if (stringHistogram.getCountForItem("abc") != 3) {
            printError(6);
        }
        Iterator<String> stringIterator = stringHistogram.iterator();
        if (!stringIterator.next().equals("aa")) {
            printError(7);
        }
        if (!stringIterator.next().equals("bb")) {
            printError(8);
        }
        System.out.println("done!");
    }

    private static void printError(int number) {
        System.out.println("ERROR " + number);
    }
}
