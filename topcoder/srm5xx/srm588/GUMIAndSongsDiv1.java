package topcoder.srm5xx.srm588;

import java.util.*;

/**
 * Created by hama_du on 15/09/26.
 */
public class GUMIAndSongsDiv1 {
    public int maxSongs(int[] duration, int[] tone, int T) {
        int max = 0;
        int n = duration.length;
        int[][] songs = new int[n][2];
        for (int i = 0; i < n ; i++) {
            songs[i][0] = duration[i];
            songs[i][1] = tone[i];
        }
        Arrays.sort(songs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });

        for (int l = 0; l < n ; l++) {
            for (int r = l ; r < n ; r++) {
                int total = songs[r][1] - songs[l][1];
                int cnt = 0;
                if (l != r) {
                    total += songs[l][0] + songs[r][0];
                    cnt += 2;
                } else {
                    total += songs[l][0];
                    cnt += 1;
                }
                if (total <= T) {
                    List<Integer> betweenSongs = new ArrayList<>();
                    for (int c = l+1 ; c <= r-1 ; c++) {
                        betweenSongs.add(songs[c][0]);
                    }
                    Collections.sort(betweenSongs);
                    for (int a = 0; a < betweenSongs.size() ; a++) {
                        total += betweenSongs.get(a);
                        if (total > T) {
                            break;
                        }
                        cnt++;
                    }
                    max = Math.max(max, cnt);
                }
            }
        }
        return max;
    }
}
