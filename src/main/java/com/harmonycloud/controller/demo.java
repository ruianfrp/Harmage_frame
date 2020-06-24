package com.harmonycloud.controller;

public class demo {
    public static int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[128]; // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            System.out.println(s.charAt(j));
            i = Math.max(index[s.charAt(j)], i);
            System.out.println(i);
            ans = Math.max(ans, j - i + 1);
            System.out.println(ans);
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        demo.lengthOfLongestSubstring("abcbabb");
    }
}
