package topcoder.srm5xx.srm554;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/31.
 */
public class TheBrickTowerHardDivOne {
    private static final int MOD = 1234567891;

    private static final long MOD2 = 1234567891L*1234567891L;

    public int find(int C, int K, long H) {
        generateColorPatterns(0, new int[4]);

        int states = cn * (K+1);
        long[][] mat = new long[states][states];
        for (int ci = 0; ci < cn ; ci++) {
            for (int nc = 0; nc <= 4; nc++) {
                int left = C - colorCount[ci];
                if (left < nc) {
                    continue;
                }
                long colorCombination = 1;
                for (int i = 0; i < nc ; i++) {
                    colorCombination *= (left-i);
                    colorCombination %= MOD;
                }
                int ptnCnt = (int)Math.pow(8, 4);
                for (int tp = 0; tp < ptnCnt ; tp++) {
                    int pp = tp;
                    int[] col = new int[4];
                    for (int i = 0; i < 4 ; i++) {
                        col[i] = pp % 8;
                        pp /= 8;
                    }
                    int newColorCount = 0;
                    boolean[] seen = new boolean[8];
                    for (int i = 0; i < 4 ; i++) {
                        seen[colorPatterns[ci][i]] = true;
                    }
                    boolean isOK = true;
                    for (int i = 0; i < 4 ; i++) {
                        if (!seen[col[i]]) {
                            if (col[i] != colorCount[ci]+newColorCount) {
                                isOK = false;
                            }
                            seen[col[i]] = true;
                            newColorCount++;
                        }
                    }
                    if (newColorCount != nc || !isOK) {
                        continue;
                    }
                    int cj = colorID(normalize(col.clone()));
                    int incK = colorsK[cj];
                    for (int i = 0; i < 4 ; i++) {
                        if (colorPatterns[ci][i] == col[i]) {
                            incK++;
                        }
                    }
                    for (int fk = 0; fk+incK <= K ; fk++) {
                        int fromID = ci*(K+1)+fk;
                        int toID = cj*(K+1)+fk+incK;
                        mat[toID][fromID] += colorCombination;
                        mat[toID][fromID] %= MOD;
                    }
                }
            }
        }

        int[][] A = new int[states+1][states+1];
        for (int i = 0; i < states; i++) {
            for (int j = 0; j < states ; j++) {
                A[i][j] = (int)mat[i][j];
            }
        }
        A[states][states] = 1;

        for (int ci = 0; ci < cn ; ci++) {
            long colorCombination = 1;
            for (int i = 0; i < colorCount[ci] ; i++) {
                colorCombination *= (C-i);
                colorCombination %= MOD;
            }
            if (colorsK[ci] <= K) {
                A[ci*(K+1)+colorsK[ci]][states] = (int)colorCombination;
            }
        }
        A = pow(A, H, MOD);
        long ret = 0;
        for (int j = 0; j < states; j++) {
            ret += A[j][mat.length] % MOD;
        }
        ret %= MOD;
        return (int)ret;
    }

    public static int[][] pow(int[][] a, long n, int mod) {
        long i = 1;
        int[][] res = E(a.length);
        int[][] ap = mul(E(a.length), a, mod);
        while (i <= n) {
            if ((n & i) >= 1) {
                res = mul(res, ap, mod);
            }
            i *= 2;
            ap = mul(ap, ap, mod);
        }
        return res;
    }

    public static int[][] E(int n) {
        int[][] a = new int[n][n];
        for (int i = 0 ; i < n ; i++) {
            a[i][i] = 1;
        }
        return a;
    }


    public static int[][] mul(int[][] a, int[][] b, int mod) {
        int n = a.length;
        int[][] c = new int[n][n];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                long sum = 0;
                for (int k = 0 ; k < n ; k++) {
                    sum += 1L * a[i][k] * b[k][j];
                    while (sum >= MOD2) {
                        sum -= MOD2;
                    }
                }
                c[i][j] = (int)(sum % MOD);
            }
        }
        return c;
    }

    boolean[] seen = new boolean[256];
    int[] colorIdToCN = new int[256];
    int[][] colorPatterns = new int[256][4];
    int[] colorCount = new int[256];
    int[] colorsK = new int[256];
    int cn = 0;

    public int[] normalize(int[] colorPattern) {
        int[] ret = new int[colorPattern.length];
        int[] ctoi = new int[16];
        Arrays.fill(ctoi, -1);
        int cid = 0;
        for (int i = 0; i < colorPattern.length ; i++) {
            if (ctoi[colorPattern[i]] == -1) {
                ctoi[colorPattern[i]] = cid;
                ret[i] = cid;
                cid++;
            } else {
                ret[i] = ctoi[colorPattern[i]];
            }
        }
        return ret;
    }

    public int colorID(int[] colorPattern) {
        int colorId = 0;
        for (int i = 0; i < 4 ; i++) {
            colorId |= colorPattern[i]<<(i*2);
        }
        return colorIdToCN[colorId];
    }

    public void generateColorPatterns(int idx, int[] cptn) {
        if (idx <= 3) {
            for (int a = 0; a <= 3; a++) {
                cptn[idx] = a;
                generateColorPatterns(idx+1, cptn.clone());
            }
            return;
        }

        int[] ctoi = new int[4];
        Arrays.fill(ctoi, -1);
        int cid = 0;
        for (int i = 0; i < 4 ; i++) {
            if (ctoi[cptn[i]] == -1) {
                ctoi[cptn[i]] = cid;
                cptn[i] = cid;
                cid++;
            } else {
                cptn[i] = ctoi[cptn[i]];
            }
        }
        int colorId = 0;
        for (int i = 0; i < 4 ; i++) {
            colorId |= cptn[i]<<(i*2);
        }
        if (seen[colorId]) {
            return;
        }
        int same = 0;
        for (int i = 0; i < 4 ; i++) {
            if (cptn[i] == cptn[(i+1)%4]) {
                same++;
            }
        }
        seen[colorId] = true;
        colorIdToCN[colorId] = cn;
        colorPatterns[cn] = cptn;
        colorCount[cn] = cid;
        colorsK[cn] = same;
        cn++;
    }

    public static void main(String[] args) {
        TheBrickTowerHardDivOne hardDivOne = new TheBrickTowerHardDivOne();
        debug(hardDivOne.find(4, 7, 47));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
