package topcoder.srm6xx.srm689.div1;

/**
 * Created by hama_du on 2016/05/09.
 */
public class ColorfulGarden {
    public String[] rearrange(String[] garden) {
        int n = garden[0].length();
        int[] cnt = new int[255];
        for (int i = 0 ; i < 2 ; i++) {
            for (int j = 0; j < n ; j++) {
                cnt[garden[i].charAt(j)]++;
            }
        }
        int max = 0;
        char maxChar = 0;
        for (int i = 'a' ; i <= 'z'; i++) {
            if (cnt[i] > max) {
                max = cnt[i];
                maxChar = (char)i;
            }
        }
        if (max > n) {
            return new String[]{};
        }
        int[][] map = new int[2*n][2];
        for (int i = 0; i < 2*n ; i++) {
            if (i >= n) {
                map[i][0] = 1-map[i-n][0];
                map[i][1] = i-n;
            } else {
                map[i][0] = i%2;
                map[i][1] = i;
            }
        }

        char[][] ret = new char[2][n];
        for (int i = 0; i < max ; i++) {
            ret[i%2][i] = maxChar;
        }
        cnt[maxChar] = 0;

        int idx = max;
        for (char c = 'a' ; c <= 'z' ; c++) {
            while (cnt[c] >= 1) {
                ret[map[idx][0]][map[idx][1]] = c;
                cnt[c]--;
                idx++;
            }
        }
        return new String[]{ String.valueOf(ret[0]), String.valueOf(ret[1]) };
    }
}
