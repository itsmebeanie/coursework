package Project03;

/**
 * Defines a generic linked list node storing a data item of type E and a single
 * reference to next.
 * 
 * @author Joanna Klukowska
 * @version Mar 2, 2014
 *
 * @param <E>
 *            any valid reference type
 */

public class GenericNode<E> {
	// reference to the next node
	private GenericNode<E> next;
	// data item stored in the node
	private E data;

	/**
	 * Default constructor creates an empty node.
	 */
	public GenericNode() {
		data = null;
		next = null;
	}

	/**
	 * Creates a node with specified data item.
	 * 
	 * @param data
	 *            data item to store in the node
	 */
	public GenericNode(E data) {
		if (data != null)
			this.data = data;
	}

	/**
	 * Creates a node with specified data and reference to next.
	 * 
	 * @param data
	 *            data item to store in the node
	 * @param next
	 */
	public GenericNode(E data, GenericNode<E> next) {
		if (data != null)
			this.data = data;
		if (next != null)
			this.next = next;
	}

	/**
	 * @return the next
	 */
	public GenericNode<E> getNext() {
		return next;
	}

	/**
	 * @param next
	 *            the next to set
	 */
	public void setNext(GenericNode<E> next) {
		this.next = next;
	}

	/**
	 * @return the data
	 */
	public E getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(E data) {
		this.data = data;
	}

}
