package com.example.administrator.chaoshen.util.MathJava;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 胜负彩/任九彩 相关工具类
 *
 * @author backflow
 */
public class WinloseUtil {

   public static String separator = "\\|";

    public static void main(String[] args) {
        String bet = "3|3|3|3|3|3|3|3|3|3|3|3|3|3";

        String[] bets = bet.split(separator, -1);

        System.out.println(bets(bets));

        List<String[]> combinations = combinations(bets);
        int sum = bets(combinations);
        System.out.println(combinations.size() + "场, " + sum + "注");
    }

    /**
     * 计算总注数
     *
     * @param bets        14场投注内容数组
     * @param combination 是否拆分成任9组合计算
     * @return 总注数
     */
    public static int bets(String[] bets, boolean combination) {
        return combination ?
                bets(combinations(bets)) :
                bets(bets);
    }


    /**
     * 任9彩: 计算投注的所有组合方式
     */
    private static List<String[]> combinations(String[] bets) {
        bets = filterEmpty(bets);
        List<String[]> combination = new ArrayList<>();
        for (String[] strings : CombinationAndPermutation.combination(bets, 9)) {
            combination.add(strings);
        }
        return combination;
    }

    private static String[] filterEmpty(String[] bets) {
        List<String> list = new ArrayList<>();
        for (String bet : bets) {
            if (!bet.isEmpty()) {
                list.add(bet);
            }
        }
        return list.toArray(new String[0]);
    }

    /**
     * 计算多种组合总注数
     */
    private static int bets(List<String[]> combinations) {
        int sum = 0;
        for (String[] combination : combinations) {
            sum += bets(combination);
        }
        return sum;
    }

    /**
     * 计算单个组合注数
     */
    public static int bets(String[] bets) {
        int sum = 1;
        for (String bet : bets) {
            if (bet.isEmpty()) {
                continue;
            }
            sum *= bet.length();
        }
        return sum;
    }

    static class CartesianProduct implements Iterable<int[]>, Iterator<int[]> {
        private final int[] indices;
        private final int[] lengths;
        private boolean next = true;

        CartesianProduct(Object[][] objects) {
            lengths = new int[objects.length];
            for (int i = 0; i < objects.length; i++) {
                lengths[i] = objects[i].length;
            }
            indices = new int[lengths.length];
        }

        public boolean hasNext() {
            return next;
        }

        public int[] next() {
            int[] result = Arrays.copyOf(indices, indices.length);
            for (int i = indices.length - 1; i >= 0; i--) {
                if (indices[i] == lengths[i] - 1) {
                    indices[i] = 0;
                    if (i == 0) {
                        next = false;
                    }
                } else {
                    indices[i]++;
                    break;
                }
            }
            return result;
        }

        public Iterator<int[]> iterator() {
            return this;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
