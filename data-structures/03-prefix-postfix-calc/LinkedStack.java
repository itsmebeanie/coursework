package Project03;

/**
 * Provides a linked list based implementation of stack interface.
 *
 * @author Kaitlin Gu
 * @creation November 8, 2014
 * 
 * @param <E>
 *            any valid reference type
 * 
 */
public class LinkedStack<E> implements Stack<E> {
	// size of stack
	private int size;
	// top of the stack
	private GenericNode<E> top;

	// Default constructor
	public LinkedStack() {
		top = null;
		size = 0;
	}

	/**
	 * Returns the number of items on the stack
	 * 
	 * @return number of items on stack
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 
	 * @return Returns boolean true, if stack is empty, false if it is not
	 */
	public boolean isEmpty() {
		return top == null;
	}

	@Override
	public void push(E item) {
		if (item != null) {
			if (top == null) {
				top = new GenericNode<E>(item, null);
			} else {
				GenericNode<E> tempTop = top;
				top = new GenericNode<E>(item);
				top.setNext(tempTop);
			}

			size++;
		}
	}

	@Override
	public E pop() {
		if (top == null) {
			return null;
		}
		E data = top.getData();
		top = top.getNext();
		size--;
		return data;
	}

	@Override
	public E peek() {
		if (top == null)
			return null;
		return top.getData();
	}

}
