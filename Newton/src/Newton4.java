import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Calculates an approximate root to a user-provided double.
 *
 * @author Justin Imber
 *
 */
public final class Newton4 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton4() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @param epsilon
     *            positive number representing relative error value
     * @return estimate of square root
     */
    private static double sqrt(double x, double epsilon) {
        // creating initial guess
        double guess = x;
        /*
         * Runs a Newton iteration for finding an approximate result for the
         * square root of the provided double unless the initial value is 0 or
         * until the approximation is within 0.01% of the exact answer.
         */
        while (x != 0 && Math.abs(guess * guess - x) / x >= epsilon * epsilon) {
            // updates guess to a closer approximation
            guess = (guess + (x / guess)) / 2;
        }
        // returns the value of guess AKA the calculated approximation
        return guess;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        // declares variables used for recording results and user's double
        double result, userNum, relativeError;
        // declares and initialises the percent multiplier
        final double percentMultiplier = 0.01;
        // prompts user to enter a double whose root they would like estimated
        out.print("Please enter a positive double number: ");
        // records user's provided double in variable userNum
        userNum = in.nextDouble();
        while (userNum >= 0) {
            // prompts user to enter a double to use for epsilon
            out.print("Please enter a positive relative error percentage: ");
            // records user's relative error double in variable relativeError
            relativeError = in.nextDouble() * percentMultiplier;
            /*
             * runs square-root function with provided double and relativeError
             * and stores the approximation of the root of the user-provided
             * double
             */
            result = sqrt(userNum, relativeError);
            // outputs the result of sqrt() function to user
            out.println("The approximate root of the double you provided is: "
                    + result);
            // prompts user to enter a double whose root they would like estimated
            out.print("Please enter a positive double number: ");
            // records user's provided double in variable userNum
            userNum = in.nextDouble();
        }
        // tells the user that the program is terminating
        out.println("Program terminating... Good-bye!");
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
