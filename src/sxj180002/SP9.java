/*
 * @author Sakshi Jain (sxj180002) and Pooja Srinivasan (pxs176230)
 * Implementation of Sorting algorithm - Merge Sort and Insertion Sort
 */

package sxj180002;
import java.util.Arrays;
import java.util.Random;

public class SP9 {
    public static Random random = new Random();
    public static int numTrials = 100;
    public static int threshold = 80;

    /**
     * <p>Main method to execute the sort algorithm based on the 4 options provided: </p>
     * 	<ol>
     *     <li>Insertion Sort</li>
     *     <li>MergeSort Take 1</li>
     *     <li>MergeSort Take 2</li>
     *     <li>MergeSort Take 3</li>
     *	 </ol>
     *
     * @param args The arguments are:
     *             <ol>
     *             <li>No of elements to be sorted</li>
     *             <li>Sorting algo option</li>
     *             </ol>
     */
    public static void main(String[] args) {
        int n = 100;
        int choice = 1 + random.nextInt(4);
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }
        if (args.length > 1) {
            choice = Integer.parseInt(args[1]);
        }
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        Timer timer = new Timer();
        switch (choice) {
            case 1:
                Shuffle.shuffle(arr);
                numTrials = 1;
                insertionSort(arr);
                break;
            case 2:
                for (int i = 0; i < numTrials; i++) {
                    Shuffle.shuffle(arr);
                    mergeSort1(arr);
                }
                break;
            case 3:
                for (int i = 0; i < numTrials; i++) {
                    Shuffle.shuffle(arr);
                    mergeSort2(arr);
                }
                break;
            case 4:
                for (int i = 0; i < numTrials; i++) {
                    Shuffle.shuffle(arr);
                    mergeSort3(arr);
                }
                break;
        }
        timer.end();
        timer.scale(numTrials);

