package gcj.gcj2017.round2;

import java.io.PrintWriter;
import java.util.*;

public class B {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int n = in.nextInt();
            int c = in.nextInt();
            int m = in.nextInt();
            int[][] tickets = new int[m][2];
            for (int i = 0; i < m ; i++) {
                for (int j = 0; j < 2 ; j++) {
                    tickets[i][j] = in.nextInt()-1;
                }
            }
            int[] pa = solveSub(n, tickets, c);
            out.println(String.format("Case #%d: %d %d", cs, pa[0], pa[1]));
        }
        out.flush();
    }

    private static int[] solveSub(int n, int[][] tickets, int c) {
        int[] cnt = new int[c];
        int[] pos = new int[n];
        for (int i = 0; i < tickets.length ; i++) {
            int p = tickets[i][0];
            int cus = tickets[i][1];
            pos[p]++;
            cnt[cus]++;
        }

        int column = 0;
        for (int i = 0; i < c ; i++) {
            column = Math.max(column, cnt[i]);
        }

        for (int seat = column ; seat <= 1010 ; seat++) {
            int po = solveSub(n, seat, pos);
            if (po == -1) {
                continue;
            }
            return new int[]{seat, po};
        }
        throw new RuntimeException("no");
    }

    private static int solveSub(int n, int maxSeat, int[] posReq) {
        int promoted = 0;
        int leftSeats = 0;
        for (int po = 0 ; po < n ; po++) {
            int sum = posReq[po];
            leftSeats += maxSeat;
            if (sum == 0) {
                continue;
            }
            leftSeats -= Math.min(sum, maxSeat);
            sum -= maxSeat;
            if (sum <= 0) {
                continue;
            }
            if (sum > leftSeats) {
                return -1;
            }
            leftSeats -= sum;
            promoted += sum;
        }
        return promoted;
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



