package aoj.vol13;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by hama_du on 15/07/21.
 */
public class P1317 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            in.nextLine();
            char[][] words = new char[n][];
            for (int i = 0; i < n ; i++) {
                words[i] = in.nextLine().toCharArray();
            }
            String message = in.nextLine();
            String[] crypt = message.substring(0, message.length()-1).split(" ");
            char[][] msg = new char[crypt.length][];
            for (int i = 0; i < msg.length ; i++) {
                msg[i] = crypt[i].toCharArray();
            }
            out.println(solve(words, msg));

        }

        out.flush();
    }

    private static String solve(char[][] W, char[][] M) {
        words = W;
        message = M;
        n = words.length;
        m = message.length;

        matches = new boolean[n][m];
        converter = new int[n][m][26];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (words[i].length != message[j].length) {
                    continue;
                }
                Arrays.fill(converter[i][j], -1);

                boolean isOK = true;
                int[] cvr = converter[i][j];
                int wl = words[i].length;
                for (int k = 0; k < wl ; k++) {
                    int c1 = words[i][k] - 'A';
                    int c2 = message[j][k] - 'A';
                    if (cvr[c1] != c2 && cvr[c1] != -1) {
                        isOK = false;
                        break;
                    }
                    if (cvr[c2] != c1 && cvr[c2] != -1) {
                        isOK = false;
                        break;
                    }
                    cvr[c1] = c2;
                    cvr[c2] = c1;
                }
                if (isOK) {
                    matches[i][j] = true;
                }
            }
        }

        msgOrder = new int[m][2];
        for (int i = 0; i < m ; i++) {
            int mcnt = 0;
            for (int j = 0; j < n ; j++) {
                if (matches[j][i]) {
                    mcnt++;
                }
            }
            msgOrder[i][0] = i;
            msgOrder[i][1] = mcnt * 1000;
            int[] us = new int[26];
            for (int j = 0; j < message[i].length ; j++) {
                int c = message[i][j]-'A';
                if (us[c] == 0) {
                    msgOrder[i][1]--;
                    us[c] = 1;
                }
            }
        }
        Arrays.sort(msgOrder, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });

        int[] r = new int[26];
        Arrays.fill(r, -1);

        _cnt = 0;
        try {
            dfs(0, m, r);
        } catch (Exception e) {
            return "-.";
        }

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < m ; i++) {
            line.append(' ');
            for (char c : message[i]) {
                int nu = onlyRule[c-'A'];
                line.append((char)('A'+nu));
            }
        }
        line.append('.');
        return line.substring(1);
    }

    static int[][] msgOrder;

    static boolean[][] matches;
    static int[][][] converter;

    static char[][] words;
    static char[][] message;
    static int[] onlyRule;


    static int n;
    static int m;

    static int _cnt;

    static void dfs(int mi, int max, int[] rule) {
        if (mi == max) {
            onlyRule = rule.clone();
            _cnt++;
            if (_cnt >= 2) {
                throw new RuntimeException();
            }
            return;
        }
        int idx = msgOrder[mi][0];
        for (int i = 0; i < n ; i++) {
            if (!matches[i][idx]) {
                continue;
            }
            boolean isOK = true;
            int[] trule = rule.clone();
            for (int k = 0; k < 26 ; k++) {
                if (converter[i][idx][k] != -1) {
                    if (converter[i][idx][k] != trule[k] && trule[k] != -1) {
                        isOK = false;
                        break;
                    }
                    trule[k] = converter[i][idx][k];
                }
            }
            if (isOK) {
                dfs(mi + 1, max, trule);
            }
        }
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
