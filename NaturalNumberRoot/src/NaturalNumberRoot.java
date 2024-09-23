import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program with implementation of {@code NaturalNumber} secondary operation
 * {@code root} implemented as static method.
 *
 * @author Justin Imber
 *
 */
public final class NaturalNumberRoot {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private NaturalNumberRoot() {
    }

    /**
     * Updates {@code n} to the {@code r}-th root of its incoming value.
     *
     * @param n
     *            the number whose root to compute
     * @param r
     *            root
     * @updates n
     * @requires r >= 2
     * @ensures n ^ (r) <= #n < (n + 1) ^ (r)
     */
    public static void root(NaturalNumber n, int r) {
        assert n != null : "Violation of: n is  not null";
        assert r >= 2 : "Violation of: r >= 2";

        /*
         * Creates variable tooHigh with the same type as n, then copies and
         * stores the value of n and adds 1 to fit the math (n + 1)
         */
        NaturalNumber tooHigh = n.newInstance();
        tooHigh.copyFrom(n);
        tooHigh.increment();
        /*
         * Creates variable lowEnough with the same type as n, starting at
         * initial value (0)
         */
        NaturalNumber lowEnough = n.newInstance();
        lowEnough.setFromInt(0);

        /*
         * Creates variable comparison with the same type as n, then copies and
         * stores value from tooHigh and subtracts lowEnough to use in while
         * loop
         */
        NaturalNumber comparison = n.newInstance();
        comparison.copyFrom(tooHigh);
        comparison.subtract(lowEnough);

        /*
         * Creates NaturalNumbers of the same type as n for values of 1 and 2
         * for the sake of NaturalNumber comparisons and math in the while loop
         */
        NaturalNumber one = n.newInstance();
        one.setFromInt(1);
        NaturalNumber two = n.newInstance();
        two.setFromInt(2);

        /*
         * Creates variable midpoint with the same type as n for the sake of
         * calculating and storing the midpoint for that iteration of the while
         * loop using the math (tooHigh + lowEnough) / 2
         */
        NaturalNumber midpoint = n.newInstance();
        /*
         * Runs until the comparison value of (tooHigh - lowEnough) to one is 0
         * or less, meaning until the value of (tooHigh - lowEnough) is equal to
         * or less than 1
         */
        while (comparison.compareTo(one) > 0) {
            /*
             * Calculates and stores the midpoint each iteration of the while
             * loop using the math (tooHigh + lowEnough) / 2
             */
            midpoint.add(tooHigh);
            midpoint.add(lowEnough);
            midpoint.divide(two);

            /*
             * Creates variable poweredMP to store the value for midpoint raised
             * to the power of r
             */
            NaturalNumber poweredMP = n.newInstance();
            poweredMP.copyFrom(midpoint);
            poweredMP.power(r);

            /*
             * Copies midpoint value to tooHigh or lowEnough depending on
             * whether poweredNN is greater than n or holds a different
             * relationship, respectively
             */
            if (poweredMP.compareTo(n) > 0) {
                tooHigh.copyFrom(midpoint);
            } else {
                lowEnough.copyFrom(midpoint);
            }

            /*
             * Evaluates the new comparison value for the loop using the new
             * tooHigh or lowEnough variable
             */
            comparison.copyFrom(tooHigh);
            comparison.subtract(lowEnough);
        }

        /*
         * Copies the value of lowEnough to n since the method contract is to
         * update n and lowEnough holds the expected value within the method
         */
        n.copyFrom(lowEnough);

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        final String[] numbers = { "0", "1", "13", "1024", "189943527", "0",
                "1", "13", "4096", "189943527", "0", "1", "13", "1024",
                "189943527", "82", "82", "82", "82", "82", "9", "27", "81",
                "243", "143489073", "2147483647", "2147483648",
                "9223372036854775807", "9223372036854775808",
                "618970019642690137449562111",
                "162259276829213363391578010288127",
                "170141183460469231731687303715884105727" };
        final int[] roots = { 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 15, 15, 15, 15, 15,
                2, 3, 4, 5, 15, 2, 3, 4, 5, 15, 2, 2, 3, 3, 4, 5, 6 };
        final String[] results = { "0", "1", "3", "32", "13782", "0", "1", "2",
                "16", "574", "0", "1", "1", "1", "3", "9", "4", "3", "2", "1",
                "3", "3", "3", "3", "3", "46340", "46340", "2097151", "2097152",
                "4987896", "2767208", "2353973" };

        for (int i = 0; i < numbers.length; i++) {
            NaturalNumber n = new NaturalNumber2(numbers[i]);
            NaturalNumber r = new NaturalNumber2(results[i]);
            root(n, roots[i]);
            if (n.equals(r)) {
                out.println("Test " + (i + 1) + " passed: root(" + numbers[i]
                        + ", " + roots[i] + ") = " + results[i]);
            } else {
                out.println("*** Test " + (i + 1) + " failed: root("
                        + numbers[i] + ", " + roots[i] + ") expected <"
                        + results[i] + "> but was <" + n + ">");
            }
        }

        out.close();
    }

}
