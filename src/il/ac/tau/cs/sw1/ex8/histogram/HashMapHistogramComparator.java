package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Comparator;

public class HashMapHistogramComparator<T extends Comparable<T>> implements Comparator<T> {

	private java.util.Map<T, Integer> hashMap;
	
	public HashMapHistogramComparator(java.util.Map<T, Integer> hashMap) {
		this.hashMap = hashMap;
	}
	
	@Override
	public int compare(T o1, T o2) {
		int val1 = hashMap.get(o1);
		int val2 = hashMap.get(o2);
		if (val1 > val2) {
			return -1;
		}
		if (val2 > val1) {
			return 1;
		}
		return o1.compareTo(o2);
	}

}