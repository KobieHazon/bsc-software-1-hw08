package il.ac.tau.cs.sw1.ex8.histogram;

public class IllegalItem extends Exception {

	private static final long serialVersionUID = 1L;

	public IllegalItem(){
		super("The item you are trying to remove is illegal");
	}
}