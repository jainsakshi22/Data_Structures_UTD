
// Starter code for lp1.
// Version 1.0 (8:00 PM, Wed, Sep 5).

// Change following line to your NetId
package sxj180002;

import com.sun.tools.corba.se.idl.constExpr.Divide;

import java.util.Arrays;

public class Num implements Comparable<Num> {

    static long defaultBase = 10;  // Change as needed
    long base = defaultBase;  // Change as needed
    long[] arr;  // array to store arbitrarily large integers
    boolean isNegative;  // boolean flag to represent negative numbers
    int len;  // actual number of elements of array that are used;  number is stored in arr[0..len-1]

    public Num(String s) {
        s = s.trim();
        if (s.charAt(0) == '-') {
            isNegative = true;
        }
        boolean leading = true;
        int size = 0;
        for (int i = isNegative ? 1 : 0; i < s.length(); i++) {
            if (leading && s.charAt(i) == '0') {
                continue;
            } else {
                leading = false;
                size++;
            }
        }
        arr = new long[size];
        for (int i = s.length() - 1, j = 0; i >= s.length() - size; i--) {
            arr[j++] = s.charAt(i);
        }


    }

    public Num(long x) {

    }

    private Num(long[] arr){
        this.arr = arr;
    }

    public static Num add(Num a, Num b) {
        int maxIter = a.arr.length > b.arr.length ? a.arr.length : b.arr.length;
        long[] resultArr = new long[maxIter + 1];
        long carry = 0;
        for (int i = 0; i <= maxIter; i++) {
            long num1 = i >= a.arr.length ? 0 : a.arr[i];
            long num2 = i >= b.arr.length ? 0 : b.arr[i];
            long sum = num1 + num2 + carry;
            long result = sum % defaultBase;
            carry = sum / defaultBase;
            resultArr[resultArr.length - 1 - i] = result;
        }
        Num resNum = new Num(Arrays.toString(resultArr));
        return resNum;
    }

    public static Num subtract(Num a, Num b) {
        boolean resIsNeg = false;
        if(a.arr.length < b.arr.length ||
                (a.arr.length == b.arr.length
                        && a.arr[a.arr.length-1]<b.arr[b.arr.length -1])){

            resIsNeg = true;
            Num temp = a;
            a=b;
            b=temp;
        }
        long[] resultArr = new long[a.arr.length];
        for(int i = 0; i<a.arr.length;i++){
            long result = 0;
            long num2 = i >= b.arr.length ? 0 : b.arr[i];
           if(a.arr[i]<num2){
               result = a.arr[i]+defaultBase - num2;
               a.arr[i+1]--;
           }else{
               result = a.arr[i] - num2;
           }
            resultArr[resultArr.length - 1 - i] = result;
        }


        Num resNum = new Num(Arrays.toString(resultArr));
        if(resIsNeg) resNum.isNegative = true;
        return resNum;
    }

    public static Num product(Num a, Num b) {

        if(a.arr.length == 1 && b.arr.length == 1){
            return new Num(a.arr[0]*b.arr[0]);
        }
        if(a.arr.length > b.arr.length){
            b = pad(b, a.arr.length);
        }else if(a.arr.length < b.arr.length){
            a = pad(a, b.arr.length);
        }
        Num a1 = splitFirstHalf(a);
        Num a2 = splitSecondHalf(a);
        Num b1 = splitFirstHalf(b);
        Num b2 = splitSecondHalf(b);

        Num z0 = product(a1,b1);
        Num z1 = product(add(a1,a2),add(b1,b2));
        Num z2 = product(a2,b2);

        Num z3 = leftShift(z2,a.arr.length);
        Num z4 = leftShift(subtract(z1,add(z2,z0)),a.arr.length/2);

        return add(z3,add(z4,z0));
    }

    private static Num leftShift(Num a, int length) {
        long[] array = new long[a.arr.length+length];
        for(int i = 0; i < array.length; i++){
            if(i < length){
                array[i] = 0;
            }else{
                array[i] = a.arr[i - length];
            }
        }
        return new Num(array);
    }

