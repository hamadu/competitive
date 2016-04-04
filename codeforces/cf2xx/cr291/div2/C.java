package codeforces.cf2xx.cr291.div2;

import java.io.*;
import java.util.*;

public class C {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        String[] nm = in.readLine().split(" ");
        int n = Integer.valueOf(nm[0]);
        int m = Integer.valueOf(nm[1]);
        char[][] c = new char[n][];
        int sum = 0;
        for (int i = 0 ; i < n ; i++) {
            c[i] = in.readLine().toCharArray();
            sum += c[i].length;
        }
        sum += 3;

        int[] trieDepth = new int[sum];
        boolean[] trieFinal = new boolean[sum];
        int[][] trieNext = new int[3][sum];
        for (int ci = 0 ; ci <= 2 ; ci++) {
            Arrays.fill(trieNext[ci], -1);
        }

        int nodeID = 1;
        for (int i = 0 ; i < n ; i++) {
            int cid = 0;
            int cl = c[i].length;
            for (int j = 0 ; j < cl ; j++) {
                int cv = c[i][j] - 'a';
                int tv = trieNext[cv][cid];
                if (tv == -1) {
                    trieDepth[nodeID] = trieDepth[cid] + 1;
                    trieNext[cv][cid] = nodeID;
                    if (j == cl - 1) {
                        trieFinal[nodeID] = true;
                    }
                    cid = nodeID;
                    nodeID++;
                } else {
                    cid = tv;
                }
            }
        }

        // debug(trieNext);

        int[][] dp = new int[2][sum];
        for (int i = 0 ; i <= 1 ; i++) {
            Arrays.fill(dp[i], -1);
        }
        int[] q = new int[6*sum];
        int qh = 0;
        int qt = 0;
        for (int qi = 0 ; qi < m ; qi++) {
            char[] query = in.readLine().toCharArray();
            int qn = query.length;

            dp[0][0] = qi;

            qh = qt = 0;
            q[qh++] = 0;
            q[qh++] = 0;

            boolean found = false;
            while (qt < qh) {
                int node = q[qt++];
                int cng = q[qt++];
                int depth = trieDepth[node];
                if (depth == qn) {
                    if (cng == 1 && trieFinal[node]) {
                        found = true;
                        break;
                    }
                    continue;
                }

                int x = query[depth] - 'a';
                for (int t = 0 ; t <= 2 ; t++) {
                    if (trieNext[t][node] >= 0) {
                        int next = trieNext[t][node];
                        int tcng = cng;
                        if (t != x) {
                            tcng++;
                        }
                        if (next >= 0 && tcng <= 1 && dp[tcng][next] < qi) {
                            dp[tcng][next] = qi;
                            q[qh++] = next;
                            q[qh++] = tcng;
                        }
                    }
                }
            }
            if (found) {
                out.println("YES");
            } else {
                out.println("NO");
            }
        }
        out.flush();
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



