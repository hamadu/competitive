package topcoder.srm5xx.srm536;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by hama_du on 15/08/24.
 */
public class MergersDivOne {
    public double findMaximum(int[] revenues) {
        int n = revenues.length;
        Arrays.sort(revenues);

        double max = -1e18;
        for (int k = -1 ; k <= n; k++) {
            List<Double> mi = new ArrayList<>();
            List<Double> pl = new ArrayList<>();
            for (int i = 0; i < n ; i++) {
                if (i <= k) {
                    mi.add(revenues[i] * 1.0d);
                } else {
                    pl.add(revenues[i] * 1.0d);
                }
            }
            max = Math.max(max, doit(mi, pl));
        }
        return max;
    }

    private double doit(List<Double> mi, List<Double> pl) {
        if (mi.size() == 0) {
            return doitPlus(pl);
        } else if (pl.size() == 0) {
            return doitMinus(mi);
        } else {
            return (doitPlus(pl) + doitMinus(mi)) / 2.0d;
        }
    }

    private double doitPlus(List<Double> pl) {
        while (pl.size() >= 2) {
            Collections.sort(pl);
            double a = pl.remove(0);
            double b = pl.remove(0);
            pl.add((a+b)/2);
        }
        return pl.get(0);
    }

    private double doitMinus(List<Double> mi) {
        while (mi.size() >= 2) {
            Collections.sort(mi);
            double a = mi.remove(0);
            double b = mi.remove(mi.size()-1);
            mi.add((a+b) / 2);
        }
        return mi.get(0);
    }
}
