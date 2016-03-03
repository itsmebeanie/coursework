package Project04;

/**
 * 
 * AVLNode class is used to represent nodes in an AVL Tree. It contains a data
 * item that implements the Comparable interface as well as references to left
 * and right subtree
 * 
 * @author Kaitlin Gu
 * @creation November 29, 2014
 * @param <E>
 *            a reference type that implements Comparable <E> interface
 */
public class AVLNode<E extends Comparable<E>> implements Comparable<AVLNode<E>> {
	// data item stored in the node
	private E data;
	// reference to the left subtree
	private AVLNode<E> left;
	// reference to right subtree
	private AVLNode<E> right;
	// height of the node
	private int height;

	/**
	 * Constructs an AVL Node initializing the data part according to the
	 * parameter and setting both references to subtrees to null. Height is set
	 * to 0
	 * 
	 * @param data
	 *            data to be stored in node
	 */
	public AVLNode(E data) {
		this(data, null, null);
	}

	/**
	 * Constructs an AVL Node initializing the data part and subtree references
	 * according to the parameters. Height is set based on left and right
	 * subtree references
	 * 
	 * @param data
	 *            data to be stored in the node
	 * @param left
	 *            reference to left subtree
	 * @param right
	 *            reference to right subtree
	 */
	public AVLNode(E data, AVLNode<E> left, AVLNode<E> right) {
		this.data = data;
		this.left = left;
		this.right = right;
		if (left == null && right == null)
			height = 0;
		else if (left == null)
			height = right.getHeight() + 1;
		else if (right == null)
			height = left.getHeight() + 1;
		else
			height = 1 + Math.max(left.getHeight(), right.getHeight());
	}

	/**
	 * Returns a reference to the data stored in node
	 * 
	 * @return reference to data stored in node
	 */
	public E getData() {
		return data;
	}

	/**
	 * Changes the data stored in node to the one specified in parameter
	 * 
	 * @param data
	 *            reterence to new data of node
	 */
	public void setData(E data) {
		this.data = data;
	}

	/**
	 * Left subtree accessor
	 * 
	 * @return reference to the left subtree of a node
	 */
	public AVLNode<E> getLeft() {
		return left;
	}

	/**
	 * Changes the reference to the left subtree to the one specified in the
	 * parameter.
	 * 
	 * @param reference
	 *            to the new left subtree of the node.
	 */

	public void setLeft(AVLNode<E> left) {
		this.left = left;
	}

	/**
	 * Right subtree accessor
	 * 
	 * @return reference to the right subtree of a node
	 */
	public AVLNode<E> getRight() {
		return right;
	}

	/**
	 * Changes the reference to the right subtree to the one specified in the
	 * parameter.
	 * 
	 * @param reference
	 *            to the new right subtree of the node.
	 */

	public void setRight(AVLNode<E> right) {
		this.right = right;
	}

	/**
	 * Height accessor
	 * 
	 * @return height of the node
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Changes the height to the node to the one specified in the parameter.
	 * 
	 * @param new height of node
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return data.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AVLNode<E> other) {
		return this.data.compareTo(other.data);
	}

}
