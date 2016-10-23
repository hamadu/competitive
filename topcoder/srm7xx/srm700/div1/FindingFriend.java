package topcoder.srm7xx.srm700.div1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hamada on 2016/10/13.
 */
public class FindingFriend {
    public int find(int roomSize, int[] leaders, int friendPlace) {
        int roomCount = leaders.length;
        for (int i = 0; i < leaders.length; i++) {
            if (friendPlace == leaders[i]) {
                return 1;
            }
        }

        Arrays.sort(leaders);
        int n = roomCount * roomSize;

        int last = 1;
        List<Integer> gaps = new ArrayList<>();
        for (int i = 1 ; i <= leaders.length ; i++) {
            int thi = i == leaders.length ? n+1 : leaders[i];
            int winner = Math.min(thi, friendPlace);

            int gap = winner - last - 1;
            gaps.add(gap);
            last = winner;
            if (last == friendPlace) {
                break;
            }
        }

        int nonWinner = roomSize-1;
        int count = 0;
        for (int wantToPlace = 0 ; wantToPlace < gaps.size() ; wantToPlace++) {
            int inRoomLeft = 0;
            int inOtherLeft = 0;
            for (int i = 0 ; i < gaps.size() ; i++) {
                if (wantToPlace == i) {
                    inRoomLeft += nonWinner;
                } else {
                    inOtherLeft += nonWinner;
                }
                int gap = gaps.get(i);
                if (inOtherLeft >= 1) {
                    int og = Math.min(gap, inOtherLeft);
                    gap -= og;
                    inOtherLeft -= og;
                }
                inRoomLeft -= gap;
            }
            if (inRoomLeft >= 1) {
                count++;
            }
        }
        return count;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    public static void main(String[] args) {
        FindingFriend ff = new FindingFriend();
        debug(ff.find(2, new int[]{5,1,2}, 4));

        debug(ff.find(2, new int[]{3,6,8,1,2}, 10));
    }
}