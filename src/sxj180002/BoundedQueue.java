package sxj180002;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BoundedQueue<T> {
    private int maxSize, front, rear, size;
    private Object[] boundedQueue;

    public BoundedQueue(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Queue size should be greater than 1");
        }
        maxSize = size;
        front = size = 0;
        rear = maxSize - 1;
        boundedQueue = new Object[maxSize];
    }

    public boolean offer(T x) {
        if (isFull()) {
            return false;
        }
        rear = (rear + 1) % maxSize;
        boundedQueue[rear] = x;
        size++;
        return true;
    }

    public T poll() {
        if (isEmpty()) {
            return null;
        }
        T firstItem = (T) boundedQueue[front];
        front = (front + 1) % maxSize;
        size--;
        return firstItem;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return (T)boundedQueue[front];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        front = size = 0;
        rear = maxSize - 1;
    }

    public void toArray(T[] a) {
        if (a.length < size) {
            throw new IllegalArgumentException("Array size is smaller than elements present in queue");
        }

        int i = front % maxSize;
        int arrayIndex = 0;
        while (arrayIndex < size) {
            a[arrayIndex++] = (T)boundedQueue[i];
            i = (i+1) % maxSize;
        }
    }

    public boolean isFull() {
        return size == maxSize;
    }

    public static void main(String[] args) {
        int n = 10;
        if (args.length > 0) {
            n = Integer.parseInt((args[0]));
        }

        BoundedQueue<Integer> queue = new BoundedQueue<>(15);
        for(int i = 1; i <= n; i++) {
            queue.offer(Integer.valueOf(i));
        }
        Scanner in = new Scanner(System.in);
        whileloop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1: // Add element
                    System.out.println("Add an element: ");
                    if (!queue.offer(Integer.valueOf(in.nextInt()))) {
                        System.out.println("Can't add element. Queue is full");
                    };
                    break;
                case 2: // Poll element
                    System.out.println("Poll: " + queue.poll());
                    break;
                case 3: // Peek element
                    System.out.println("Peek: " + queue.peek());
                    break;
                case 4: // Get size element
                    System.out.println("Size: " + queue.size());
                    break;
                case 5: // Check if queue is empty
                    System.out.println(queue.isEmpty() ? "Queue is empty" : "Queue is not empty");
                    break;
                case 6: // Clear queue
                    System.out.println("Clear queue: ");
                    queue.clear();
                    break;
                case 7: // Add elements from queue to array
                    Integer[] arr = new Integer[queue.size];
                    queue.toArray(arr);
                    System.out.println(Arrays.toString(arr));
                    break;
                default:
                    break whileloop;
            }
        }
    }

    public static class BinaryHeap<T extends Comparable<? super T>> {
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
                throw new ArrayIndexOutOfBoundsException("Priority queue is full. Can't add more element"); // Difference
                // between add
                // and offer
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

         */
        public T remove() { /* throw exception if pq is empty */
            if (size <= 0) {
                throw new NoSuchElementException("Can't remove the element as queue is empty"); // Difference between remove
                // and poll
            }
            T obj = pq[0];
            pq[0] = pq[--size];
            percolateDown(0); // Replace the first element with the last element and percolate it down
            return obj; // Return the removed node
        }

        public T poll() { /* return false if pq is empty */
            try {
                return remove();
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
         * <p>
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
         * <p>
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
            Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

            BinaryHeap<Integer> heap = new BinaryHeap<Integer>(arr);
            heap.print();
            Scanner in = new Scanner(System.in);
            whileloop:
            while (in.hasNext()) {
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
}
