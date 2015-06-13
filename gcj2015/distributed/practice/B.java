package gcj2015.distributed.practice;

import java.util.Arrays;

/**
 * Created by hama_du on 15/06/12.
 */
public class B {
    public static void main(String[] args) {
        long N = sandwich.GetN();
        int nodes = message.NumberOfNodes();
        int myid = message.MyNodeId();
        if (N <= 10) {
            if (myid == 0) {
                System.out.println(solveSmall(N));
            }
            return;
        }

        // [fr,to)
        long per = Math.max((N + nodes - 1) / nodes, 1L);
        long fr = per * myid;
        long to = Math.min(N, fr + per);
        long[] result = solveSub(N, fr, to);

        for (long x : result) {
            message.PutLL(0, x);
        }
        if (myid >= 1) {
            return;
        }

        // aggregate and answer
        long[] left = new long[nodes];
        long[] right = new long[nodes];
        long[] center = new long[nodes];
        long[] all = new long[nodes];

        int z = nodes;
        long eatAll = 0;
        for (int i = 0 ; i < nodes ; i++) {
            left[i] = message.GetLL(i);
            right[i] = message.GetLL(i);
            center[i] = message.GetLL(i);
            all[i] = message.GetLL(i);
            if (all[i] == Long.MIN_VALUE) {
                z = Math.min(z, i);
            } else {
                eatAll += all[i];
            }
        }

        long max = Math.max(0, eatAll);
        for (int l = -1 ; l < z ; l++) {
            for (int r = z ; r > l ; r--) {
                long sum = 0;
                for (int k = 0 ; k < z ; k++) {
                    if (k < l) {
                        sum += all[k];
                    } else if (k == l) {
                        sum += left[k];
                    } else if (k == r) {
                        sum += right[k];
                    } else if (r < k) {
                        sum += all[k];
                    }
                }
                max = Math.max(max, sum);
            }
        }

        for (int c = 0 ; c < z ; c++) {
            long sum = 0;
            for (int k = 0; k < z; k++) {
                if (c == k) {
                    sum += center[k];
                } else {
                    sum += all[k];
                }
            }
            max = Math.max(max, sum);
        }
        System.out.println(max);
    }

    private static long solveSmall(long N) {
        int n = (int)N;
        long[] ct = new long[n];
        for (int i = 0; i < n ; i++) {
            ct[i] = sandwich.GetTaste(i);
        }

        long max = 0;
        for (int l = -1 ; l < n ; l++) {
            for (int r = n ; r > l ; r--) {
                long sum = 0;
                for (int k = 0 ; k < n ; k++) {
                    if (k <= l) {
                        sum += ct[k];
                    } else if (r <= k) {
                        sum += ct[k];
                    }
                }
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    private static long[] solveSub(long N, long fr, long to) {
        if (fr >= N || to > N) {
            return new long[]{ Long.MIN_VALUE , Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE};
        }

        int cnt = (int)(to - fr);
        int ci = 0;
        long[] values = new long[cnt];
        for (long l = fr ; l < to ; l++) {
            values[ci++] = sandwich.GetTaste(l);
        }

        long leftMax = 0;
        long rightMax = 0;
        long all = 0;
        for (int i = 0 ; i < cnt ; i++) {
            all += values[i];
        }
        long left = 0;
        for (int i = 0 ; i < cnt ; i++) {
            left += values[i];
            leftMax = Math.max(leftMax, left);
        }
        long right = 0;
        for (int i = cnt-1 ; i >= 0 ; i--) {
            right += values[i];
            rightMax = Math.max(rightMax, right);
        }

        long min = Long.MAX_VALUE;
        long now = 0;
        for (int i = 0 ; i < cnt ; i++) {
            now += values[i];
            if (min > now) {
                min = now;
            }
            if (now > 0) {
                now = 0;
            }
        }
        return new long[] {leftMax, rightMax, all - min, all};
    }

    public static class sandwich {
        public sandwich() {
        }

        public static long GetN() {
            return 7L;
        }

        public static long GetTaste(long index) {
            switch ((int)index) {
                case 0: return -10L;
                case 1: return -2L;
                case 2: return -5L;
                case 3: return -4L;
                case 4: return -3L;
                case 5: return 5L;
                case 6: return -1L;
                default: throw new IllegalArgumentException("Invalid argument");
            }
        }
    }

    public static class message {

        // The number of nodes on which the solution is running.
        public static int NumberOfNodes() { return 1; }

        // The index (in the range [0 .. NumberOfNodes()-1]) of the node on which this
        // process is running.
        public static int MyNodeId() { return 0; }

        // In all the functions below, if "target" or "source" is not in the valid
        // range, the behaviour is undefined.

        // The library internally has a message buffer for each of the nodes in
        // [0 .. NumberOfNodes()-1]. It accumulates the message in such a buffer through
        // the "Put" methods.

        // Append "value" to the message that is being prepared for the node with id
        // "target". The "Int" in PutInt is interpreted as 32 bits, regardless of
        // whether the actual int type will be 32 or 64 bits.
        public static void PutChar(int target, char value) {}
        public static void PutInt(int target, int value) {}
        public static void PutLL(int target, long value) {}

        // Send the message that was accumulated in the appropriate buffer to the
        // "target" instance, and clear the buffer for this instance.
        //
        // This method is non-blocking - that is, it does not wait for the receiver to
        // call "Receive", it returns immediately after sending the message.
        public static void Send(int target) {}

        // The library also has a receiving buffer for each instance. When you call
        // "Receive" and retrieve a message from an instance, the buffer tied to this
        // instance is overwritten. You can then retrieve individual parts of the
        // message through the Get* methods. You must retrieve the contents of the
        // message in the order in which they were appended.
        //
        // This method is blocking - if there is no message to receive, it will wait for
        // the message to arrive.
        //
        // You can call Receive(-1) to retrieve a message from any source, or with
        // source in [0 .. NumberOfNodes()-1] to retrieve a message from a particular
        // source.
        //
        // It returns the number of the instance which sent the message (which is equal
        // to source, unless source is -1).
        public static int Receive(int source) { return 0; }

        // Each of these methods returns and consumes one item from the buffer of the
        // appropriate instance. You must call these methods in the order in which the
        // elements were appended to the message (so, for instance, if the message was
        // created with PutChar, PutChar, PutLL, you must call GetChar, GetChar, GetLL
        // in this order).
        // If you call them in different order, or you call a Get* method after
        // consuming all the contents of the buffer, behaviour is undefined.
        // The "Int" in GetInt is interpreted as 32 bits, regardless of whether the
        // actual int type will be 32 or 64 bits.
        public static char GetChar(int source) { return 0; }
        public static int GetInt(int source) { return 0; }
        public static long GetLL(int source) { return 0;}
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
