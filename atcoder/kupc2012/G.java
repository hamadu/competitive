package atcoder.kupc2012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by hama_du on 15/08/01.
 */
public class G {
    public static void main(String[] args) throws IOException {
        RAD = (new Random(1000000007)).nextDouble() * Math.PI;

        BufferedReader s = new BufferedReader(new InputStreamReader(System.in));
        String[] line = s.readLine().split(" ");
        N = Integer.valueOf(line[0]);
        R = Double.valueOf(line[1]);
        h = new House[N];
        for (int i = 0; i < N; i++) {
            h[i] = new House(i, s.readLine().split(" "));
        }
        visited = new boolean[N];

        Arrays.sort(h);

        int cnt = 0;
        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                doit(i);
                cnt++;
            }
        }
        System.out.println(cnt);
    }

    public static class House implements Comparable<House> {
        double x;
        double y;
        int id;

        House(int i, String[] line) {
            id = i;
            double xx = Double.valueOf(line[0]);
            double yy = Double.valueOf(line[1]);
            x = xx * Math.cos(RAD) - yy * Math.sin(RAD);
            y = xx * Math.sin(RAD) + yy * Math.cos(RAD);
        }

        @Override
        public int compareTo(House o) {
            return Double.compare(x, o.x);
        }
    }

    static double EPS = 1e-8;
    static int N;
    static double R;
    static boolean[] visited;
    static House[] h;

    static double RAD;

    public static boolean isin(int i, int j) {
        double dx = h[i].x - h[j].x;
        double dy = h[i].y - h[j].y;

        double d2 = dx * dx + dy * dy;
        if (d2 < R * R + EPS) {
            return true;
        }
        return false;
    }

    public static void doit(int i) {
        visited[i] = true;
        for (int j = i + 1; j < N; j++) {
            if (!visited[j]) {
                if (isin(i, j)) {
                    visited[j] = true;
                } else if (Math.abs(h[j].x - h[i].x) > R) {
                    return;
                }
            }
        }
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
