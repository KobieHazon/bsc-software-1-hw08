package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class HashMapHistogramIterator<T extends Comparable<T>> implements Iterator<T> {
    private final List<T> items;
    private int index = 0;

    public HashMapHistogramIterator(List<T> items) {
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        return index < items.size();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return items.get(index++);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
