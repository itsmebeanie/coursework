package Project04;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * AVL Tree provides a generic representation of an AVL Tree. It provides method
 * for inserting and removing data from the tree and searching for data in the
 * tree.
 * 
 * @author Kaitlin Gu
 * @creation December 2, 2014
 *
 * @param <E>
 *            a reference type that implements Comparable <E> interface
 */
public class AVLTree<E extends Comparable<E>> implements Iterable<E> {
	// reference to the root of the AVL Tree
	protected AVLNode<E> root;
	// number of elements
	private int numberOfElements;

	/**
	 * Creates an empty AVL Tree.
	 */
	public AVLTree() {
		root = null;
		numberOfElements = 0;
	}

	/**
	 * Method that returns the balance factor i.e. the differences in the
	 * heights of the left and right subtrees of a given node n
	 * 
	 * @param n
	 *            node of a given tree whose balance factor will be evaluated
	 * @return height difference of the subtrees
	 */
	private int balanceFactor(AVLNode<E> n) {
		if (n.getRight() == null) {
			return -n.getHeight();
		}
		if (n.getLeft() == null) {
			return n.getHeight();
		}

		// balance factor is determined by subtracting left
		// subtree's height from right's subtree height
		return n.getRight().getHeight() - n.getLeft().getHeight();

	}

	/**
	 * Method that updates the height of a given node
	 * 
	 * @param t
	 *            node whose height will be updated
	 */
	private void updateHeight(AVLNode<E> t) {
		// if node is a leaf then its height is zero
		if (t.getLeft() == null && t.getRight() == null)
			t.setHeight(0);
		// if the left subtree is null then right subtree is taller
		else if (t.getLeft() == null)
			t.setHeight(t.getRight().getHeight() + 1);
		// if right subtree is null then left subtree is taller
		else if (t.getRight() == null)
			t.setHeight(t.getLeft().getHeight() + 1);
		// neither is taller
		else
			t.setHeight(1 + Math.max(t.getLeft().getHeight(), t.getRight()
					.getHeight()));
	}

	/**
	 * Method that keeps the tree balanced at node t based on four possible
	 * rotations for rebalancing
	 * 
	 * @param t
	 *            root of subtree to balance
	 * @return new root of balanced subtree
	 */
	private AVLNode<E> balance(AVLNode<E> t) {
		// node is imbalanced, left subtree is taller by 2 levels
		if (balanceFactor(t) == -2) {
			AVLNode<E> B = compareHeight(t.getLeft(), t.getRight());
			// determine what rotations to use
			if (balanceFactor(B) == -1 || balanceFactor(B) == 0) {
				t = LLimbalance(t);
			} else if (balanceFactor(B) == 1) {
				t = LRimbalance(t);
			}
		}
		// node is imbalanced, right subtree is taller by 2 levels
		else if (balanceFactor(t) == 2) {
			AVLNode<E> B = compareHeight(t.getLeft(), t.getRight());
			// determine what rotations to use
			if (balanceFactor(B) == 1 || balanceFactor(B) == 0) {
				t = RRimbalance(t);
			} else if (balanceFactor(B) == -1) {
				t = RLimbalance(t);
			}
		}
		return t;

	}

	/*
	 * Method that compares the heights of two AVL nodes and returns the one
	 * with greater height
	 * 
	 * @param L AVL node to be compared
	 * 
	 * @param R AVL node to be compared
	 * 
	 * @return the taller of the two nodes
	 */
	private AVLNode<E> compareHeight(AVLNode<E> L, AVLNode<E> R) {
		if (L == null)
			return R;
		else if (R == null)
			return L;
		if (L.getHeight() >= R.getHeight()) {
			return L;
		} else {
			return R;
		}
	}

	/*
	 * Method that rebalances the tree based on a left left imbalance at node A
	 * 
	 * @param A node where the imbalances occur
	 * 
	 * @return reference to new root of balanced subtree
	 */
	private AVLNode<E> LLimbalance(AVLNode<E> A) {
		AVLNode<E> B = A.getLeft();
		A.setLeft(B.getRight());
		B.setRight(A);
		// update heights
		updateHeight(A);
		updateHeight(B);
		// new root of subtree
		return B;

	}