    private static Num splitSecondHalf(Num a) {
        long[] array = new long[a.arr.length - (a.arr.length/2)];
        for(int i = 0; i<array.length; i++){
            array[i] = a.arr[i];
        }
        return new Num(array);
    }

    private static Num splitFirstHalf(Num a) {
        long[] array = new long[a.arr.length/2];
        for(int i = a.arr.length/2,j=0; i<a.arr.length;i++){
            array[j++] = a.arr[i];
        }
        return new Num(array);
    }

    private static Num pad(Num b, int length) {
        long[] array = new long[length];
        for(int i = 0; i<length; i++){
            if(i >= b.arr.length){
                array[i] = 0L;
            }else{
                array[i] = b.arr[i];
            }
        }
        return new Num(array);
    }


    // Use divide and conquer
    public static Num power(Num a, long n) {
        if (n == 0) {
            if (a.checkNumIsZero()) {
                throw new IllegalArgumentException("Base and power both can't be zero");
            }
            return new Num(1);
        }
        if (a.checkNumIsZero()) {
            return new Num(0);
        }

        Num pow = power(a, n/2);
        Num powSquare = product(pow, pow);

        if (n % 2 == 0) { //Check if number is even
            return powSquare;
        }
        return product(a, powSquare);
    }

    // Use binary search to calculate a/b
    public static Num divide(Num a, Num b) {

        if (b.checkNumIsZero()) {
            throw new IllegalArgumentException("Cannot Divide By Zero");
        }

        Num zero = new Num(0);
        Num low = new Num(0);
        Num high = new Num(Arrays.copyOf(a.arr, a.arr.length));
        Num mid = null;
        while (true) {
            mid = subtract(high,low).by2();
            Num rem = subtract(a, product(b,mid));
            if (rem.compareTo(zero) == -1 ) {
                high = mid;
            } else if (rem.compareTo(b) == 1) {
                low = mid;
            } else {
                break;
            }
        }
        return mid;
    }

    // return a%b
    public static Num mod(Num a, Num b) {
        Num quotient = divide(a,b);
        return subtract(a,product(b, quotient));
    }

    // Use binary search
    public static Num squareRoot(Num a) {

        if (a.checkNumIsZero()) {
            return new Num(0);
        }

        Num low = new Num(0);
        Num high = new Num(Arrays.copyOf(a.arr, a.arr.length));
        Num squareRoot = null;
        Num mid = null;
        while (low.compareTo(high) == -1) {
           mid = add(low, high).by2();
           if (power(mid, 2) == a) {
               squareRoot = mid;
               break;
           } else if (power(mid, 2).compareTo(a) == 1) {
               high = subtract(mid, new Num(1));
           } else if (power(mid, 2).compareTo(a) == -1) {
               low = add(mid, new Num(1));
               squareRoot = mid;
           }
        }
        return squareRoot;
    }


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
        return 0;
    }

    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    public void printList() {
    }

    // Return number to a string in base 10
    public String toString() {
        return null;
    }

    public long base() {
        return base;
    }

    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {
        return null;
    }

    // Divide by 2, for using in binary search
    public Num by2() {
        return leftShift(this.convertBase(2),1).convertBase((int)defaultBase);
    }

    // Evaluate an expression in postfix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluatePostfix(String[] expr) {
        return null;
    }

    // Evaluate an expression in infix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "(", ")", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluateInfix(String[] expr) {
        return null;
    }

    private boolean checkNumIsZero() {
        boolean isZero = false, isNeg = false;
        if (this.arr[0] == '-') {
            isNeg = true;
        }
        for (int i = isNeg ? 1 : 0; i < this.arr.length; i++) {
            if (this.arr[i] == '0') {
                continue;
            } else {
                isZero = false;
                break;
            }
        }
        return isZero;
    }

    public static void main(String[] args) {
        Num x = new Num(999);
        Num y = new Num("8");
        Num z = Num.add(x, y);
        System.out.println(z);
        Num a = Num.power(x, 8);
        System.out.println(a);
        if (z != null) z.printList();
    }
}
