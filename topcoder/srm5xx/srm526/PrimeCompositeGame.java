package topcoder.srm5xx.srm526;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * Created by hama_du on 15/08/18.
 */
public class PrimeCompositeGame {
    public int theOutcome(int N, int K) {
        boolean[] isp = generatePrimes(1000000);
        isp[0] = isp[1] = false;
        boolean[] isc = new boolean[isp.length];
        for (int i = 0 ; i < isp.length ; i++) {
            isc[i] = !isp[i];
        }
        isc[0] = isc[1] = false;

        int[] dp0 = new int[N+1];
        int[] dp1 = new int[N+1];

        TreeSet<GameState> compositeAndYouWin = new TreeSet<>();
        TreeSet<GameState> compositeAndYouLose = new TreeSet<>();
        TreeSet<GameState> primeAndDengklekWin = new TreeSet<>();
        TreeSet<GameState> primeAndDengklekLose = new TreeSet<>();
        for (int i = 1 ; i <= N; i++) {
            // you
            if (primeAndDengklekLose.size() >= 1) {
                // win
                dp0[i] = primeAndDengklekLose.first().turn+1;
            } else if (primeAndDengklekWin.size() >= 1) {
                // lose
                dp0[i] = -(primeAndDengklekWin.last().turn+1);
            } else {
                // there is not valid move : lose
            }
            if (isc[i]) {
                if (dp0[i] >= 1) {
                    compositeAndYouWin.add(new GameState(dp0[i], i));
                } else {
                    compositeAndYouLose.add(new GameState(-dp0[i], i));
                }
            }

            // dengklek
            if (compositeAndYouLose.size() >= 1) {
                // win
                dp1[i] = compositeAndYouLose.first().turn+1;
            } else if (compositeAndYouWin.size() >= 1) {
                // lose
                dp1[i] = -(compositeAndYouWin.last().turn+1);
            } else {
                // there is not valid move : lose
            }
            if (isp[i]) {
                if (dp1[i] >= 1) {
                    primeAndDengklekWin.add(new GameState(dp1[i], i));
                } else {
                    primeAndDengklekLose.add(new GameState(-dp1[i], i));
                }
            }

            if (i >= K) {
                if (isc[i-K]) {
                    if (dp0[i-K] <= 0) {
                        compositeAndYouLose.remove(new GameState(-dp0[i-K], i-K));
                    } else {
                        compositeAndYouWin.remove(new GameState(dp0[i-K], i-K));
                    }
                } else {
                    if (dp1[i-K] <= 0) {
                        primeAndDengklekLose.remove(new GameState(-dp1[i-K], i-K));
                    } else {
                        primeAndDengklekWin.remove(new GameState(dp1[i-K], i-K));
                    }
                }
            }
        }
        return dp0[N];
    }

    static class GameState implements Comparable<GameState> {
        int turn;
        int number;

        GameState(int t, int n) {
            turn = t;
            number = n;
        }

        @Override
        public int compareTo(GameState o) {
            if (turn != o.turn) {
                return turn - o.turn;
            }
            return number - o.number;
        }

        public String toString() {
            return String.format("%d/%d", turn, number);
        }
    }

    static boolean[] generatePrimes(int upto) {
        boolean[] isp = new boolean[upto];
        Arrays.fill(isp, true);
        isp[0] = isp[1] = false;

        int pi = 0;
        for (int i = 2; i < upto ; i++) {
            if (isp[i]) {
                pi++;
                for (int j = i * 2; j < upto; j += i) {
                    isp[j] = false;
                }
            }
        }
        return isp;
    }

    public static void main(String[] args) {
        PrimeCompositeGame game = new PrimeCompositeGame();
        debug(game.theOutcome(58, 1));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
