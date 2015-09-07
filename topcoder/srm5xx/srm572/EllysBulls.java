package topcoder.srm5xx.srm572;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by hama_du on 15/09/05.
 */
public class EllysBulls {
    public String getNumber(String[] guesses, int[] bulls) {
        int n = guesses.length;

        int L = guesses[0].length();
        int leftL = L/2;
        int rightL = L - L/2;

        BigInteger wantAnswer = BigInteger.ZERO;
        for (int i = 0; i < n ; i++) {
            wantAnswer = wantAnswer.multiply(BigInteger.TEN);
            wantAnswer = wantAnswer.add(BigInteger.valueOf(bulls[i]));
        }

        String ans = "";
        Map<BigInteger,Integer> leftPart = doit(guesses, bulls, 0, L/2);
        for (int r = 0; r < (int)Math.pow(10, rightL); r++) {
            BigInteger right = compute(r, guesses, bulls, L/2, L);
            if (right == null) {
                continue;
            }
            BigInteger needLeft = wantAnswer.subtract(right);
            if (leftPart.containsKey(needLeft)) {
                int l = leftPart.get(needLeft);
                if (l == -1 || !ans.equals("")) {
                    return "Ambiguity";
                } else  {
                    String fmt = "%0" + leftL + "d" + "%0" + rightL + "d";
                    ans = String.format(fmt, l, r);
                }
            }
        }
        if (ans.equals("")) {
            return "Liar";
        }
        return ans;
    }

    private Map<BigInteger,Integer> doit(String[] guesses, int[] bulls, int fr, int to) {
        int limit = (int)Math.pow(10, to-fr);
        Map<BigInteger,Integer> map = new HashMap<>();
        for (int f = 0; f < limit ; f++) {
            BigInteger ret = compute(f, guesses, bulls, fr, to);
            if (ret != null) {
                if (map.containsKey(ret)) {
                    map.put(ret, -1);
                } else {
                    map.put(ret, f);
                }
            }
        }
        return map;
    }


    private BigInteger compute(int num, String[] guesses, int[] bulls, int fr, int to) {
        int[] idx = new int[to-fr];
        for (int i = 0; i < idx.length; i++) {
            idx[idx.length-1-i] = num % 10;
            num /= 10;
        }
        int n = guesses.length;
        int[] cnt = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = fr; j < to; j++) {
                if (guesses[i].charAt(j)-'0' == idx[j-fr]) {
                    cnt[i]++;
                }
            }
            if (cnt[i] > bulls[i]) {
                return null;
            }
        }
        BigInteger ret = BigInteger.ZERO;
        for (int i = 0; i < n; i++) {
            ret = ret.multiply(BigInteger.TEN);
            ret = ret.add(BigInteger.valueOf(cnt[i]));
        }
        return ret;
    }
}
