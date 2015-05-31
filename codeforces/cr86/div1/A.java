package codeforces.cr86.div1;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by hama_du on 15/05/31.
 */
public class A {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String[] line = in.nextLine().split(" ");
        List<String> words = new ArrayList<>();
        for (String li : line) {
            words.add(li);
        }

        if (isok(words)) {
            out.println("YES");
        } else {
            out.println("NO");
        }
        out.flush();
    }

    static final String[][] grammer = {
            {"lios", "liala"},
            {"etr", "etra"},
            {"initis", "inites"}
    };

    private static boolean isok(List<String> words) {
        int mf = 0;

        int[] order = new int[words.size()];

        for (int wi = 0 ; wi < words.size() ; wi++) {
            String w = words.get(wi);
            boolean found = false;
            sch: for (int anv = 0 ; anv <= 2 ; anv++) {
                for (int mi = 0 ; mi <= 1 ; mi++) {
                    if (w.endsWith(grammer[anv][mi])) {
                        mf |= 1<<mi;
                        found = true;
                        order[wi] = anv;
                        break sch;
                    }
                }
            }
            if (!found) {
                return false;
            }
        }
        if (mf != 1 && mf != 2) {
            return false;
        }
        if (words.size() == 1) {
            return true;
        }

        int nhead = -1;
        int ntail = -1;
        for (int i = 0 ; i < order.length ; i++) {
            if (order[i] != 0) {
                nhead = i;
                break;
            }
        }
        for (int i = order.length-1 ; i >= 0 ; i--) {
            if (order[i] != 2) {
                ntail = i;
                break;
            }
        }
        if (nhead == -1 || ntail == -1 || order[nhead] != 1 || nhead != ntail) {
            return false;
        }
        return true;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
