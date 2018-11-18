/**
 * @author Pooja Srinivasan (pxs176230), Sakshi Jain
 * Implementation of Double Hashing with operations like add(), remove(), contains()
 */
package sxj180002;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class DoubleHashing<T> {

    class Entry<T> {
        T key;
        boolean value; // indicates isDeleted

        public Entry(T key, boolean value) {
            this.key = key;
            this.value = false;
        }
    }

    private static final int TABLE_SIZE = 12000;
    private int size;
    private Entry[] hashTable;
    private int primeSize;

    public DoubleHashing() {
        this(TABLE_SIZE);
    }

    public DoubleHashing(int tablesize) {
        size = 0; // initial capacity = 0
        hashTable = new Entry[nextPrime(tablesize)]; // creates a hashtable of size to a prime number that is greater than or equal to given size
        primeSize = getPrimeNum(); // assigns to a prime number that is as equal to the table size for second hash funtion
    }

    /**
     * @return size of hashtable (number og keys)
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the alloted table size for hashtable
     */
    public int getTableSize() {
        return hashTable.length;
    }

    /**
     * @param key
     * @return true if key is present in hashtable else false
     */
    public boolean contains(T key) {
        int hash1 = hash1(key);
        int hash2 = hash2(key);
        while (hashTable[hash1] != null && (hashTable[hash1].key == null || !hashTable[hash1].key.equals(key))) {
            hash1 += hash2;
            hash1 = hash1 % getTableSize();
        }
        if (hashTable[hash1] == null) return false;
        return true;
    }

    /**
     * adds key to hashset by using hash1 and hash2 and rehashes the table if size reaches maximum capacity
     *
     * @param key
     */
    public void add(T key) {
        int hash1 = hash1(key);
        int hash2 = hash2(key);
        while (hashTable[hash1] != null && !hashTable[hash1].key.equals(key) && hashTable[hash1].key != null) {
            hash1 += hash2;
            hash1 = hash1 % getTableSize();
        }
        if (hashTable[hash1] == null || hashTable[hash1].key == null) {
            hashTable[hash1] = new Entry(key, false);
            size++;
            if (size == getTableSize()) {
                rehash();
            }
        }
    }


    /**
     * removes key from table if present and returns true else returns false
     *
     * @param key
     * @return
     */
    public boolean remove(T key) {
        if (size == 0) {
            System.out.println("Table is empty");
        }
        int hash1 = hash1(key);
        int hash2 = hash2(key);
        while (hashTable[hash1] != null && (hashTable[hash1].key == null || !hashTable[hash1].key.equals(key))) {
            hash1 += hash2;
            hash1 %= getTableSize();
        }
        if (hashTable[hash1] != null) {
            hashTable[hash1].key = null;
            hashTable[hash1].value = true; //marking isDeleted = true;
            size--;
            return true;
        }
        return false;
    }

    /**
     * first hashfunction to find index in table based on hashcode
     *
     * @param key
     * @return
     */
    public int hash1(T key) {
        int hashVal = key.hashCode();
        hashVal = hashVal % getTableSize();
        if (hashVal < 0) {
            hashVal += getTableSize();
        }
        return hashVal;
    }

    /**
     * second hashfunction that calculates offset to move from index calculated from first hash function
     *
     * @param key
     * @return
     */
    public int hash2(T key) {
        int hashval = hash1(key);
        return primeSize - hashval % primeSize;
    }

    /**
     * chooses the biggest primenum that is equal to or less than tablesize
     *
     * @return
     */
    public int getPrimeNum() {
        for (int i = getTableSize() - 1; i >= 2; i--) {
            if (isPrime(i)) return i;
        }
        return 3;
    }

    /**
     * method to
     * increase hashtablesize when it reaches maximum capacity
     * sets a primenumber to a tablesize that is greater than twice the existing one
     */
    private void rehash() {
        Entry<T>[] oldArray = hashTable;
        int tablesize = 2 * getTableSize();
        hashTable = new Entry[nextPrime(tablesize)];
        size = 0;
        this.primeSize = getPrimeNum();
        for (int i = 0; i < oldArray.length; i++) {
            if (oldArray[i].key != null) {
                add(oldArray[i].key);
            }
        }
    }

    /**
     * chooses a primenumber that is greater than or equal to tablesize
     *
     * @param tablesize
     * @return
     */
    public int nextPrime(int tablesize) {
        if (tablesize % 2 == 0)
            tablesize++;
        for (; !isPrime(tablesize); tablesize += 2) ;
        return tablesize;
    }

    /**
     * returns true if the method is prime else false
     *
     * @param n
     * @return
     */

    public boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    /**
     * returns count of distinct unique elements in double hashing table
     *
     * @param arr of values
     * @param <T>
     * @return count
     */
    public static <T> int distinctElements(T[] arr) {
        DoubleHashing<T> newTable = new DoubleHashing<>();
        for (T element : arr) {
            newTable.add(element);
        }
        return newTable.size;
    }

    /**
     * optional
     * driver method
     *
     * @param args
     */
    public static void main(String[] args) {
        int n = 15000000;
        Random random = new Random();
        HashSet<Integer> set = new HashSet<>();
        DoubleHashing<Integer> doubleHashingSet = new DoubleHashing<>();
        Integer[] elements = new Integer[n];
        int count = 0;
        while (count < n) {
            elements[count] = random.nextInt(n);
            count++;
        }
        // adding elements in hashset
        System.out.println("Adding elements to HashSet");
        for (int element : elements) {
            set.add(element);
        }
        // adding elements in doublehashingset
        System.out.println("Adding elements to DoubleHashingSet");
        for (int element : elements) {
            doubleHashingSet.add(element);
        }
        Timer timer = new Timer();
        System.out.println("Enter an option:");
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    //add operation
                    System.out.println("Java HashSet Implementation");
                    timer.start();
                    int randomVal = new Random().nextInt(n);
                    set.add(randomVal);
                    timer.end();
                    System.out.println(timer.toString());
                    System.out.println("Double Hashing Implementation");
                    timer.start();
                    doubleHashingSet.add(randomVal);
                    timer.end();
                    System.out.println(timer.toString());
                    break;
                case 2:
                    //contains operation
                    System.out.println("Java HashSet Implementation");
                    timer.start();
                    count = 0;
                    while (count < 100000) {
                        set.contains(count);
                        count++;
                    }
                    timer.end();
                    System.out.println(timer.toString());
                    System.out.println("Double Hashing Implementation");
                    timer.start();
                    count = 0;
                    while (count < 100000) {
                        doubleHashingSet.contains(count);
                        count++;
                    }
                    timer.end();
                    System.out.println(timer.toString());
                    break;
                case 3:
                    //remove operation
                    System.out.println("Java HashSet Implementation");
                    timer.start();
                    count = 0;
                    while (count < 30000) {
                        int randomElement1 = elements[(int) (Math.random() * elements.length)];
                        set.remove(randomElement1);
                        count++;
                    }
                    timer.end();
                    System.out.println(timer.toString());
                    System.out.println("Double Hashing Implementation");
                    timer.start();
                    count = 0;
                    while (count < 30000) {
                        int randomElement1 = elements[(int) (Math.random() * elements.length)];
                        doubleHashingSet.remove(randomElement1);
                        count++;
                    }
                    timer.end();
                    System.out.println(timer.toString());
                    break;
                default:
                    //Count Distinct Elements
                    System.out.println("Counting Distinct elemet");
                    System.out.println("Java HashSet Implementation");
                    timer.start();
                    for (int element : elements) {
                        set.add(element);
                    }
                    timer.end();
                    System.out.println("Unique Elements using Java HashSet " + set.size());
                    System.out.println(timer.toString());
                    System.out.println("Double Hashing Implementation");
                    timer.start();
                    System.out.println(DoubleHashing.distinctElements(elements));
                    timer.end();
                    System.out.println(timer.toString());
            }
        }

     /*  DoubleHashing<Integer> set = new DoubleHashing<>();
       set.add(2); // index 2 % 7 = 2
       set.add(9); // index 5 - 2%5 = 5 - 2 = 3
       set.add(16); // index 5 - 3%5 = 2
       set.add(23); // index
        System.out.println(set.getSize());
       set.remove(9);
       System.out.println(set.getSize());
       System.out.println(set.contains(9));
       System.out.println(set.contains(23));*/
    }

}
