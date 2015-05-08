package atcoder.gw2015;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dhamada on 15/05/07.
 */
public class C {
    static PrintWriter out;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        out = new PrintWriter(System.out);
        int h = in.nextInt();
        int w = in.nextInt();


        boolean rev = false;
        if ((h * w) % 2 == 1) {
            printAndFlush("First");
            printAndFlush((h+1)/2, (w+1)/2, 0);
        } else {
            rev = true;
            printAndFlush("Second");
        }
        while (true) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            if (a == -1 && b == -1 && c == -1) {
                return;
            }
            int pa = h+1-a;
            int pb = w+1-b;
            int pc = rev ? (1 - c) : c;
            printAndFlush(pa, pb, pc);
        }
    }

    static void printAndFlush(String x) {
        out.println(x);
        out.flush();
    }

    static void printAndFlush(int h, int w, int c) {
        out.println(String.format("%d %d %d", h, w, c));
        out.flush();
    }
}
