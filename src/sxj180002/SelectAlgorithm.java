/**
 * @author Sakshi Jain(sxj180002) and Harsh Verma
 */

package sxj180002;
import java.util.Random;

public class SelectAlgorithm {

    public static Random random = new Random();
    private static int threshold = 20;
    static int index;

    /**
     * <p> Find kth largest elements from array </p>
     * @param arr Array of elements from which k elements need to be selected
     * @param k Largest element index to be selected
     */
    public static void select(int[] arr, int k) {
        index = select(arr, 0, arr.length, k);
    }

    /**
     * <p> Find kth largest element from array[p..p+n-1]</p>
     * @param arr Array of elements from which k elements need to be selected
     * @param p Start index of array from which elements need to be selected
     * @param n End index of array
     * @param k Largest element index to be selected
     * @return
     */
    private static int select(int[] arr, int p, int n, int k) {
        if (n < threshold) {
            insertionSort(arr, p, p + n - 1);
            return n-k+p;
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
     * @param arr Array from which element needs to be swapped
     * @param p Starting position of array
     * @param r Last element index from which pivot needs to be selected
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
     * @param arr Array which needs to be partitioned
     * @param p Starting position of array
     * @param r Last element index of array
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
     * @param arr Array of elements from which element needs to be swapped
     * @param i First index
     * @param j Second index
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * <p> Helper function used in insertion sort to sort elements from index p to r </p>
     * @param arr Array of elements to be sorted
     * @param p Starting index of array from where elements need to be sorted
     * @param r End index of array till elements need to be sorted
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

    public static void main(String[] args) {

        int n = 1000000;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }
        SelectAlgorithm.Shuffle.shuffle(arr);
        int k = (int) Math.ceil(arr.length / 2.0);
        select(arr, k);
        System.out.println("Index is: " + index);


        //For testing
//        for (int i = arr.length - k; i < arr.length; i++) {
//            System.out.print(arr[i] + " ");
//        }
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
}
