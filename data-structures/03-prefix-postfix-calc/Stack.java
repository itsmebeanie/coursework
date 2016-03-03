package Project03;

/**
 * Stack interface
 * 
 * @author kaitlingu
 *
 * @param <E>
 *            any valid reference type
 */

public interface Stack<E> {
	/**
	 * Add an object to the top of the stack
	 * 
	 * @param item
	 *            character to be added to the stack
	 */
	public void push(E item);

	/**
	 * Remove and return an object from the top of the stack
	 * 
	 * @return an object from the top of the stack and remove from the stack If
	 *         stack is empty, null is returned
	 */
	public E pop();

	/*
	 * Return an object from the top of the stack
	 * 
	 * @return an object from the top of the stack. If stack is empty null is
	 * returned
	 */
	public E peek();

	/**
	 * Produces a string representation of the stack
	 * 
	 * @return returns a String object hat contains all elements stored on the
	 *         stack.
	 */
	public String toString();

}