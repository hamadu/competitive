package topcoder.srm6xx.srm694.div1;

import java.io.*;
import java.util.*;

public class TrySail {
    public int get(int[] strength) {
        int n = strength.length;

        int[][][][] dp = new int[n+1][256][256][8];
        dp[0][0][0][0] = 1;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 256 ; j++) {
                for (int k = 0; k < 256 ; k++) {
                    for (int l = 0; l < 8 ; l++) {
                        if (dp[i][j][k][l] == 0) {
                            continue;
                        }
                        // first
                        dp[i+1][j^strength[i]][k][l|1] = 1;

                        // second
                        dp[i+1][j][k^strength[i]][l|2] = 1;

                        // third
                        dp[i+1][j][k][l|4] = 1;
                    }
                }
            }
        }

        int all = 0;
        for (int i = 0; i < strength.length; i++) {
            all ^= strength[i];
        }
        int max = 0;
        for (int i = 0; i < 256 ; i++) {
            for (int j = 0; j < 256 ; j++) {
                if (dp[n][i][j][7] == 1) {
                    max = Math.max(max, i + j + (all ^ i ^ j));
                }
            }
        }
        return max;
    }
}
