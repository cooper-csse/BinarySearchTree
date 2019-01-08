import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * 
 * Implementation of most of the Set interface operations using a Binary Search Tree
 *
 * @author Matt Boutell and <<< YOUR NAME HERE >>>.
 * @param <T>
 */

public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {
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

	private class PreOrderIterator implements Iterator<T> {
		private Stack<BinaryNode> stack;

		public PreOrderIterator(BinaryNode node) {
			this.stack = new Stack<>();
			if (node != NULL_NODE) this.stack.push(node);
		}

		@Override
		public boolean hasNext() {
			return this.stack.size() != 0;
		}

		@Override
		public T next() throws NoSuchElementException {
			if (!this.hasNext()) throw new NoSuchElementException();
			BinaryNode node = this.stack.pop();
			if (node.right != NULL_NODE)
				this.stack.push(node.getRight());
			if (node.left != NULL_NODE)
				this.stack.push(node.getLeft());
			return node.data;
		}
	}

	private class InOrderIterator implements Iterator<T> {
		private Stack<BinaryNode> stack;

		public InOrderIterator(BinaryNode node) {
			this.stack = new Stack<>();
			this.addLefts(node);
		}

		@Override
		public boolean hasNext() {
			return this.stack.size() != 0;
		}

		@Override
		public T next() throws NoSuchElementException {
			if (!this.hasNext()) throw new NoSuchElementException();
			BinaryNode node = this.stack.pop();
			this.addLefts(node.right);
			return node.data;
		}

		private void addLefts(BinaryNode node) {
			BinaryNode n = node;
			while (n != NULL_NODE) {
				this.stack.push(n);
				n = n.left;
			}
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
		
		public BinaryNode insert(BinaryNode node, Result result) {
			int direction = (int) Math.signum(node.data.compareTo(this.data));
			if (direction == 0)
				return NULL_NODE;
			else if (direction > 0) {
				if (this.right == NULL_NODE) {
					this.right = node;
					result.success = true;
					return this.right;
				}
				return this.right.insert(node, result);
			} else {
				if (this.left == NULL_NODE) {
					this.left = node;
					result.success = true;
					return this.left;
				}
				return this.left.insert(node, result);
			}
		}

		public BinaryNode insert(T item, Result result) {
			return this.insert(new BinaryNode(item), result);
		}
	private class Result {
		private boolean success = false;
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

	/**
	 * Insert a new BinaryNode into the correct sorted location
	public boolean insert(T item) throws IllegalArgumentException {
		if (item == null) throw new IllegalArgumentException();
		if (this.root == NULL_NODE) {
			this.root = new BinaryNode(item);
			return true;
		}
		Result result = new Result();
		BinaryNode node = this.root.insert(item, result);
		if (result.success) this.changes++;
		return result.success;
	}

	public boolean contains(T item) {
		return false;
	}

	public boolean remove(T item) {
		return false;
	}

	public boolean containsNonBST(T item) {
		for (Object node : this) {
			if (node == item) return true;
		}
		return false;
	}

	public Iterator inefficientIterator() {
		return new ArrayListIterator(this);
	}

	public Iterator preOrderIterator() {
		return new PreOrderIterator(this.root);
	}

	public Iterator iterator() {
		return new InOrderIterator(this.root);
	}

	public ArrayList<Object> toArrayList() {
		ArrayList<Object> arrayList = new ArrayList<>();
		for (Object item : this) {
			arrayList.add(item);
		}
		return arrayList;
	}

	public Object[] toArray() {
		Object[] array = new Object[this.size()];
		int i = 0;
		for (Object item : this) {
			array[i] = item;
			i++;
		}
		return array;
	}

	public String toString() {
		String output = "";
		for (Object item : this) {
			output += item + ", ";
		}
		return "[" + output.substring(0, Math.max(0, output.length() - 2)) + "]";
	}
}
