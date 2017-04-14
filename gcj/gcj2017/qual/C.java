package gcj.gcj2017.qual;

import java.io.PrintWriter;
import java.util.*;

public class C {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            long n = in.nextLong();
            long k = in.nextLong();
            out.println(String.format("Case #%d: %s", cs, solve(n, k)));
        }
        out.flush();
    }

    private static String solve(long n, long k) {
        Queue<Space> q = new PriorityQueue<>((a, b) -> Long.compare(b.size, a.size));
        q.add(new Space(n, 1));

        long finalSize = 0;
        while (q.size() >= 1) {
            Space sp = q.poll();
            if (q.size() >= 1 && q.peek().size == sp.size) {
                q.peek().count += sp.count;
                continue;
            }

            k -= sp.count;
            if (k <= 0) {
                finalSize = sp.size;
                break;
            }

            if (sp.size == 1) {
                // finished;
                continue;
            } else if (sp.size == 2) {
                q.add(new Space(1, sp.count));
                continue;
            }

            if (sp.size % 2 == 1) {
                long newsize = sp.size / 2;
                q.add(new Space(newsize, sp.count * 2));
            } else {
                long R = sp.size / 2;
                long L = sp.size - R - 1;
                q.add(new Space(L, sp.count));
                q.add(new Space(R, sp.count));
            }
        }

        return String.format("%d %d", finalSize / 2, finalSize - finalSize / 2 - 1);
    }

    static class Space {
        long size;
        long count;

        public Space(long size, long count) {
            this.size = size;
            this.count = count;
        }
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
