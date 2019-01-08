import java.util.ArrayList;
import java.util.ConcurrentModificationException;
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
	private int changes = 0;

	/**
	 * Inefficient Iterator that puts the whole BST into an ArrayList before traversing
	 */
	private class ArrayListIterator implements Iterator<T> {
		private BinarySearchTree binarySearchTree;
		private ArrayList<T> array;
		private int index = 0;
		private int length;
		private int changes;

		// Store all the values in the tree in an ArrayList
		public ArrayListIterator(BinarySearchTree binarySearchTree) {
			this.binarySearchTree = binarySearchTree;
			this.array = binarySearchTree.toArrayList();
			this.length = this.array.size();
			this.changes = this.binarySearchTree.changes;
		}

		@Override
		public boolean hasNext() {
			return this.index < this.length;
		}

		@Override
		public T next() throws NoSuchElementException, ConcurrentModificationException {
			if (!this.hasNext()) throw new NoSuchElementException();
			if (this.changes != this.binarySearchTree.changes) throw new ConcurrentModificationException();
			return this.array.get(this.index++);
		}
	}

	/**
	 * Lazy Iterator that efficiently traverses the BST using PreOrder
	 */
	private class PreOrderIterator implements Iterator<T> {
		private Stack<BinaryNode> stack;
		private BinarySearchTree binarySearchTree;
		private T lastNext = null;
		private int changes;
		private boolean nextSinceRemove = false;

		public PreOrderIterator(BinaryNode node, BinarySearchTree binarySearchTree) {
			this.stack = new Stack<>();
			this.binarySearchTree = binarySearchTree;
			this.changes = this.binarySearchTree.changes;
			if (node != NULL_NODE) this.stack.push(node);
		}

		@Override
		public boolean hasNext() {
			return this.stack.size() != 0;
		}

		@Override
		public T next() throws NoSuchElementException, ConcurrentModificationException {
			if (!this.hasNext()) throw new NoSuchElementException();
			if (this.changes != this.binarySearchTree.changes) throw new ConcurrentModificationException();
			BinaryNode node = this.stack.pop();
			this.nextSinceRemove = true;
			if (node.right != NULL_NODE)
				this.stack.push(node.getRight());
			if (node.left != NULL_NODE)
				this.stack.push(node.getLeft());
			this.lastNext = node.data;
			return node.data;
		}

		@Override
		public void remove() throws NoSuchElementException, ConcurrentModificationException, IllegalStateException {
			if (!this.nextSinceRemove) throw new IllegalStateException();
			if (!this.hasNext()) throw new NoSuchElementException();
			if (this.changes != this.binarySearchTree.changes) throw new ConcurrentModificationException();
			this.nextSinceRemove = false;
			this.binarySearchTree.remove(this.lastNext);
		}
	}

	/**
	 * Lazy Iterator that efficiently traverses the BST using InOrder
	 */
	private class InOrderIterator implements Iterator<T> {
		private Stack<BinaryNode> stack;
		private BinarySearchTree binarySearchTree;
		private T lastNext = null;
		private int changes;
		private boolean nextSinceRemove = false;

		public InOrderIterator(BinaryNode node, BinarySearchTree binarySearchTree) {
			this.stack = new Stack<>();
			this.binarySearchTree = binarySearchTree;
			this.changes = this.binarySearchTree.changes;
			this.addLefts(node);
		}

		@Override
		public boolean hasNext() {
			return this.stack.size() != 0;
		}

		@Override
		public T next() throws NoSuchElementException, ConcurrentModificationException {
			if (this.changes != this.binarySearchTree.changes) throw new ConcurrentModificationException();
			if (!this.hasNext()) throw new NoSuchElementException();
			BinaryNode node = this.stack.pop();
			this.nextSinceRemove = true;
			this.addLefts(node.right);
			this.lastNext = node.data;
			return node.data;
		}

		@Override
		public void remove() throws NoSuchElementException, ConcurrentModificationException, IllegalStateException {
			if (!this.nextSinceRemove) throw new IllegalStateException();
			if (!this.hasNext()) throw new NoSuchElementException();
			if (this.changes != this.binarySearchTree.changes) throw new ConcurrentModificationException();
			this.nextSinceRemove = false;
			this.binarySearchTree.remove(this.lastNext);
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

		/**
		 * Insert a new BinaryNode into the correct sorted location
		 * @param node The new BinaryNode you are inserting
		 * @param result Boolean wrapper to keep track of the success
		 * @return The node that was inserted
		 */
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

		/**
		 * Search the BST for a specific item
		 * @param item The item you are looking for
		 * @param result boolean wrapper to keep track of the success
		 * @return The node if it was found
		 */
		public BinaryNode contains(T item, Result result) {
			if (this == NULL_NODE) return NULL_NODE;
			int direction = (int) Math.signum(item.compareTo(this.data));
			if (direction == 0) {
				result.success = true;
				return this;
			}else if (direction > 0)
				return this.right.contains(item, result);
			else return this.left.contains(item, result);
		}

		/**
		 * Remove an item from the BST and reorganize the tree
		 * @param item The new BinaryNode you are inserting
		 * @param result boolean wrapper to keep track of the success
		 * @param binarySearchTree Pointer to the BST
		 * @param parent The node's parent, used for recursion
		 * @param left If the last recursive operation moved left or right
		 * @return The node that was inserted
		 */
		public BinaryNode remove(T item, Result result, BinarySearchTree binarySearchTree, BinaryNode parent, boolean left) {
			if (this == NULL_NODE) return NULL_NODE;
			int direction = (int) Math.signum(item.compareTo(this.data));
			if (direction == 0) {
				result.success = true;
				BinaryNode predecessor;
				if (this.left != NULL_NODE && this.right != NULL_NODE)
					predecessor = this.left != NULL_NODE ? this.left.popFurthestRight(this.left) : this.right;
				else
					predecessor = this.left != NULL_NODE ? this.left : this.right;
				if (this.left == predecessor) this.left = NULL_NODE;
				if (parent == NULL_NODE && binarySearchTree != null) binarySearchTree.root = predecessor;
				else {
					if (left) parent.left = predecessor;
					else parent.right = predecessor;
				}
				if (this.left != NULL_NODE) predecessor.insert(this.left, new Result());
				if (this.right != NULL_NODE) predecessor.insert(this.right, new Result());
				return this;
			}else if (direction > 0)
				return this.right.remove(item, result, null, this, false);
			else return this.left.remove(item, result, null, this, true);
		}

		public BinaryNode remove(T item, Result result, BinarySearchTree binarySearchTree) {
			return this.remove(item, result, binarySearchTree, NULL_NODE, true);
		}

		/**
		 * Used to find and pop the furthest right child node of any other node
		 * @param parent The parent node for recursion
		 * @return The node furthest to the right
		 */
		private BinaryNode popFurthestRight(BinaryNode parent) {
			if (this == NULL_NODE) return NULL_NODE;
			if (this.right == NULL_NODE) {
				parent.right = NULL_NODE;
				return this;
			}
			return this.right.popFurthestRight(parent);
		}
	}

	/**
	 * Boolean wrapper class, used to keep track of success in methods
	 */
	private class Result {
		private boolean success = false;
	}

	// TODO: Implement your 3 iterator classes here, plus any other inner helper classes you'd like.
	public boolean isEmpty() {
		return this.root == NULL_NODE;
	}

	/**
	 * @return The number of nodes in the tree
	 */
	public int size() {
		return this.root.size();
	}

	/**
	 * @return How many layers there are in the tree
	 */
	public int height() {
		return this.root.height() - 1;
	}

	/**
	 * Insert a new BinaryNode into the correct sorted location
	 * @param item The new item you are inserting
	 * @return True if the item was successfully inserted
	 * @throws IllegalArgumentException
	 */
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

	/**
	 * Search the BST for a specific item
	 * @param item The item we are searching for
	 * @return True if the item was found in the tree
	 */
	public boolean contains(T item) {
		Result result = new Result();
		BinaryNode node = this.root.contains(item, result);
		return result.success;
	}

	/**
	 * Remove an item from the BST
	 * @param item The item you are trying to remove
	 * @return True if the item was successfully removed from the tree
	 * @throws IllegalArgumentException
	 */
	public boolean remove(T item) throws IllegalArgumentException {
		if (item == null) throw new IllegalArgumentException();
		Result result = new Result();
		BinaryNode node = this.root.remove(item, result, this);
		if (result.success) this.changes++;
		return result.success;
	}

	/**
	 * Inefficiently search for an item in the BT
	 * @param item The item you are searching for
	 * @return True if the item was found
	 */
	public boolean containsNonBST(T item) {
		for (Object node : this) {
			if (node == item) return true;
		}
		return false;
	}

	/**
	 * @return An iterator using an ArrayList
	 */
	public Iterator inefficientIterator() {
		return new ArrayListIterator(this);
	}

	/**
	 * @return An iterator using a preOrder traversal
	 */
	public Iterator preOrderIterator() {
		return new PreOrderIterator(this.root, this);
	}

	/**
	 * @return An iterator using an inOrder traversal
	 */
	public Iterator iterator() {
		return new InOrderIterator(this.root, this);
	}

	/**
	 * Convert the BST to an ArrayList
	 * @return The ArrayList
	 */
	public ArrayList<Object> toArrayList() {
		ArrayList<Object> arrayList = new ArrayList<>();
		for (Object item : this) {
			arrayList.add(item);
		}
		return arrayList;
	}

	/**
	 * Convert the BST to an Array
	 * @return The Array
	 */
	public Object[] toArray() {
		Object[] array = new Object[this.size()];
		int i = 0;
		for (Object item : this) {
			array[i] = item;
			i++;
		}
		return array;
	}

	/**
	 * Convert the BST to a string containing all the items
	 * @return The string containing all the items
	 */
	public String toString() {
		String output = "";
		for (Object item : this) {
			output += item + ", ";
		}
		return "[" + output.substring(0, Math.max(0, output.length() - 2)) + "]";
	}
}
