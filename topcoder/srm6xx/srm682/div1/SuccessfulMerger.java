package topcoder.srm6xx.srm682.div1;

/**
 * Created by hama_du on 2016/05/15.
 */
public class SuccessfulMerger {
    public int minimumMergers(int[] road) {
        int n = road.length;
        int[] deg = new int[n];
        for (int i = 0; i < n ; i++) {
            deg[i]++;
            deg[road[i]]++;
        }
        int head = 0;
        for (int i = 0; i < n ; i++) {
            head = road[head];
        }

        // head is in cycle! (It's a functional graph)
        boolean[] isCircle = new boolean[n];
        for (int i = 0; i < n ; i++) {
            isCircle[head] = true;
            head = road[head];
        }

        int cities = 1;
        boolean shouldLeft = false;
        for (int i = 0; i < n ; i++) {
            if (deg[i] == 1) {
                cities++;
            }
            if (isCircle[i] && deg[i] == 2) {
                shouldLeft = true;
            }
        }
        cities += shouldLeft ? 1 : 0;
        return n - cities;
    }
}
