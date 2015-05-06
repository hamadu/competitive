package atcoder.gw2015;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dhamada on 15/05/07.
 */
public class A {
    public static Set<Integer> scoreSet = new HashSet<>();

    public static void dfs(int idx, int sum, int[][] scores) {
        if (idx == scores.length) {
            scoreSet.add(sum);
            return;
        }

        for (int s : scores[idx]) {
            dfs(idx+1, sum+s, scores);
        }
    }

    public static void main(String[] args) {
        int[][] scores = {
                {0, 25},
                {0, 39},
                {0, 51},
                {0, 76},
                {0, 163},
                {0, 111},
                {0, 58, 136},
                {0, 128},
                {0, 133},
                {0, 138}
        };
        dfs(0, 0, scores);

        Integer[] arr = scoreSet.toArray(new Integer[0]);
        Arrays.sort(arr);
        for (int a : arr) {
            System.out.println(a);
        }
    }
}
