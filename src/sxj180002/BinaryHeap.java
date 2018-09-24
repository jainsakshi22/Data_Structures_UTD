/** @author Sakshi Jain, Arunachalam Saravanan
 */

package sxj180002;

import java.util.*;

public class BinaryHeap<T extends Comparable<? super T>> {
	T[] pq;
	Comparator<T> comp;
	int size;

	// Constructor for building an empty priority queue using natural ordering of T
	public BinaryHeap(T[] q) {
		// Use a lambda expression to create comparator from compareTo
		this(q, (T a, T b) -> a.compareTo(b));
	}

	// Constructor for building an empty priority queue with custom comparator
	public BinaryHeap(T[] q, Comparator<T> c) {
		pq = q;
		comp = c;
		size = q.length; // Check what should be the size of queue
	}

	public void add(T x) { /* throw exception if pq is full */
		if (size == pq.length) {
			throw new ArrayIndexOutOfBoundsException("Priority queue is full. Can't add more element"); // Difference between add and offer
		}
		pq[size] = x;
		percolateUp(size);
		size++;
	}

	public boolean offer(T x) { /* return false if pq is full */
		try {
			add(x);
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false; // return false if queue is full and program can't add more element to queue
		}
	}

	/*
	Get the root element and remove that element
	*/
	public T remove() { /* throw exception if pq is empty */
		if (size <= 0) {
			throw new NoSuchElementException("Can't remove the element as queue is empty"); // Difference between remove
			// and poll
		}
		T obj = pq[0];
		pq[0] = pq[--size]; //Replace the first element with the last element and decrease the size of heap
		percolateDown(0); //percolate down first element
		return obj; // Return the removed node
	}

	public T poll() { /* return false if pq is empty */

		try {
			return remove(); //Try removing the first element. If it fails, return null instead of throwing exception
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public T peek() { /* return null if pq is empty */

		if (pq.length == 0) {
			return null;
		}
		return pq[0]; // Return the first element of the heap
	}

	/**
	 * pq[i] may violate heap order with parent
	 *
	 * While adding a new element, increase the size of the array and fill the hole
	 */
	void percolateUp(int i) {
		T obj = pq[i];
		while (i > 0 && comp.compare(pq[parent(i)], obj) > 0) {
			pq[i] = pq[parent(i)];
			i = parent(i);
		}
		pq[i] = obj;
	}

	/**
	 * pq[i] may violate heap order with children
	 *
	 * After the first element is removed, move the last element to the first and
	 * push it down to fill the hole caused by first element
	 */
	void percolateDown(int i) {
		T obj = pq[i];
		int child = leftChild(i);
		while (child <= size - 1) {
			if (child + 1 < size && comp.compare(pq[child], pq[child + 1]) > 0) {
				child++;
			}
			if (comp.compare(obj, pq[child]) < 0) {
				break; // Stop when object is less than the left child
			}
			pq[i] = pq[child];
			i = child;
			child = leftChild(i);
		}
		pq[i] = obj;
	}

	// Assign x to pq[i]. Indexed heap will override this method
	void move(int i, T x) {
		pq[i] = x;
	}

	// Get the Parent node
	int parent(int i) {
		return (i - 1) / 2;
	}

	// Get the Left child
	int leftChild(int i) {
		return 2 * i + 1;
	}

	// Print the heap pq
	void print() {
		for (int i = 0; i < size; i++) {
			System.out.print(pq[i] + " ");
		}
	}

	public static void main(String[] args) {
		Integer[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		BinaryHeap<Integer> heap = new BinaryHeap<Integer>(arr);
		heap.print();
		Scanner in = new Scanner(System.in);
		whileloop: while (in.hasNext()) {
			int com = in.nextInt();
			switch (com) {
				case 1: // Add element
					heap.add(11);
					heap.print();
					break;
				case 2: // Offer element
					System.out.println(heap.offer(12));
					heap.print();
					break;
				case 3: // Remove element
					heap.remove();
					heap.print();
					break;
				case 4: // Poll element
					heap.poll();
					heap.print();
					break;
				case 5: // Peak element
					System.out.println(heap.peek());
					heap.print();
					break;
				default:
					break whileloop;
			}
		}
	}
}