	/*
	 * Method that rebalances the tree based on a right right imbalance at node
	 * A
	 * 
	 * @param A node where the imbalances occur
	 * 
	 * @return reference to new root of balanced subtree
	 */
	private AVLNode<E> RRimbalance(AVLNode<E> A) {
		AVLNode<E> B = A.getRight();
		A.setRight(B.getLeft());
		B.setLeft(A);
		// update heights
		updateHeight(A);
		updateHeight(B);
		return B;
	}

	/*
	 * Method that rebalances the tree based on a left right imbalance at node A
	 * 
	 * @param A node where the imbalances occur
	 * 
	 * @return reference to new root of balanced subtree
	 */

	private AVLNode<E> LRimbalance(AVLNode<E> A) {
		AVLNode<E> B = A.getLeft();
		AVLNode<E> C = B.getRight();
		A.setLeft(C.getRight());
		B.setRight(C.getLeft());
		C.setLeft(B);
		C.setRight(A);
		// update heights
		updateHeight(A);
		updateHeight(B);
		updateHeight(C);
		// new root of subtree
		return C;
	}

	/*
	 * Method that rebalances the tree based on a right left imbalance at node A
	 * 
	 * @param A node where the imbalances occur
	 * 
	 * @return reference to new root of balanced subtree
	 */
	private AVLNode<E> RLimbalance(AVLNode<E> A) {
		AVLNode<E> B = A.getRight();
		AVLNode<E> C = B.getLeft();
		A.setRight(C.getLeft());
		B.setLeft(C.getRight());
		C.setRight(B);
		C.setLeft(A);
		// update heights
		updateHeight(A);
		updateHeight(B);
		updateHeight(C);
		// new root of subtree
		return C;
	}

	/**
	 * Method that adds an item to this AVL Tree
	 * 
	 * @param data
	 *            data to be added to the be tree unless it is null
	 */
	public void insert(E data) {
		if (data != null)
			root = insert(data, root);

		numberOfElements++;

	}

	/*
	 * Recursive method for adding an item to this AVL tree.
	 * 
	 * @param data data to be added
	 * 
	 * @param t root of tree in which the node will be added
	 * 
	 * @return new root of tree containing the data added
	 */
	private AVLNode<E> insert(E data, AVLNode<E> t) {
		if (t == null)
			t = new AVLNode<E>(data, null, null);

		else if (data.compareTo(t.getData()) < 0) {

			t.setLeft(insert(data, t.getLeft()));

		} else {
			t.setRight(insert(data, t.getRight()));
		}

		// rebalance tree
		updateHeight(t);
		t = balance(t);
		// new root
		return t;

	}

	/**
	 * Method that removes removes data from this AVL. If the item is null or is
	 * not found, the tree does not change
	 * 
	 * @param data
	 *            to be removed
	 * 
	 */
	public void remove(E data) {
		if (data != null) {
			root = remove(data, root);
			numberOfElements--;
		}

	}

	/*
	 * Recursively removes item from AVL tree
	 * 
	 * @param data data to be removed
	 * 
	 * @param t current root of tree in which item will be removed
	 * 
	 * @return new reference to AVL tree after item is removed
	 */
	private AVLNode<E> remove(E data, AVLNode<E> t) {

		if (t == null)
			// do nothing if root is null
			;
		if (data.compareTo(t.getData()) < 0) {
			t.setLeft(remove(data, t.getLeft()));
			// rebalance
			updateHeight(t);
			return balance(t);

		}

		else if (data.compareTo(t.getData()) > 0) {
			t.setRight(remove(data, t.getRight()));
			// rebalance
			updateHeight(t);
			return balance(t);
		}

		// the data is found
		else {
			// no children
			if (t.getLeft() == null && t.getRight() == null)
				return null;
			// 1 child so balancing is guaranteed
			if (t.getLeft() == null) {
				return t.getRight();
			}
			if (t.getRight() == null) {
				return t.getLeft();
			}
			// 2 children may leave tree unbalanced
			else {
				// find the max value of the left subtree
				E maxValue = getMax(t.getLeft());
				// replace the root with max value
				t.setData(maxValue);
				t.setLeft(remove(maxValue, t.getLeft()));

				// rebalance
				updateHeight(t);
				return balance(t);

			}
		}

	}

