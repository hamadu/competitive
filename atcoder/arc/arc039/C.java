package atcoder.arc.arc039;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/05/16.
 */
public class C {
    static final int SZ = 640;

    static int[] dx = new int[256];
    static int[] dy = new int[256];

    static void tr(int n) {
        Random rnd =new Random(100007);
        char[] m = new char[]{ 'L', 'R', 'D', 'U' };
        System.out.println(n);
        for (int i = 0 ; i < n ; i++) {
            System.out.print(m[i%2+2]);
        }
        System.out.println();
    }

    static class Area {
        static Map<Long,Area> areaMap = new HashMap<>();

        long id;
        int bx;
        int by;
        int[] nx;
        int[] ny;
        boolean[][] visited;

        public Area(int x, int y) {
            id = id(x, y);
            bx = x;
            by = y;
            nx = new int[SZ];
            ny = new int[SZ];
            visited = new boolean[SZ][SZ];

            areaMap.put(id, this);
        }

        public void stamp(int lx, int ly) {
            if (!visited[lx][ly]) {
                visited[lx][ly] = true;
                ny[ly]++;
                nx[lx]++;
            }
        }


        static int[] move(int x, int y, char d) {
            x += dx[d];
            y += dy[d];
            while (true) {
                int bx = base(x);
                int by = base(y);
                long id = id(bx, by);
                Area area = null;
                if (!areaMap.containsKey(id)) {
                    area = new Area(bx, by);
                } else {
                    area = areaMap.get(id);
                }
                int lx = x - bx;
                int ly = y - by;
                if (!area.visited[lx][ly]) {
                    area.stamp(lx, ly);
                    return new int[]{ x, y };
                }

                boolean localMove = true;
                if (d == 'L' || d == 'R') {
                    if (area.ny[ly] == SZ) {
                        localMove = false;
                    }
                } else {
                    if (area.nx[lx] == SZ) {
                        localMove = false;
                    }
                }
                if (localMove) {
                    while (true) {
                        x += dx[d];
                        y += dy[d];
                        lx += dx[d];
                        ly += dy[d];
                        if (bx <= x && x < bx+SZ && by <= y && y < by+SZ) {
                            if (!area.visited[lx][ly]) {
                                area.stamp(lx, ly);
                                return new int[]{x, y};
                            }
                        } else {
                            break;
                        }
                    }
                } else {
                    if (d == 'L') {
                        x = bx-1;
                    } else if (d == 'R') {
                        x = bx+SZ;
                    } else if (d == 'U') {
                        y = by+SZ;
                    } else if (d == 'D') {
                        y = by-1;
                    }
                }
            }
        }

        static long id(long bx, long by) {
            return bx*(1L<<20)+by;
        }

        static int base(int x) {
            if (x >= 0) {
                int d = x / SZ;
                return d * SZ;
            } else {
                return (((x + 1) / SZ) - 1) * SZ;
            }
        }
    }

    public static void main(String[] args) {
        // tr(200000);

        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        dx['R'] = 1;
        dx['L'] = -1;
        dy['U'] = 1;
        dy['D'] = -1;

        int k = in.nextInt();
        char[] c = in.nextToken().toCharArray();

        long x = System.currentTimeMillis();
        // debug("ok");

        Area firstArea = new Area(0, 0);
        firstArea.stamp(0, 0);

        int nx = 0;
        int ny = 0;
        int px = -1;
        int py = -1;
        char pd = ' ';
        for (char ci : c) {
            if ((ci == 'L' && pd =='R') || (ci == 'R' && pd =='L') || (ci == 'U' && pd =='D') || (ci == 'D' && pd =='U')) {
                nx = px;
                ny = py;
            }
            int[] r = Area.move(nx, ny, ci);
            px = nx;
            py = ny;
            pd = ci;
            nx = r[0];
            ny = r[1];
        }
        out.println(nx + " " + ny);
        out.flush();

        // debug(System.currentTimeMillis() - x);
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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
                res += c - '0';
                c = next();
            } while (!isSpaceChar(c));
            return res * sgn;
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
                res += c - '0';
                c = next();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }
}
