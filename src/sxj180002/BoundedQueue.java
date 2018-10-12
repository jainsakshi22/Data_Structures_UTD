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
}
