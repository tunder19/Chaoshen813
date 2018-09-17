package com.example.administrator.chaoshen.util.MathJava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 组合排列工具类
 */
public class CombinationAndPermutation {

    /**
     * 组合选择Cmn (从列表m中选择n个组合)
     *
     * @param dataList 待选列表 (m)
     * @param n        选择个数 (n)
     */
    public static List<String[]> combination(String[] dataList, int n) {
        long total = c(dataList.length, n);
        System.out.println(String.format("C(%d, %d) = %d", dataList.length, n, total));
        List<String[]> result = new ArrayList<>();
        combination(dataList, 0, new String[n], 0, result);
        return result;
    }

    /**
     * 组合选择
     *
     * @param dataList    待选列表
     * @param dataIndex   待选开始索引
     * @param resultList  前面（resultIndex-1）个的组合结果
     * @param resultIndex 选择索引，从0开始
     */
    private static void combination(String[] dataList, int dataIndex, String[] resultList, int resultIndex, List<String[]> result) {
        int resultLen = resultList.length;
        int resultCount = resultIndex + 1;
        if (resultCount > resultLen) { // 全部选择完时，输出组合结果
            String[] arr = new String[resultLen];
            System.arraycopy(resultList, 0, arr, 0, resultLen);
            result.add(arr);
            return;
        }

        // 递归选择下一个
        for (int i = dataIndex; i < dataList.length + resultCount - resultLen; i++) {
            resultList[resultIndex] = dataList[i];
            combination(dataList, i + 1, resultList, resultIndex + 1, result);
        }
    }

    /**
     * 对二维数组进行排列
     *
     * @param arrays 二维数组
     */
    public static List<String[]> permutation(String[]...arrays) {
        List<String[]> result = new ArrayList<>();
        permutation(arrays, result, 0, new String[arrays.length]);
        return result;
    }

    private static void permutation(String[][] arrays, List<String[]> result, int idx, String[] arr) {
        if (idx == arrays.length) {
            result.add(arr.clone());
            return;
        }
        for (String s : arrays[idx]) {
            arr[idx] = s;
            permutation(arrays, result, idx + 1, arr);
        }
    }

    /**
     * 排列选择（从列表中选择n个排列）
     *
     * @param dataList 待选列表
     * @param n        选择个数
     */
    public static void permutation(String[] dataList, int n) {
        System.out.println(String.format("A(%d, %d) = %d", dataList.length, n, permutation(dataList.length, n)));
        permutation(dataList, new String[n], 0);
    }

    /**
     * 排列选择
     *
     * @param dataList    待选列表
     * @param resultList  前面（resultIndex-1）个的排列结果
     * @param resultIndex 选择索引，从0开始
     */
    private static void permutation(String[] dataList, String[] resultList, int resultIndex) {
        int resultLen = resultList.length;
        if (resultIndex >= resultLen) { // 全部选择完时，输出排列结果
            System.out.println(Arrays.asList(resultList));
            return;
        }

        // 递归选择下一个
        for (String d : dataList) {
            // 判断待选项是否存在于排列结果中
            boolean exists = false;
            for (int j = 0; j < resultIndex; j++) {
                if (d.equals(resultList[j])) {
                    exists = true;
                    break;
                }
            }
            if (!exists) { // 排列结果不存在该项，才可选择
                resultList[resultIndex] = d;
                permutation(dataList, resultList, resultIndex + 1);
            }
        }
    }

    /**
     * 计算 C(m,n) 公式 方法二
     * 计算 C(m,n)个数 = (m!)/(n!*(m-n)!)
     * 从M个数中选N个数，函数返回有多少种选法 参数 m 必须大于等于 n m = 0; n = 0; retuan 1;
     */
    public static long c(int m, int n) {
        if (m < n)
            return 0; // 如果总数小于取出的数，直接返回0

        int j = 1, k = 1;
        // 该种算法约掉了分母的(m-n)!,这样分子相乘的个数就是有n个了
        for (int i = n; i >= 1; i--, m--, n--) {
            k *= m;
            j *= n;
        }
        return k / j;
    }

    // 计算 C(m,n) 公式 方法二
    public static int cc(int m, int n) {
        double temp = 1; //temp 为答案
        if (n < m - n) //保证n>=m-n
            return cc(m, m - n);
        for (int i = 0; i < m - n; ++i) {
            temp *= n + i + 1;
            temp /= i + 1;
        }

        return (int) temp;
    }


    /**
     * 计算组合数，即C(m, n) = m!/((m-n)! * n!)
     */
    public static long combination(int m, int n) {
        return (m >= n) ? factorial(m) / factorial(m - n) / factorial(n) : 0;
    }

    /**
     * 计算排列数，即A(m, n) = n!/(n-m)!
     */
    public static long permutation(int m, int n) {
        return (m >= n) ? factorial(m) / factorial(m - n) : 0;
    }

    /**
     * 计算阶乘数，即n! = n * (n-1) * ... * 2 * 1
     */
    public static long factorial(int n) {
        return (n > 1) ? n * factorial(n - 1) : 1;
    }
}
