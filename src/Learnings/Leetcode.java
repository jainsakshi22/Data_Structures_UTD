package Learnings;

import java.io.IOException;
import java.util.*;

public class Leetcode {

    /* 3. Longest Substring Without Repeating Characters */
    public int lengthOfLongestSubstring(String s) {
        int len = s.length();
        Set<Character> set = new HashSet<Character>();
        int i = 0, j = 0, max_length = 0;
        while (i < len && j < len) {
            if (set.contains(s.charAt(j))) {
                set.remove(s.charAt(i));
                i++;
            } else {
                set.add(s.charAt(j));
                j++;
                max_length = Math.max(max_length, j - i);
            }
        }
        return max_length;
    }

    static int MAX_CHAR = 26;

    // function to return true if strings have
    // common substring and no if strings have
    // no common substring
    static boolean twoStrings(String s1, String s2)
    {
        // vector for storing character occurrences
        boolean v[]=new boolean[MAX_CHAR];
        Arrays.fill(v,false);

        // increment vector index for every
        // character of str1
        for (int i = 0; i < s1.length(); i++)
            v[s1.charAt(i) - 'a'] = true;

        // checking common substring of str2 in str1
        for (int i = 0; i < s2.length(); i++)
            if (v[s2.charAt(i) - 'a'])
                return true;

        return false;
    }

    public String longestCommonPrefix(String[] strs) {

        if (strs == null || strs.length == 0) return "";

        String firstStr = strs[0];
        for (int i = 0; i < firstStr.length(); i++) {
            for (int j = 1; j < strs.length; j++) {
                if (strs[j].length() == i || strs[j].charAt(i) != firstStr.charAt(i)) {
                    return firstStr.substring(0, i);
                }
            }
        }
        return firstStr;
    }

    public int reverseInteger(int x) {
        long result = 0;
        while(x != 0) {
            result = (result*10) + (x % 10);
            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) return 0;
            x /= 10;
        }
        return (int)result;
    }

    /* 9. Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward. */
    public boolean isIntegerPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;

        int rev = 0;
        while (x > rev) {
            rev = rev * 10 + x % 10;
            x = x/10;
        }
        return (x == rev) || (x == rev/10);
    }

    /* 13. Roman to Integer */
    public int romanToInt(String s) {

        // Do this in constructor.
        // As of now, doing in function as it is common class for Leetcode problems,
        // and stack, hashmap will be used for various questions
        HashMap<Character, Integer> map;
        Stack<Integer> stack;

        stack = new Stack<Integer>();
        map = new HashMap<Character, Integer>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        for (char c: s.toCharArray() ) {
            int value = map.get(c);
            if (stack.empty()) {
                stack.push(value);
                continue;
            }
            int top = stack.peek();
            if (top < value) {
                stack.pop();
                stack.push(value-top);
            } else {
                stack.push(value);
            }
        }

        int number = 0;
        while (!stack.empty()) {
            number = number + stack.pop();
        }
        return number;
    }

    /* 20. Valid Parentheses */
    public boolean isValidParanthesis(String s) {
        HashMap<Character, Character> hash;
        Stack<Character> stack;

        hash = new HashMap<Character, Character>();
        stack = new Stack<Character>();
        stack.push('#');
        hash.put(')', '(');
        hash.put('}', '{');
        hash.put(']', '[');

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (hash.containsValue(c)) {
                stack.push(c);
            } else {
                Character top = stack.peek();
                if (isMatchingPair(c, top, hash)) {stack.pop();}
                else return false;
            }
        }
        Character element = stack.pop();
        if (element == '#') return true;
        return false;
    }

    // Used by isValidParanthesis()
    private boolean isMatchingPair(Character key, Character value, HashMap<Character, Character> hash) {
        if (hash.get(key) == value) return true;
        return false;
    }

    /* 119. Pascal's Triangle II */
    public List<Integer> getPascalTriangleRow(int rowIndex) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        for (int i = 0; i < rowIndex; i++) {
            list = getPascal(list);
        }
        return list;
    }

    //Used by Pascal's Triangle II
    private List<Integer> getPascal(List<Integer> list) {
        List<Integer> newList = new ArrayList<Integer>();
        newList.add(list.get(0));
        for (int i = 0; i < list.size() -1; i++) {
            newList.add(list.get(i) + list.get(i+1));
        }
        newList.add(list.get(list.size() - 1));
        return newList;
    }

    /* 121. Best Time to Buy and Sell Stock */
    public int maxStockProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        int buy = prices[0], sell = -1;
        int profit = 0;
        for (int price: prices) {
            if (price < buy) buy = price;
            if (price > buy && price - buy > profit) profit = price - buy;
        }
        return profit;
    }

    /* 760. Find Anagram Mappings
        Index mapping P, from A to B. A mapping P[i] = j means the ith element in A appears in B at index j
    */
    public int[] anagramMappings(int[] A, int[] B) {
        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
        for (int i = 0; i < B.length; i++) {
            hash.put(B[i],i);
        }

        int output[] = new int[A.length];
        for (int i = 0; i < A.length; i++) {
            int index = hash.get(A[i]);
            output[i] = index;
        }
        return output;
    }

    public static void main(String[] args) throws IOException {
        Leetcode obj = new Leetcode();
        Scanner in = new Scanner(System.in);
        whileloop: while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 3: //Longest Substring Without Repeating Characters
                    System.out.println("Enter string to calulate Longest Substring Without Repeating Characters");
                    Scanner scan = new Scanner(System.in);
                    String input = scan.nextLine();
                    System.out.println("Length of longest Substring: " + obj.lengthOfLongestSubstring(input));
                    break;
                case 7: //Reverse Integer
                    System.out.println(obj.reverseInteger(123));
                    break;
                case 9: //Is Integer palindrome
                    System.out.println(obj.isIntegerPalindrome(121));
                    break;
                case 13: //Roman to integer
                    System.out.println(obj.romanToInt("IV"));
                    break;
                case 14: //Longest Prefix
                    System.out.println(obj.longestCommonPrefix(new String[]{"Sakshi", "Saku","Sakuntla","Sakshu"}));
                    break;
                case 20: //is valid paranthesis
                    System.out.println(obj.isValidParanthesis("()[]{}"));
                    break;
                case 119: //Pascal's Triangle II
                    System.out.println(obj.getPascalTriangleRow(4));
                    break;
                case 121: //Maximum Stock Profit
                    System.out.println(obj.maxStockProfit(new int[]{7,1,5,3,6,4}));
                    break;
                case 760: //Anagram Mappings
                    System.out.println(Arrays.toString(obj.anagramMappings(new int[]{12, 28, 46, 32, 50}, new int[]{50, 12, 32, 46, 28})));
                    break;
                case 1001: // Find if two strings have commonsubstring
                    String str1 = "abc";
                    String str2 = "worad";
                    if (twoStrings(str1, str2))
                        System.out.print("Yes");
                    else
                        System.out.print("No");
                    break;
                default:
                    break whileloop;
            }
        }
    }
}
