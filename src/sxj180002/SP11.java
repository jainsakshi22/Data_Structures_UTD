package sxj180002;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * @author Sakshi Jain(sxj180002) and Harsh Verma(hxv180001)
 */
public class SP11 {
    public static Random random = new Random();
    private static int threshold = 20;
    static int index;
    private static BinaryHeap pq;
    private static PriorityQueue<Integer> p = new PriorityQueue<>();

    /**
     * <p> Find kth largest elements from array </p>
     *
     * @param arr Array of elements from which k elements need to be selected
     * @param k   Largest element index to be selected
     */
    public static void select(int[] arr, int k) {
        index = select(arr, 0, arr.length, k);
    }

    /**
     * <p> Find kth largest element from array[p..p+n-1]</p>
     *
     * @param arr Array of elements from which k elements need to be selected
     * @param p   Start index of array from which elements need to be selected
     * @param n   End index of array
     * @param k   Largest element index to be selected
     * @return
     */
    private static int select(int[] arr, int p, int n, int k) {
        if (n < threshold) {
            insertionSort(arr, p, p + n - 1);
            return n - k + p;
        } else {
            int q = randomizedPartition(arr, p, p + n - 1);
            int left = q - p;
            int right = n - left - 1;
            if (right >= k) {
                return select(arr, q + 1, right, k);
            } else if (right + 1 == k) {
                return q;
            } else {
                return select(arr, p, left, k - right - 1);
            }
        }
    }

    /**
     * <p> Randomly generates the pivot and swaps element at r position with pivot </p>
     *
     * @param arr Array from which element needs to be swapped
     * @param p   Starting position of array
     * @param r   Last element index from which pivot needs to be selected
     * @return
     */
    private static int randomizedPartition(int[] arr, int p, int r) {
        Random random = new Random();
        int i = random.nextInt((r - p)) + p;
        swap(arr, i, r);
        return partition(arr, p, r);
    }

    /**
     * Partitions array with smaller element than pivot to left and larger elements to right
     *
     * @param arr Array which needs to be partitioned
     * @param p   Starting position of array
     * @param r   Last element index of array
     * @return
     */
    private static int partition(int[] arr, int p, int r) {
        int x = arr[r];
        int i = p;
        for (int j = p; j < r; j++) {
            if (arr[j] <= x) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, r);
        return i;
    }

    /**
     * <p> Swaps elements of array from index i to j </p>
     *
     * @param arr Array of elements from which element needs to be swapped
     * @param i   First index
     * @param j   Second index
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * <p> Helper function used in Select algorithm to sort elements from index p to r </p>
     *
     * @param arr Array of elements to be sorted
     * @param p   Starting index of array from where elements need to be sorted
     * @param r   End index of array till elements need to be sorted
     */
    private static void insertionSort(int[] arr, int p, int r) {
        int temp;
        int j;
        for (int i = p + 1; i <= r; i++) {
            temp = arr[i];
            j = i - 1;
            while (j >= p && temp < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    /**
     * <p> Finds the k largest numbers in an array using priority queue algorithm </p>
     *
     * @param arr Array of elements to be sorted
     * @param k Largest element index to be selected
     */
    public static void selectKLargestElements(int[] arr, int k) {
        Integer[] temp = new Integer[k];
        for (int i = 0; i < k; i++) {
            temp[i] = Integer.valueOf(arr[i]);
        }
        pq = new BinaryHeap(temp);
        for (int i = k; i < arr.length; i++) {
            Integer x = arr[i];
            if (x.compareTo((Integer) pq.peek()) > 0) {
                pq.move(0, x);
                pq.percolateDown(0);
            }
        }
    }

    public static void findKthLargest(int[] nums, int k) {
        for(int i: nums){
            p.offer(i);

            if(p.size()>k){
                p.poll();
            }
        }
    }

    public static void main(String[] args) {
        // Change value of numberOFValues here
        int numberOFValues = 10000;
        System.out.println("N = " + numberOFValues);
        int[] arr = new int[numberOFValues];
        for (int i = 0; i < numberOFValues; i++) {
            arr[i] = i + 1;
        }
        Timer timer = new Timer();
        sxj180002.SP11.Shuffle.shuffle(arr);
        int k = (int) Math.ceil(arr.length / 2.0);
        timer.end();
        timer.scale(1);
        System.out.println("Timer: " + timer);

        timer = new Timer();
        sxj180002.SP11.Shuffle.shuffle(arr);
        //selectKLargestElements(arr, k);
        timer.end();
        timer.scale(1);
        System.out.println("Timer: " + timer);

        timer = new Timer();
        sxj180002.SP11.Shuffle.shuffle(arr);
        findKthLargest(arr, k);
        timer.end();
        timer.scale(1);
        System.out.println("Timer: " + timer);
        while (p.size() != 0) {
            System.out.println(p.poll() + " ");
        }
    }

    /**
     * @author rbk : based on algorithm described in a book
     */

    /* Shuffle the elements of an array arr[from..to] randomly */
    public static class Shuffle {

        public static void shuffle(int[] arr) {
            shuffle(arr, 0, arr.length - 1);
        }

        public static void shuffle(int[] arr, int from, int to) {
            int n = to - from + 1;
            for (int i = 1; i < n; i++) {
                int j = random.nextInt(i);
                swap(arr, i + from, j + from);
            }
        }

        static void swap(int[] arr, int x, int y) {
            int tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }
    }

    /**
     * Timer class for roughly calculating running time of programs
     *
     * @author rbk Usage: Timer timer = new Timer(); timer.start(); timer.end();
     * System.out.println(timer); // output statistics
     */

    public static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public void start() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime - startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() {
            if (!ready) {
                end();
            }
            return elapsedTime;
        }

        public long memory() {
            if (!ready) {
                end();
            }
            return memUsed;
        }

        public void scale(int num) {
            elapsedTime /= num;
        }

        public String toString() {
            if (!ready) {
                end();
            }
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed / 1048576) + " MB / "
                    + (memAvailable / 1048576) + " MB.";
        }
    }
}