	/*
	 * Obtains the predecessor of a node (according to AVL ordering).
	 * 
	 * @param tree node whose predecessor we are after
	 * 
	 * @return the data contained in the predecessor node
	 */
	private E getMax(AVLNode<E> tree) {
		while (tree.getRight() != null)
			tree = tree.getRight();
		return tree.getData();
	}

	/**
	 * Method that searches for a data stored in a node of this AVL tree
	 * 
	 * @param data
	 *            data used for search
	 * @return true if data is found, false if not
	 */
	public boolean contains(E data) {
		if (data != null) {
			return contains(data, root);
		}
		// false if the data is null
		return false;
	}

	/*
	 * Recursively searching for data in tree
	 * 
	 * @param data Item to search for
	 * 
	 * @param t current root of tree
	 * 
	 * @return true if data is found, otherwise false
	 */
	private boolean contains(E data, AVLNode<E> t) {

		// if root is null then data is not found
		if (t == null) {
			return false;
		} else if (data.compareTo(t.getData()) < 0) {
			return contains(data, t.getLeft());
		} else if (data.compareTo(t.getData()) > 0) {
			return contains(data, t.getRight());
		}
		// data is found
		return true;

	}

	/*
	 * Method that returns an ArrayList containing an inorder traversal of this
	 * tree (Used for the iterator)
	 * 
	 * @return ArrayList containing inorder traversal
	 */

	private ArrayList<E> inOrderTraversal() {
		ArrayList<E> inOrder = new ArrayList<E>();
		inOrderTraversal(root, inOrder);
		return inOrder;
	}

	/*
	 * Recursive implementation for an inorder traversal of this tree
	 * 
	 * @param t Tree node to traverse
	 * 
	 * @param inOrder ArrayList to append to
	 */
	private void inOrderTraversal(AVLNode<E> t, ArrayList<E> inOrder) {
		if (t != null) {

			inOrderTraversal(t.getLeft(), inOrder);
			inOrder.add(t.getData());
			inOrderTraversal(t.getRight(), inOrder);
		}
	}

	/**
	 * Method that gets the number of elements in this AVL tree
	 * 
	 * @return the number of elements in this AVL tree
	 */

	public int getSize() {
		return numberOfElements;
	}

	/*
	 * Method that generates a string representation of this AVL
	 * 
	 * @author Joanna Klukowska
	 * 
	 * @see lecture06_trees source code
	 * 
	 * @param tree the root of the current subtree
	 * 
	 * @param level level (depth) of the current recursive call in the tree to
	 * determine the indentation of each item
	 * 
	 * @param output the string that accumulated the string representation of
	 * this AVL
	 */
	private void postOrderPrint(AVLNode<E> tree, int level, StringBuilder output) {
		if (tree != null) {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append(tree.getData());
			postOrderPrint(tree.getLeft(), level + 1, output);
			postOrderPrint(tree.getRight(), level + 1, output);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		postOrderPrint(root, 0, s);
		return s.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 * 
	 * Method that returns iterator for this AVL tree
	 */
	@Override
	public Iterator<E> iterator() {
		return (new AVLIterator());
	}

	/*
	 * AVL Iterator class to be used with an array list containing the inorder
	 * traversal of this AVL tree
	 */
	private class AVLIterator implements Iterator<E> {
		// array list containing in order traversal
		private ArrayList<E> inOrder;
		private int currentIndex;
		// flag for next method
		private int endIndex;

		/*
		 * Default constructor that sets the current index to 0and the end index
		 * to the last element of the array
		 */
		public AVLIterator() {
			currentIndex = 0;
			inOrder = inOrderTraversal();
			endIndex = inOrder.size() - 1;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			// if the current index reaches the last index hasNext is false
			return (currentIndex <= endIndex);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
		@Override
		public E next() {
			if (hasNext() == false) {
				throw new NoSuchElementException();
			}
			// get the next index in the array
			return inOrder.get(currentIndex++);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			// Unimplemented
			throw new UnsupportedOperationException();

		}

	}
}
