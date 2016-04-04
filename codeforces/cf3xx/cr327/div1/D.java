package codeforces.cf3xx.cr327.div1;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by hama_du on 15/10/26.
 */
public class D {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        int s = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }

        // 入力に対して問題を解く
        out.println(solve(a, k, s));

        out.flush();
    }

    private static int solve(int[] a, int k, int s) {
        int n = a.length;

        // s が十分にある場合
        if (s >= n * (n - 1) / 2) {
            // 小さいのから k 個の和が答え
            Arrays.sort(a);
            int ret = 0;
            for (int i = 0; i < k ; i++) {
                ret += a[i];
            }
            return ret;
        }

        // dp[今何番目の数を見ているか][これまでに幾つ数を選んだか][先頭に動かす時のコストはいくつか] := 選んだ数の和はいくつか
        // そのまま (今何番目の数を見ているか) を持つとメモリが足りないので頑張って使い回す
        int[][][] dp = new int[2][k+1][s+1];
        for (int f = 0; f <= 1; f++) {
            for (int j = 0; j <= k; j++) {
                Arrays.fill(dp[f][j], Integer.MAX_VALUE);
            }
        }
        dp[0][0][0] = 0;

        for (int i = 0; i < n ; i++) {
            // fr,to は (0,1) と (1,0) のどれか。
            // dp[fr] := 計算元
            // dp[to] := 計算先
            // i が増える度に入れ替わるようにする
            int fr = i % 2;
            int to = 1 - fr;

            // 計算先を十分大きな値で埋める(こうしとかないと前回(i-2)の計算結果が入ってておかしなことになる)
            for (int j = 0; j <= k; j++) {
                Arrays.fill(dp[to][j], Integer.MAX_VALUE);
            }

            // j : 選んだ数値の数
            // l : 今までの移動回数
            for (int j = 0; j <= k; j++) {
                for (int l = 0; l <= s; l++) {
                    int base = dp[fr][j][l];
                    if (base == Integer.MAX_VALUE) {
                        continue;
                    }

                    // 数値 a[i] を採用する場合。左への移動回数は i-j (何故かは考えてみてください) で、
                    // これが累計 s を超えてなければ選べる
                    if (l+i-j <= s && j+1 <= k) {
                        // 選んだ数値の合計が小さいものを貪欲に選ぶ
                        dp[to][j+1][l+i-j] = Math.min(dp[to][j+1][l+i-j], base+a[i]);
                    }

                    // 数値 a[i] を採用しない場合。採用数、合計移動数、数値の合計共に変わらず
                    dp[to][j][l] = Math.min(dp[to][j][l], base);
                }
            }
        }

        // 答えの中から、一番小さいものを選ぶ
        int min = Integer.MAX_VALUE;
        for (int l = 0; l <= s ; l++) {
            min = Math.min(min, dp[n%2][k][l]);
        }
        return min;
    }
}
