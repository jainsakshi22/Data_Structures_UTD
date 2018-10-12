/** Sakshi Jain, Arunachalam Saravanan
 *  Implementation of Binary Search Tree
 **/

package sxj180002;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
	static class Entry<E> {
		E element;
		Entry<E> left, right;

		public Entry(E x, Entry<E> left, Entry<E> right) {
			this.element = x;
			this.left = left;
			this.right = right;
		}
	}

	Entry<T> root;
	int size;
	Comparator<T> comp;
	Stack<Entry<T>> parentNodes; // Store parent nodes using a stack

	/**
	 * Default Constructor
	 */
	public BinarySearchTree() {
		root = null;
		size = 0;
		parentNodes = new Stack<>();
		comp = (T a, T b) -> a.compareTo(b);
	}

	/**
	 * Returns size of the tree
	 * 
	 * @return size
	 */
	private int size() {
		return size;
	}

	/**
	 * Returns the root element
	 * 
	 * @return root
	 */
	private Entry<T> root() {
		return root;
	}

	/**
	 * Return the parent nodes in the tree
	 * 
	 * @return parentNodes
	 */
	private Stack<Entry<T>> parentNodes() {
		return parentNodes;
	}

	/**
	 * Check if the tree contains the input element
	 * 
	 * @param x
	 * @return true if element is present
	 */
	public boolean contains(T x) {
		Entry<T> t = find(x);
		if (t == null || t.element != x)
			return false;
		return true;
	}

	/**
	 * Helper method to find if given element is present
	 * 
	 * @param x
	 * @return entry if present, null otherwise
	 */
	private Entry<T> find(T x) {
		parentNodes.push(null);
		return find(root(), x);
	}

	/**
	 * Helper method to find if given element is present
	 * 
	 * @param t
	 * @param x
	 * @return entry if present, null otherwise
	 */
	private Entry<T> find(Entry<T> t, T x) {
		if (t == null || comp.compare(t.element, x) == 0) {
			return t;
		}
		while (true) {
			int comparable = comp.compare(t.element, x);
			if (comparable > 0) {
				if (t.left == null)
					break;
				else {
					parentNodes().push(t); // Keep adding parent nodes to the stack
					t = t.left;
				}
			} else if (comparable == 0)
				break;
			else {
				if (t.right == null)
					break;
				else {
					parentNodes().push(t);
					t = t.right;
				}
			}
		}
		return t;
	}

	/**
	 * Check if there exists an element that is equal to x in the tree? .
	 * 
	 * @param x
	 * @return Element in tree that is equal to x is returned, null otherwise
	 */
	public T get(T x) {
		Entry<T> t = find(x);
		if (t == null || comp.compare(t.element, x) != 0)
			return null;
		return t.element;
	}

	/**
	 * Add x to tree. If tree contains a node with same key, replace element by x.
	 * 
	 * @param x
	 * @return true if x is a new element added to tree.
	 */
	public boolean add(T x) {
		if (size() == 0) {
			root = new Entry<T>(x, null, null);
			size++;
			return true;
		} else {
			Entry<T> t = find(x);
			int comparable = comp.compare(t.element, x);
			if (comparable == 0) {
				t.element = x; // Replace the element
				return false;
			} else if (comparable > 0) {
				t.left = new Entry<T>(x, null, null);
			} else {
				t.right = new Entry<T>(x, null, null);
			}
			size++;
			return true;
		}
	}

	/**
	 * Remove x from the tree
	 * 
	 * @param x
	 * @return x if found, otherwise return null
	 */
	public T remove(T x) {
		if (root() == null)
			return null;
		Entry<T> t = find(x);
		if (comp.compare(t.element, x) != 0)
			return null;
		T result = t.element;
		if (t.left == null || t.right == null) {
			bypass(t);
		} else { // This node has 2 children
			parentNodes().push(t);
			Entry<T> minRight = find(t.right, x);
			t.element = minRight.element;
			bypass(minRight);
		}
		size--;
		return result;
	}

	/**
	 * Helper method to rearrange the tree during removal of an element
	 * 
	 * @param t
	 */
	private void bypass(Entry<T> t) { // Called when t has atmost one child
		Entry<T> parent = parentNodes().peek();
		Entry<T> child = t.left == null ? t.right : t.left;
		if (parent == null) {
			root = child;
		} else {
			if (parent.left == t)
				parent.left = child;
			else
				parent.right = child;
		}
	}

	public static void main(String[] args) {
		BinarySearchTree<Integer> t = new BinarySearchTree<>();
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int x = in.nextInt();
			if (x > 0) {
				System.out.print("Add " + x + " : ");
				t.add(x);
				t.printTree();
			} else if (x < 0) {
				System.out.print("Remove " + x + " : ");
				t.remove(-x);
				t.printTree();
			} else {
				Comparable[] arr = t.toArray();
				System.out.print("Final: ");
				for (int i = 0; i < t.size(); i++) {
					System.out.print(arr[i] + " ");
				}
				System.out.println();
				return;
			}
		}
	}

	/**
	 * To find the minimum element in the tree
	 *
	 * @return
	 */
	public T min() {
		if (size() == 0)
			return null;
		Entry<T> t = root();
		while (t.left != null)
			t = t.left;
		return t.element;
	}

	/**
	 * To find the minimum element in the tree
	 *
	 * @return
	 */
	public T max() {
		if (size() == 0)
			return null;
		Entry<T> t = root();
		while (t.right != null)
			t = t.right;
		return t.element;
	}

	/**
	 * Create an array with the elements using in-order traversal of tree
	 *
	 * @return array version of the tree
	 */
	public Comparable[] toArray() {
		Comparable[] arr = new Comparable[size()];
		Entry<T> t = root();
		inOrderTraversal(t, arr, 0);
		return arr;
	}

	/**
	 * Helper method to traverse inorder
	 *
	 * @param t
	 * @param arr
	 * @param i
	 * @return latest index of the array
	 */
	private int inOrderTraversal(Entry<T> t, Comparable[] arr, int i) {
		if (t != null) {
			i = inOrderTraversal(t.left, arr, i);
			arr[i++] = t.element;
			i = inOrderTraversal(t.right, arr, i);
		}
		return i;

	}

	/**
	 * Optional problem 2: Iterate elements in sorted order of keys Solve this
	 * problem without creating an array using in-order traversal (toArray()).
	 */
	public Iterator<T> iterator() {
		return null;
	}

	/**
	 * Print the tree
	 */
	public void printTree() {
		System.out.print("[" + size() + "]");
		printTree(root);
		System.out.println();
	}

	/**
	 * Print the tree using inorder traversal
	 * 
	 * @param node
	 */
	void printTree(Entry<T> node) {
		if (node != null) {
			printTree(node.left);
			System.out.print(" " + node.element);
			printTree(node.right);
		}
	}

}

/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10

*/
