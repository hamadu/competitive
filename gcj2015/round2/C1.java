package gcj2015.round2;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class C1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int n = in.nextInt();
            in.nextLine();
            String[][] sen = new String[n][];
            for (int i = 0; i < n ; i++) {
                sen[i] = in.nextLine().split(" ");
            }
            out.println(String.format("Case #%d: %d", cs, solve(sen)));
        }
        out.flush();
    }

    private static int solve(String[][] sen) {
        int n = sen.length;
        int min = Integer.MAX_VALUE;
        for (int ptn = 0 ; ptn < 1<<(n-2) ; ptn++) {
            Set<String> eng = new HashSet<>();
            Set<String> fra = new HashSet<>();
            int ef = (ptn<<2)|1;
            for (int i = 0; i < n ; i++) {
                if ((ef & (1<<i)) >= 1) {
                    for (String s : sen[i]) {
                        eng.add(s);
                    }
                } else {
                    for (String s : sen[i]) {
                        fra.add(s);
                    }
                }
            }
            if (eng.size() < fra.size()) {
                eng.retainAll(fra);
                min = Math.min(min, eng.size());
            } else {
                fra.retainAll(eng);
                min = Math.min(min, fra.size());
            }
        }
        return min;
    }

    static class UnionFind {
        int[] parent, rank;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0 ; i < n ; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int x) {
            if (parent[x] == x) {
                return x;
            }
            parent[x] = find(parent[x]);
            return parent[x];
        }

        void unite(int x, int y) {
            x = find(x);
            y = find(y);
            if (x == y) {
                return;
            }
            if (rank[x] < rank[y]) {
                parent[x] = y;
            } else {
                parent[y] = x;
                if (rank[x] == rank[y]) {
                    rank[x]++;
                }
            }
        }
        boolean issame(int x, int y) {
            return (find(x) == find(y));
        }
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



