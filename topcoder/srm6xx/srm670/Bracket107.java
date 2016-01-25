package topcoder.srm6xx.srm670;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hama_du on 15/10/11.
 */
public class Bracket107 {
    public int yetanother(String s) {
        char[] c = s.toCharArray();
        int n = c.length;

        Set<String> answers = new HashSet<>();
        for (int i = 0 ; i < n ; i++) {
            for (int j = i+1 ; j <= n ; j++) {
                String left = s.substring(0, i);
                String part = s.substring(i, j);
                String right = s.substring(j, n);

                char partHead = part.charAt(0);
                String partTail = part.substring(1);

                char partLast = part.charAt(part.length()-1);
                String partInit = part.substring(0, part.length()-1);

                String candidateA = left + partTail + partHead + right;
                String candidateB = left + partLast + partInit + right;
                if (isOK(candidateA.toCharArray())) {
                    answers.add(candidateA);
                }
                if (isOK(candidateB.toCharArray())) {
                    answers.add(candidateB);
                }
            }
        }
        answers.remove(s);
        return answers.size();
    }

    private boolean isOK(char[] d) {
        int have = 0;
        int n = d.length;
        for (int i = 0; i < n ; i++) {
            if (d[i] == '(') {
                have++;
            } else {
                have--;
                if (have < 0) {
                    return false;
                }
            }
        }
        return have == 0;
    }
}
