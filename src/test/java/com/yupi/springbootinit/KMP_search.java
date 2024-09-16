package com.yupi.springbootinit;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2024/09/16/10:04
 * @Description: kmp 字符串匹配算法
 */
public class KMP_search {

    //Todo
    public static int lengthOfLastWord(String s) {
        int i = s.length() - 1;
        while(s.charAt(i) == ' ') {
            i--;
        }
        int j = i - 1;
        while(j >= 0 && s.charAt(j) != ' ') {
            j--;
        }
        return i - j;
    }

    // 求解 next 数组
    public static int[] computeNextArray(String pattern) {
        int m = pattern.length();
        int[] next = new int[m];
        next[0] = 0;  // next[0] 默认为 0，因为第一个字符没有前缀和后缀

        int j = 0;  // j 表示前缀的长度
        for (int i = 1; i < m; i++) {
            // 当前缀不匹配时，尝试向前回溯
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];  // 回溯到前一个最长前缀的位置
            }

            // 如果前缀和后缀匹配，前缀长度 +1
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }

            // 将 j 赋值给 next[i]
            next[i] = j;
        }
        return next;
    }

    // KMP 算法的主函数，比较主串与子串
    public static int KMPMatch(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        int[] next = computeNextArray(pattern);  // 计算 next 数组

        int j = 0;  // j 表示当前匹配的模式串的位置
        for (int i = 0; i < n; i++) {
            // 当前字符不匹配时，利用 next 数组跳转
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];  // 回溯到前一个可能的匹配位置
            }

            // 如果字符匹配
            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
            }

            // 如果找到一个完整的匹配
            if (j == m) {
                return i - m + 1;  // 返回匹配开始的索引
            }
        }

        // 未找到匹配，返回 -1
        return -1;
    }

    public static void main(String[] args) {
//        String text = "ababcabcacbab";
//        String pattern = "abcac";
//
//        // 调用 KMP 算法匹配
//        int result = KMPMatch(text, pattern);
//
//        // 输出匹配结果
//        if (result != -1) {
//            System.out.println("Pattern found at index: " + result);
//        } else {
//            System.out.println("Pattern not found");
//        }

        //Todo
        String str = "Hello World";
        int i = lengthOfLastWord(str);
        System.out.println(i);
    }


}
