package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Collection;
import java.util.Set;

public interface IHistogram<T> extends Iterable<T> {
    void addItem(T item);

    void removeItem(T item) throws IllegalItem;

    void addItemKTimes(T item, int k) throws IllegalKValue;

    void removeItemKTimes(T item, int k) throws IllegalKValue;

    int getCountForItem(T item);

    void addAll(Collection<T> items);

    void clear();

    Set<T> getItemsSet();
}
