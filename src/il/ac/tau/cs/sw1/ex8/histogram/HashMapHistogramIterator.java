package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Iterator;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogramIterator<T extends Comparable<T>> implements Iterator<T>{
	
	private java.util.List<T> items;
	private int cnt;
	
	public HashMapHistogramIterator(java.util.List<T> items) {
		this.items = items;
	}
	
	@Override
	public boolean hasNext() {
		return cnt != items.size();
	}

	@Override
	public T next() {
		return items.get(cnt++);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(); //no need to change this
	}
}
