package topcoder.srm5xx.srm519;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hama_du on 15/08/16.
 */
public class RequiredSubstrings {
    private static final long MOD = 1000000009;

    public int solve(String[] words, int C, int L) {
        int n = words.length;
        Node root = new Node();
        Node.words = words;
        for (int i = 0; i < words.length ; i++) {
            root.dig(words[i].toCharArray(), 0);
        }
        root.chk();

        int nc = Node.nodes.size();
        long[][][] dp = new long[L+1][1<<n][nc];
        dp[0][0][0] = 1;
        for (int i = 0; i < L ; i++) {
            for (int ptn = 0; ptn < (1<<n) ; ptn++) {
                for (int j = 0; j < nc ; j++) {
                    if (dp[i][ptn][j] == 0) {
                        continue;
                    }
                    Node node = Node.nodes.get(j);
                    long base = dp[i][ptn][j];
                    for (int k = 0; k < 26 ; k++) {
                        int to = node.next[k].id;
                        int tm = ptn | node.next[k].mask;
                        dp[i+1][tm][to] += base;
                        if (dp[i+1][tm][to] >= MOD) {
                            dp[i+1][tm][to] -= MOD;
                        }
                    }
                }
            }
        }

        long sum = 0;
        for (int p = 0; p < 1<<n ; p++) {
            if (Integer.bitCount(p) == C) {
                for (int i = 0; i < nc; i++) {
                    sum += dp[L][p][i];
                    sum %= MOD;
                }
            }
        }
        return (int)sum;
    }

    static class Node {
        static Map<String,Node> nodeMap = new HashMap<>();
        static List<Node> nodes = new ArrayList<>();
        static String[] words;

        int id;
        String str;
        int mask;
        Node[] next;

        Node() {
            str = "";
            init();
        }

        Node(char c, Node parent) {
            str = parent.str + c;
            init();
        }

        void init() {
            next = new Node[26];
            id = nodes.size();
            nodeMap.put(str, this);
            nodes.add(this);
        }

        void dig(char[] c, int idx) {
            if (c.length == idx) {
                return;
            }
            int a = c[idx]-'a';
            if (next[a] == null) {
                next[a] = new Node(c[idx], this);
            }
            next[a].dig(c, idx+1);
        }

        void chk() {
            for (int i = 0; i < 26 ; i++) {
                if (next[i] != null) {
                    next[i].chk();
                    continue;
                }
                String to = str + (char)('a' + i);
                while (!nodeMap.containsKey(to)) {
                    to = to.substring(1);
                }
                next[i] = nodeMap.get(to);
            }

            for (int i = 0; i < words.length ; i++) {
                if (str.contains(words[i])) {
                    mask |= 1<<i;
                }
            }
        }
    }
}
