package codeforces.cf3xx.cf386.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Queue;

public class F {
    private static final int INF = (int)2e9+10;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int w = in.nextInt();
        int T = in.nextInt();

        Song[] songs = new Song[n];
        {
            int[] p = in.nextInts(n);
            int[] t = in.nextInts(n);
            for (int i = 0; i < n; i++) {
                songs[i] = new Song(i, p[i], t[i]);
            }
        }

        Queue<Song> fullQ = new PriorityQueue<>((a, b) -> b.time - a.time);
        Queue<Song> halfQ = new PriorityQueue<>((a, b) -> a.time - b.time);

        boolean[] listening = new boolean[n];
        boolean[] isHalf = new boolean[n];

        int max = 0;
        int fr = 0;
        int to = 0;
        int time = 0;
        int half = 0;
        int nowScore = 0;
        while (fr < n) {
            while (to < n) {
                // debug(fr, to, time, half, nowScore);

                Song next = songs[to];
                if (half < w) {
                    if (time + next.half <= T) {
                        time += next.half;
                        nowScore += next.pleasure;
                        listening[to] = isHalf[to] = true;
                        half++;
                        halfQ.add(next);
                    } else {
                        break;
                    }
                } else {
                    Song maxTimeSong = next;
                    while (fullQ.size() >= 1) {
                        Song nowFull = fullQ.peek();
                        if (!listening[nowFull.idx]) {
                            fullQ.poll();
                            continue;
                        }
                        if (maxTimeSong.time < nowFull.time) {
                            maxTimeSong = nowFull;
                        }
                        break;
                    }

                    Song minTimeSong = null;
                    while (halfQ.size() >= 1) {
                        Song nowHalf = halfQ.peek();
                        if (!listening[nowHalf.idx]) {
                            halfQ.poll();
                            continue;
                        }
                        minTimeSong = nowHalf;
                        break;
                    }

                    int toTime2 = time + next.time;
                    int toTime = (minTimeSong != null) ?  time - minTimeSong.half + minTimeSong.time + maxTimeSong.half : INF;
                    if (Math.min(toTime, toTime2) > T) {
                        break;
                    }

                    if (toTime < toTime2) {
                        time = toTime;
                        nowScore += next.pleasure;
                        listening[to] = true;

                        isHalf[minTimeSong.idx] = false;
                        isHalf[maxTimeSong.idx] = true;

                        halfQ.poll(); // minTimeSong
                        if (maxTimeSong == next) {
                            halfQ.add(next);
                            fullQ.add(minTimeSong);
                        } else {
                            fullQ.poll(); // maxTimeSong
                            halfQ.add(maxTimeSong);
                            fullQ.add(next);
                            fullQ.add(minTimeSong);
                        }
                    } else {
                        time = toTime2;
                        nowScore += next.pleasure;
                        listening[to] = true;
                        fullQ.add(next);
                    }
                }
                to++;
            }

            max = Math.max(max, nowScore);

            listening[fr] = false;
            if (isHalf[fr]) {
                time -= songs[fr].half;
                half--;
                while (fullQ.size() >= 1) {
                    Song nowFull = fullQ.peek();
                    if (!listening[nowFull.idx]) {
                        fullQ.poll();
                        continue;
                    }
                    fullQ.poll();
                    halfQ.add(nowFull);
                    isHalf[nowFull.idx] = true;
                    time -= songs[nowFull.idx].time;
                    time += songs[nowFull.idx].half;
                    half++;
                    break;
                }
            } else {
                time -= songs[fr].time;
            }
            isHalf[fr] = false;
            nowScore -= songs[fr].pleasure;
            fr++;
        }


        out.println(max);
        out.flush();
    }

    static class Song {
        int idx;
        int pleasure;
        int time;
        int half;

        public Song(int i, int p, int t) {
            idx = i;
            pleasure = p;
            time = t;
            half = (t+1)/2;
        }
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }

        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
        }

        private int next() {
            if (numChars == -1)
                throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public char nextChar() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            if ('a' <= c && c <= 'z') {
                return (char) c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char) c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char) c);
                c = next();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public int nextInt() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = next();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c-'0';
                c = next();
            } while (!isSpaceChar(c));
            return res*sgn;
        }

        public long nextLong() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            long sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = next();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c-'0';
                c = next();
            } while (!isSpaceChar(c));
            return res*sgn;
        }

        public double nextDouble() {
            return Double.valueOf(nextToken());
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
