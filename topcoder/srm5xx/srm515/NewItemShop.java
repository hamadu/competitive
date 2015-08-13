package topcoder.srm5xx.srm515;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by hama_du on 15/08/13.
 */
public class NewItemShop {
    public double getMaximum(int swords, String[] customers) {
        int n = customers.length;

        pid = new int[n];
        Arrays.fill(pid, -1);
        int pn = 0;

        events = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            String cs = customers[i];
            String[] data = cs.split(" ");
            for (String come : data) {
                String[] l = come.split(",");
                int[] r = new int[4];
                r[0] = i;
                for (int j = 1; j <= 3; j++) {
                    r[j] = Integer.valueOf(l[j-1]);
                }
                events.add(new Event(r));
            }
            if (data.length >= 2) {
                pid[i] = pn++;
            }
        }
        Collections.sort(events);
        sn = swords;
        en = events.size();
        memo = new double[en+1][sn+1][1<<pn];
        for (int ei = 0; ei <= en ; ei++) {
            for (int si = 0; si <= sn; si++) {
                Arrays.fill(memo[ei][si], -1);
            }
        }

        rateTbl = new double[en];

        double[] rate = new double[n];
        Arrays.fill(rate, 1.0d);
        for (int ei = 0; ei < en ; ei++) {
            Event e = events.get(ei);
            double baseRate = (e.rate / 100.0d) / rate[e.cid];
            rateTbl[ei] = baseRate;
            rate[e.cid] *= (1.0d - baseRate);
        }
        return dfs(0, sn, 0);
    }

    List<Event> events;
    double[] rateTbl;
    double[][][] memo;
    int en;
    int sn;
    int[] pid;

    double dfs(int ei, int si, int ptn) {
        if (en == ei) {
            return 0;
        }
        if (memo[ei][si][ptn] >= 0) {
            return memo[ei][si][ptn];
        }
        double ret = 0;
        Event e = events.get(ei);
        double comeRate = rateTbl[ei];
        double passRate = 1.0d - comeRate;

        if ((pid[e.cid] >= 0 && (ptn & (1<<pid[e.cid])) >= 1) || si == 0) {
            comeRate = 0.0d;
            passRate = 1.0d;
        }
        if (comeRate > 0) {
            int tptn = pid[e.cid] >= 0 ? (ptn | (1<<pid[e.cid])) : ptn;
            ret += Math.max(dfs(ei+1, si, tptn), (dfs(ei+1, si-1, tptn) + e.price)) * comeRate;
        }
        if (passRate > 0) {
            ret += dfs(ei+1, si, ptn) * passRate;
        }
        memo[ei][si][ptn] = ret;
        return ret;
    }

    static class Event implements Comparable<Event> {
        int cid;
        int time;
        int price;
        int rate;

        Event(int[] a) {
            cid = a[0];
            time = a[1];
            price = a[2];
            rate = a[3];
        }

        @Override
        public int compareTo(Event o) {
            return time - o.time;
        }
    }

    public static void main(String[] args) {
        NewItemShop shop = new NewItemShop();
        debug(shop.getMaximum(1, new String[]{ "8,1,80 16,100,11", "12,10,100" }));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
