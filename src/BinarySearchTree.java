import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * Implementation of most of the Set interface operations using a Binary Search Tree
 *
 * @author Matt Boutell and <<< YOUR NAME HERE >>>.
 * @param <T>
 */

public class BinarySearchTree<T> implements Iterable<T> {
	// Most of you will prefer to use NULL NODES once you see how to use them.
	private final BinaryNode NULL_NODE = new BinaryNode();
	private BinaryNode root;

	private class ArrayListIterator implements Iterator<T> {
		private ArrayList<T> array;
		private int index = 0;
		private int length;
		// Store all the values in the tree in an ArrayList
		public ArrayListIterator(BinarySearchTree binarySearchTree) {
			this.array = binarySearchTree.toArrayList();
			this.length = this.array.size();
		}

		@Override
		public boolean hasNext() {
			return this.index < this.length;
		}

		@Override
		public T next() throws NoSuchElementException {
			if (!this.hasNext()) throw new NoSuchElementException();
			return this.array.get(this.index++);
		}
	}
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

		public int size() {
			return this == NULL_NODE ? 0 : 1 + this.left.size() + this.right.size();
		}

		public int height() {
			return this == NULL_NODE ? 0 : 1 + Math.max(this.left.height(), this.right.height());
		}
		
	// TODO: Implement your 3 iterator classes here, plus any other inner helper classes you'd like.
	public boolean isEmpty() {
		return this.root == NULL_NODE;
	}

	public int size() {
		return this.root.size();
	}

	public int height() {
		return this.root.height() - 1;
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
		return new ArrayListIterator(this);
	}

	public Iterator preOrderIterator() {
		return null;
	}

	public Iterator iterator() {
		return null;
	}

	public ArrayList<Object> toArrayList() {
		ArrayList<Object> arrayList = new ArrayList<>();
		for (Object item : this) {
			arrayList.add(item);
		}
		return arrayList;
	}

	public T[] toArray() {
		return null;
	}

}
