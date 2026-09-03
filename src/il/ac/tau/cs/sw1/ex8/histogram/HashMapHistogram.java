package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T> {
    private final Map<T, Integer> counts = new HashMap<>();

    @Override
    public void addItem(T item) {
        Objects.requireNonNull(item, "item");
        counts.put(item, getCountForItem(item) + 1);
    }

    @Override
    public void addItemKTimes(T item, int k) throws IllegalKValue {
        Objects.requireNonNull(item, "item");
        validateK(k);
        if (k == 0) {
            return;
        }
        counts.put(item, getCountForItem(item) + k);
    }

    @Override
    public int getCountForItem(T item) {
        return counts.getOrDefault(item, 0);
    }

    @Override
    public void addAll(Collection<T> items) {
        for (T item : items) {
            addItem(item);
        }
    }

    @Override
    public void clear() {
        counts.clear();
    }

    @Override
    public Set<T> getItemsSet() {
        return new HashSet<>(counts.keySet());
    }

    @Override
    public Iterator<T> iterator() {
        List<T> keys = new ArrayList<>(counts.keySet());
        keys.sort(new HashMapHistogramComparator<>(counts));
        return new HashMapHistogramIterator<>(keys);
    }

    @Override
    public void removeItem(T item) throws IllegalItem {
        int oldCount = getCountForItem(item);
        if (oldCount == 0) {
            throw new IllegalItem();
        }
        if (oldCount == 1) {
            counts.remove(item);
        } else {
            counts.put(item, oldCount - 1);
        }
    }

    @Override
    public void removeItemKTimes(T item, int k) throws IllegalKValue {
        validateK(k);
        int oldCount = getCountForItem(item);
        if (k > oldCount) {
            throw new IllegalKValue(k);
        }
        if (k == 0) {
            return;
        }
        if (k == oldCount) {
            counts.remove(item);
        } else {
            counts.put(item, oldCount - k);
        }
    }

    private void validateK(int k) throws IllegalKValue {
        if (k < 0) {
            throw new IllegalKValue(k);
        }
    }
}
