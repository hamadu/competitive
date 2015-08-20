package topcoder.srm5xx.srm529;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/19.
 */
public class KingSort {
    public String[] getSortedList(String[] kings) {
        int n = kings.length;
        King[] king = new King[n];
        for (int j = 0; j < n ; j++) {
            king[j] = new King(kings[j]);
        }
        Arrays.sort(king);
        String[] ret = new String[n];
        for (int i = 0; i < n ; i++) {
            ret[i] = king[i].toString();
        }
        return ret;
    }

    public static class King implements Comparable<King> {
        String name;
        String roman;
        int num;

        King(String line) {
            String[] nn = line.split(" ");
            name = nn[0];
            roman = nn[1];
            num = convert(nn[1]);
        }

        public String toString() {
            return String.format("%s %s", name, roman);
        }

        private static String deco(int num) {
            String z = "";
            String[] part = {"L", "XL", "X", "IX", "V", "IV", "I"};
            int[] pnum    = { 50,   40,  10,    9,   5,    4,   1};
            int idx = 0;
            while (num >= 1) {
                while (num >= pnum[idx]) {
                    num -= pnum[idx];
                    z += part[idx];
                }
                idx++;
            }
            return z;
        }

        private static int convert(String s) {
            for (int v = 1 ; v <= 50; v++) {
                if (s.equals(deco(v))) {
                    return v;
                }
            }
            return -1;
        }

        @Override
        public int compareTo(King o) {
            if (name.equals(o.name)) {
                return num - o.num;
            }
            return name.compareTo(o.name);
        }
    }
}
