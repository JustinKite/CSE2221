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
public final class Newton2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton2() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        // declaration and initialisation of acceptable relative error
        final double relativeError = 0.0001;
        // creating initial guess
        double guess = x;
        /*
         * Runs a Newton iteration for finding an approximate result for the
         * square root of the provided double unless the initial value is 0 or
         * until the approximation is within 0.01% of the exact answer.
         */
        while (x > 0 && Math.abs(guess * guess - x) / x >= relativeError
                * relativeError) {
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
        double result, userNum;
        // prompts user to enter y to proceed or anything else to terminate
        out.print("Enter 'y' to calculate a root or anything else to quit: ");
        // records user's response in variable response
        String response = in.nextLine();
        // loop runs until response is anything other than "y"
        while (response.equals("y")) {
            // prompts user to enter a double whose root they would like estimated
            out.print("Please enter a positive double number: ");
            // records user's provided double in variable userNum
            userNum = in.nextDouble();
            /*
             * runs and stores the approximation of the root of the
             * user-provided double
             */
            result = sqrt(userNum);
            // outputs the result of sqrt() function to user
            out.println("The approximate root of the double you provided is: "
                    + result);
            // prompts user to enter y to proceed or anything else to terminate
            out.print("Enter 'y' to calculate another root or anything "
                    + "else to quit: ");
            // records user's response in variable response
            response = in.nextLine();
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
