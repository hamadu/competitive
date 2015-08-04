package topcoder.srm5xx.srm504;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/04.
 */
public class AlgridTwo {
    static long MOD = 1000000007;

    public int makeProgram(String[] output) {
        int h = output.length;
        int w = output[0].length();
        long ptn = 1;
        char[][] out = new char[h][w];
        for (int i = 0; i < h ; i++) {
            out[i] = output[i].toCharArray();
        }

        char[][] input = new char[h][w];
        input[0] = out[0].clone();
        for (int i = 0; i < h-1 ; i++) {
            Arrays.fill(input[i+1], '?');
            for (int j = 0; j < w-1 ; j++) {
                char c1 = input[i][j];
                char c2 = input[i][j+1];
                if (c1 == 'W' && c2 == 'W') {
                    //
                } else if (c1 == 'B' && c2 == 'W') {
                    input[i+1][j] = input[i+1][j+1] = 'B';
                } else if (c1 == 'W' && c2 == 'B') {
                    input[i+1][j] = input[i+1][j+1] = 'W';
                } else {
                    char tmp = input[i+1][j];
                    input[i+1][j] = input[i+1][j+1];
                    input[i+1][j+1] = tmp;
                }
            }

            // check
            for (int j = 0; j < w ; j++) {
                if (input[i+1][j] != '?' && input[i+1][j] != out[i+1][j]) {
                    return 0;
                }
            }

            // multiply
            for (int j = 0; j < w ; j++) {
                if (input[i+1][j] != '?') {
                    ptn *= 2;
                    ptn %= MOD;
                }
            }
            input[i+1] = out[i+1].clone();
        }
        return (int)ptn;
    }
}
