package topcoder.srm7xx.srm702.div1;

import java.util.*;

public class RepeatedStrings {
    public String findKth(String s, int K) {
        int n = s.length();
        char[] c = s.toCharArray();

        List<String> good = new ArrayList<>();
        if (isOK(c, "()".toCharArray())) {
            good.add("()");
        }

        for (int i = 0 ; i < good.size() ; i++) {
            StringBuilder base = new StringBuilder(good.get(i));
            while (base.length()+2 <= n) {
                String to = "(" + base + ")";
                if (isOK(c, to.toCharArray())) {
                    good.add(to);
                }
                base.append(good.get(i));
            }
        }
        Collections.sort(good);

        --K;
        return K < good.size() ? good.get(K) : "";
    }

    private boolean isOK(char[] s, char[] t) {
        int n = s.length;
        int cnt = 0;
        int m = t.length;
        for (int i = 0; i < n ; i++) {
            if (cnt < m && s[i] == t[cnt]) {
                cnt++;
            }
        }
        return cnt == m;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    static Set<String> good = new HashSet<>();
}