        System.out.println("Choice: " + choice + "\n" + timer);
    }

    /**
     * <p>Insertion sort</p>
     * @param arr Array of elements to be sorted
     */
    public static void insertionSort(int[] arr) {
        insertionSort(arr, 0, arr.length - 1);
    }

    /**
     * <p> Helper function used in insertion sort to sort elements from index p to r </p>
     * @param arr Array of elements to be sorted
     * @param p Starting index of array from where elements need to be sorted
     * @param r End index of array till elements need to be sorted
     */
    private static void insertionSort(int[] arr, int p, int r) {
        for (int i = p + 1; i <= r; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= p && temp < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    /**
     * <p> Merge sort Take 3 </p>
     * @param arr Array of elements to be sorted
     */
    public static void mergeSort3(int[] arr) {
        int[] arr2 = Arrays.copyOf(arr, arr.length);
        mergeSort3(arr, arr2, 0, arr.length);
    }

    /**
     * <p> Helper method of merge sort take 3 </p>
     * @param arr1 Array of elements to be sorted
     * @param arr2 Duplicate array of arr1
     * @param left Start index of array to be sorted
     * @param n Number of elements to be sorted
     */
    private static void mergeSort3(int[] arr1, int[] arr2, int left, int n) {
        if (n < threshold) {
            insertionSort(arr1, left, left + n - 1);
        } else {
            int leftEnd = n / 2;
            mergeSort3(arr2, arr1, left, leftEnd);
            mergeSort3(arr2, arr1, left + leftEnd, n - leftEnd);
            merge3(arr1, arr2, left, left + leftEnd - 1, left + n - 1);
        }
    }

    /**
     * <p> Merge operation of divide and conquer for Merge Sort take 3 </p>
     * @param arr1 Array of elements to be sorted
     * @param arr2 Duplicate array of arr1
     * @param p Start index of left sub array which has to be merged
     * @param q End index of left sub array which has to be merged
     * @param r End index of right sub array which has to be merged
     */
    private static void merge3(int[] arr1, int[] arr2, int p, int q, int r) {
        int i = p, j = q + 1, k = p;
        while (i <= q && j <= r) {
            if (arr2[i] <= arr2[j]) {
                arr1[k++] = arr2[i++];
            } else {
                arr1[k++] = arr2[j++];
            }
        }
        while (i <= q) {
            arr1[k++] = arr2[i++];
        }
        while (j <= r) {
            arr1[k++] = arr2[j++];
        }
    }

    /**
     * <p>Merge sort Take 2</p>
     * @param arr Array of elements to be sorted
     */
    public static void mergeSort2(int[] arr) {
        int arr2[] = new int[arr.length];
        mergeSort2(arr, arr2, 0, arr.length);
    }

    /**
     * <p> Helper method for merge sort take 2 </p>
     * @param arr1 Array of elements to be sorted
     * @param arr2 Duplicate array of arr1
     * @param left Start index of array to be sorted
     * @param n Number of elements to be sorted
     */
    private static void mergeSort2(int[] arr1, int[] arr2, int left, int n) {
        if (n < threshold) {
            insertionSort(arr1, left, left + n - 1);
        } else {
            int leftEnd = n / 2;
            mergeSort2(arr1, arr2, left, leftEnd);
            mergeSort2(arr1, arr2, left + leftEnd, n - leftEnd);
            merge2(arr1, arr2, left, left + leftEnd - 1, left + n - 1);
        }
    }

    /**
     * <p> Merge operation of divide and conquer for Merge Sort take 2 </p>
     * @param arr1 Array of elements to be sorted
     * @param arr2 Empty array which has same length as arr1
     * @param p Start index of the left subarray which has to be merged
     * @param q End index of the left subarray which has to be merged
     * @param r End index of the right subarray which has to be merged
     */
    private static void merge2(int[] arr1, int[] arr2, int p, int q, int r) {
        System.arraycopy(arr1, p, arr2, p, r - p + 1);
        int i = p, j = q + 1;
        for (int k = p; k <= r; k++) {
            if (j > r || (i <= q && arr2[i] < arr2[j])) {
                arr1[k] = arr2[i++];
            } else {
                arr1[k] = arr2[j++];
            }
        }
    }

    /**
     * <p>Merge sort Take 1</p>
     * @param arr Array of elements to be sorted
     */
    public static void mergeSort1(int[] arr) {
        mergeSort1(arr, 0, arr.length - 1);
    }

    /**
     * <p>Helper function used in mergeSort Take 1</p>
     * @param arr Array of elements to be sorted
     * @param p Starting index of array from where elements need to be sorted
     * @param r End index of array till elements need to be sorted
     */
    private static void mergeSort1(int[] arr, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort1(arr, p, q);
            mergeSort1(arr, q + 1, r);
            merge1(arr, p, q, r);
        }
    }

    /**
     * <p> Merge operation of divide and conquer for Merge Sort Take 1</p>
     * @param arr Array of elements to be sorted
     * @param p Start index of left subarray which has to be merged
     * @param q End index of left subarray which has to be merged
     * @param r End index of right subarray which has to be merged
     */
    private static void merge1(int[] arr, int p, int q, int r) {
        int left[] = new int[q - p + 1];
        int right[] = new int[r - q];
        System.arraycopy(arr, p, left, 0, q - p + 1);
        System.arraycopy(arr, q + 1, right, 0, r - q);
        int i = 0, j = 0;
        for (int k = p; k <= r; k++) {
            if (j >= right.length || (i < left.length && left[i] <= right[j])) {
                arr[k] = left[i++];
            } else {
                arr[k] = right[j++];
            }
        }
    }


    /**
     * Timer class for roughly calculating running time of programs
     *
     * @author rbk
     * Usage:  Timer timer = new Timer();
     * timer.start();
     * timer.end();
     * System.out.println(timer);  // output statistics
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
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed / 1048576) + " MB / " + (memAvailable / 1048576) + " MB.";
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

        public static <T> void shuffle(T[] arr) {
            shuffle(arr, 0, arr.length - 1);
        }

        public static void shuffle(int[] arr, int from, int to) {
            int n = to - from + 1;
            for (int i = 1; i < n; i++) {
                int j = random.nextInt(i);
                swap(arr, i + from, j + from);
            }
        }

        public static <T> void shuffle(T[] arr, int from, int to) {
            int n = to - from + 1;
            Random random = new Random();
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

        static <T> void swap(T[] arr, int x, int y) {
            T tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        public static <T> void printArray(T[] arr, String message) {
            printArray(arr, 0, arr.length - 1, message);
        }

        public static <T> void printArray(T[] arr, int from, int to, String message) {
            System.out.print(message);
            for (int i = from; i <= to; i++) {
                System.out.print(" " + arr[i]);
            }
            System.out.println();
        }
    }
}

