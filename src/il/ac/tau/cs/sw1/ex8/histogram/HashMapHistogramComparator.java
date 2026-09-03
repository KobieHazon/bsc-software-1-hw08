package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Comparator;
import java.util.Map;

public class HashMapHistogramComparator<T extends Comparable<T>> implements Comparator<T> {
    private final Map<T, Integer> counts;

    public HashMapHistogramComparator(Map<T, Integer> counts) {
        this.counts = counts;
    }

    @Override
    public int compare(T left, T right) {
        int countComparison = Integer.compare(counts.get(right), counts.get(left));
        if (countComparison != 0) {
            return countComparison;
        }
        return left.compareTo(right);
    }
}
