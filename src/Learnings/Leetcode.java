package Learnings;

import java.io.IOException;
import java.util.*;

public class Leetcode {

    /*
    3. Longest Substring Without Repeating Characters
     */
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
        return longestCommonPrefix(strs, 0 , strs.length - 1);
    }

    private String longestCommonPrefix(String[] strs, int l, int r) {
        if (l == r) {
            return strs[l];
        }
        else {
            int mid = (l + r)/2;
            String lcpLeft =   longestCommonPrefix(strs, l , mid);
            String lcpRight =  longestCommonPrefix(strs, mid + 1,r);
            return commonPrefix(lcpLeft, lcpRight);
        }
    }

    public String longestCommonPrefix1(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        int minLen = Integer.MAX_VALUE;
        for (String str : strs)
            minLen = Math.min(minLen, str.length());
        int low = 1;
        int high = minLen;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (isCommonPrefix(strs, middle))
                low = middle + 1;
            else
                high = middle - 1;
        }
        return strs[0].substring(0, (low + high) / 2);
    }

    private boolean isCommonPrefix(String[] strs, int len){
        String str1 = strs[0].substring(0,len);
        for (int i = 1; i < strs.length; i++)
            if (!strs[i].startsWith(str1))
                return false;
        return true;
    }

    private String commonPrefix(String left,String right) {
        int min = Math.min(left.length(), right.length());
        for (int i = 0; i < min; i++) {
            if ( left.charAt(i) != right.charAt(i) )
                return left.substring(0, i);
        }
        return left.substring(0, min);
    }

    public static void main(String[] args) throws IOException {
        Leetcode obj = new Leetcode();
        Scanner in = new Scanner(System.in);
        whileloop: while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 3:
                    System.out.println("Enter string to calulate Longest Substring Without Repeating Characters");
                    Scanner scan = new Scanner(System.in);
                    String input = scan.nextLine();
                    System.out.println("Length of longest Substring: " + obj.lengthOfLongestSubstring(input));
                    break;
                case 1001:
                    String str1 = "abc";
                    String str2 = "worad";
                    if (twoStrings(str1, str2))
                        System.out.print("Yes");
                    else
                        System.out.print("No");
                    break;
                case 14:
                    System.out.println(obj.longestCommonPrefix1(new String[]{"Sakshi", "Saku","Sakuntla","Sakshu"}));
                    break ;
                default:
                    break whileloop;
            }
        }
    }
}
