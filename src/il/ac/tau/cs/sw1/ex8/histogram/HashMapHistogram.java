package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	private java.util.Map<T, Integer> hashMap = new java.util.HashMap<T, Integer>();

	@Override
	public void addItem(T item) {
		if (hashMap.containsKey(item)) {
			hashMap.put(item, hashMap.get(item)+1);
		}
		else {
			hashMap.put(item, 1);
		}
	}

	@Override
	public void addItemKTimes(T item, int k) throws IllegalKValue {
		if (k < 0) {
			throw new IllegalKValue(k);
		}
		
		if (hashMap.containsKey(item)) {
			hashMap.put(item, hashMap.get(item)+k);
		}
		else {
			hashMap.put(item, k);
		}
	}

	@Override
	public int getCountForItem(T item) {
		return hashMap.containsKey(item) ? hashMap.get(item) : 0;
	}

	@Override
	public void addAll(Collection<T> items) {
		for (T item: items) {
			addItem(item);
		}
	}

	@Override
	public void clear() {
		hashMap.clear();
	}

	@Override
	public Set<T> getItemsSet() {
		return hashMap.keySet();
	}

	@Override
	public Iterator<T> iterator() {
		java.util.List<T> keys = new java.util.ArrayList<T>(hashMap.keySet());
		java.util.Collections.sort(keys, new HashMapHistogramComparator<T>(hashMap));
		return new HashMapHistogramIterator<T>(keys);
	}

	@Override
	public void removeItem(T item) throws IllegalItem{
		int oldVal = getCountForItem(item);
		if (oldVal == 0) {
			throw new IllegalItem();
		}
		else if (oldVal == 1) {
			hashMap.remove(item);
		}
		else {
			hashMap.put(item, oldVal-1);
		}
	}

	@Override
	public void removeItemKTimes(T item, int k) throws IllegalKValue {
		int oldVal = getCountForItem(item);
		if (k > oldVal) {
			throw new IllegalKValue(k);
		}
		else if (k == oldVal && oldVal != 0) {
			hashMap.remove(item);
		}
		else {
			hashMap.put(item, hashMap.get(item) - k);
		}
	}
}
