package Project04;

/**
 * StringAVL class that represents a string based AVLTree. It contains a finds
 * prefix method that allows you to search for whether a given prefix is in the
 * AVL tree
 * 
 * @author Kaitlin Gu
 * @creation December 1, 2014
 */
public class StringAVL extends AVLTree<String> {

	/**
	 * Method to search for whether this StringAVL tree contains a prefix
	 * 
	 * @param prefix
	 *            prefix to search for
	 * @return true if found, false if not
	 */
	public boolean findPrefix(String prefix) {
		return findPrefix(prefix, root);

	}

	/*
	 * Recursively search tree for prefix
	 * 
	 * @param prefix prefix to search for
	 * 
	 * @param t current root
	 * 
	 * @return true if found, false if not
	 */
	private boolean findPrefix(String prefix, AVLNode<String> t) {
		// if root is null return false
		if (t == null)
			return false;
		if (t.getData().startsWith(prefix))
			// prefix is found
			return true;
		else if (prefix.compareTo(t.getData()) < 0)
			return findPrefix(prefix, t.getLeft());
		else if (prefix.compareTo(t.getData()) > 0)
			return findPrefix(prefix, t.getRight());
		// prefix is not found
		return false;
	}

}