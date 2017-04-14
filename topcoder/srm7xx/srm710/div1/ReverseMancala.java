package topcoder.srm7xx.srm710.div1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReverseMancala {
    public int[] findMoves(int[] start, int[] target) {
        n = start.length;
        sum = 0;
        for (int i = 0; i < n ; i++) {
            sum += start[i];
        }

        int[] left = start.clone();
        int[] right = target.clone();

        List<Move> leftMove = correctStones(left);
        List<Move> rightMove = correctStones(right);
        for (int i = rightMove.size() - 1 ; i >= 0 ; i--) {
            leftMove.add(rightMove.get(i).reverse());
        }

        int[] response = new int[leftMove.size()];
        for (int i = 0 ; i < leftMove.size() ; i++) {
            response[i] = leftMove.get(i).code();
        }
        return response;
    }

    class Move {
        int type;
        int idx;
        int toIdx;

        public Move(int type, int idx) {
            this.type = type;
            this.idx = idx;
            this.toIdx = -1;
        }


        public int code() {
            return type * n + idx;
        }

        public Move reverse() {
            if (toIdx == -1) {
                throw new RuntimeException("its not called");
            }
            return new Move(1-type, toIdx);
        }
    }

    public List<Move> correctStones(int[] stones) {
        List<Move> moves = new ArrayList<>();
        while (true) {
            int idx = -1;
            for (int i = 1 ; i < n ; i++) {
                if (stones[i] >= 1) {
                    idx = i;
                    break;
                }
            }
            if (idx == -1) {
                break;
            }
            Move mv = new Move(0, idx);
            applyMove(stones, mv);
            moves.add(mv);
        }
        return moves;
    }


    public void applyMove(int[] stones, Move move) {
        if (move.type == 0) {
            applyMoveA(stones, move);
        } else {
            applyMoveB(stones, move);
        }
    }

    public void applyMoveA(int[] stones, Move move) {
        int toIdx = move.idx;
        int take = stones[toIdx];
        stones[toIdx] = 0;
        while (take >= 1) {
            toIdx = (toIdx + 1) % n;
            stones[toIdx]++;
            take--;
        }
        move.toIdx = toIdx;
    }

    public void applyMoveB(int[] stones, Move move) {
        int have = 1;
        int toIdx = move.idx;
        stones[toIdx]--;
        while (true) {
            toIdx = (toIdx + n - 1) % n;
            if (stones[toIdx] == 0) {
                stones[toIdx] = have;
                break;
            }
            stones[toIdx]--;
            have++;
        }
        move.toIdx = toIdx;
    }

    int n;
    int sum;

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
