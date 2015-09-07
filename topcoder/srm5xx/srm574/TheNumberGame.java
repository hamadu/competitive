package topcoder.srm5xx.srm574;

/**
 * Created by hama_du on 15/09/05.
 */
public class TheNumberGame {
    public String determineOutcome(int A, int B) {
        String a = String.valueOf(A);
        String b = String.valueOf(B);

        if (a.contains(b) || a.contains(new StringBuilder().append(b).reverse().toString())) {
            return "Manao wins";
        } else {
            return "Manao loses";
        }
    }
}
