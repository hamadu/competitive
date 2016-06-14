package gcj.gcj2014.round3;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by hama_du on 2016/06/10.
 */
public class C {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int n = in.nextInt();
            int[][] people = new int[n][2];
            for (int i = 0; i < n ; i++) {
                char c = in.next().toCharArray()[0];
                people[i][0] = c == 'E' ? 1 : -1;
                people[i][1] = in.nextInt();
            }
            int res = solve(people);
            if (res == -1) {
                out.println(String.format("Case #%d: CRIME TIME", cs));
            } else {
                out.println(String.format("Case #%d: %d", cs, res));
            }
        }
        out.flush();
    }

    private static int solve(int[][] people) {
        int n = people.length;
        int[] have = new int[4001];
        int[] min = new int[4001];
        int[] max = new int[4001];

        boolean[] makeit = new boolean[n+1];
        int amari = 0;



        for (int i = 0 ; i < n ; i++) {
            int id = people[i][1];
            if (people[i][0] == 1) {
                if (id == 0) {
                    // who?
                    for (int j = i+1 ; j < n ; j++) {
                        int tid = people[j][1];
                        if (people[j][0] == -1 && tid != 0 && have[tid] != max[tid] && !makeit[j]) {
                            makeit[j] = true;
                            id = tid;
                            break;
                        }
                    }
                    if (id == 0) {
                        for (int s = 2001 ; s <= 4000 ; s++) {
                            if (have[s] != max[s] || min[s] == max[s]) {
                                id = s;
                                break;
                            }
                        }
                    }
                }
                have[id]++;
                max[id] = Math.max(max[id], have[id]);
            } else {
                if (id == 0) {
                    // who?
                    for (int j = i+1 ; j < n ; j++) {
                        int tid = people[j][1];
                        if (people[j][0] == 1 && tid != 0 && have[tid] != min[tid] && !makeit[j]) {
                            makeit[j] = true;
                            id = tid;
                            break;
                        }
                    }

                    boolean[] rox = new boolean[2001];
                    for (int j = i+1 ; j < n ; j++) {
                        rox[people[j][1]] = true;
                    }


                    if (id == 0) {
                        for (int s = 1; s <= 2000; s++) {
                            if (!rox[s] && have[s] == max[s] && min[s] != max[s]) {
                                id = s;
                                break;
                            }
                        }
                    }
                    if (id == 0) {
                        for (int s = 2001 ; s <= 4000 ; s++) {
                            if (have[s] != min[s] || min[s] == max[s]) {
                                id = s;
                                break;
                            }
                        }
                    }
                }
                have[id]--;
                min[id] = Math.min(min[id], have[id]);
            }

            if (id == 0 || max[id] - min[id] >= 2) {
                debug(i, id, max[id], min[id]);
                return -1;
            }
        }

        int cnt = 0;
        for (int i = 1 ; i <= 4000; i++) {
            if (have[i] == max[i] && min[i] != max[i]) {
                cnt++;
            }
        }
        return cnt;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
