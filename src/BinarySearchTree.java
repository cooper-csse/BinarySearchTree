import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * Implementation of most of the Set interface operations using a Binary Search Tree
 *
 * @author Matt Boutell and <<< YOUR NAME HERE >>>.
 * @param <T>
 */

public class BinarySearchTree<T> {
	// Most of you will prefer to use NULL NODES once you see how to use them.
	private final BinaryNode NULL_NODE = new BinaryNode();
	private BinaryNode root;

	// Most of you will prefer to use NULL NODES once you see how to use them.
	// private final BinaryNode NULL_NODE = new BinaryNode();

	public BinarySearchTree() {
		root = NULL_NODE;
	}

	// For manual tests only
	void setRoot(BinaryNode n) {
		this.root = n;
	}

	// Not private, since we need access for manual testing.
	class BinaryNode {
		private T data;
		private BinaryNode left;
		private BinaryNode right;

		public BinaryNode() {
			this.data = null;
			this.left = null;
			this.right = null;
		}

		public BinaryNode(T element) {
			this.data = element;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
		}

		public T getData() {
			return this.data;
		}

		public BinaryNode getLeft() {
			return this.left;
		}


		public BinaryNode getRight() {
			return this.right;
		}

		// For manual testing
		public void setLeft(BinaryNode left) {
			this.left = left;
		}
		
		public void setRight(BinaryNode right) {
			this.right = right;
		}
		
	}

	// TODO: Implement your 3 iterator classes here, plus any other inner helper classes you'd like.
	public boolean isEmpty() {
		return false;
	}

	public int size() {
		return 0;
	}

	public int height() {
		return 0;
	}

	public boolean insert(T item) {
		return false;
	}

	public boolean contains(T item) {
		return false;
	}

	public boolean remove(T item) {
		return false;
	}

	public boolean containsNonBST(T item) {
		return false;
	}

	public Iterator inefficientIterator() {
		return null;
	}

	public Iterator preOrderIterator() {
		return null;
	}

	public Iterator iterator() {
		return null;
	}

	public ArrayList<Integer> toArrayList() {
		return null;
	}

	public T[] toArray() {
		return null;
	}

}
