import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Utlising user-input doubles, finds closest approximation of user-provided mu
 * using de Jager formula.
 *
 * @author Justin Imber
 *
 */
public final class ABCDGuesser1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        // declaration and initialisation of initial variables
        String response;
        double userNum = 0;
        boolean keepGoing = true;
        /*
         * Loops until keepGoing is false, which is when the user's input is
         * both a double or integer and positive.
         */
        while (keepGoing) {
            // Prompts the user for a positive double
            out.print("Please enter a positive double: ");
            // Stores user's input as a string to response
            response = in.nextLine();
            /*
             * Checks if the user's input is a double and if it is, checks
             * whether the double is greater than 0.
             */
            if (FormatChecker.canParseDouble(response)
                    && Double.parseDouble(response) > 0.0) {
                // Changes keepGoing to false to end while loop
                keepGoing = false;
                // Parses the double a final time to store for return
                userNum = Double.parseDouble(response);
            }
        }
        // returns the user's entered double
        return userNum;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        // declaration and initialisation of initial variables
        String response;
        double userNum = 0;
        boolean keepGoing = true;
        /*
         * Loops until keepGoing is false, which is when the user's input is a
         * double or integer, not equal to 1.0, and positive.
         */
        while (keepGoing) {
            // Prompts the user for a positive double
            out.print("Please enter a positive double (not 1.0): ");
            // Stores user's input as a string to response
            response = in.nextLine();
            /*
             * Checks if the user's input is a double and if it is, checks
             * whether the double is not equal to 1.0 and greater than 0.
             */
            if (FormatChecker.canParseDouble(response)
                    && Double.parseDouble(response) != 1.0
                    && Double.parseDouble(response) > 0.0) {
                // Changes keepGoing to false to end while loop
                keepGoing = false;
                // Parses the double a final time to store for return
                userNum = Double.parseDouble(response);
            }
        }
        // returns the user's entered double
        return userNum;

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

        // Declaration and initialisation of percent multiplier
        final int percentMultiplier = 100;
        // Declaration of all user-value variables
        double muValue, wValue, xValue, yValue, zValue;
        /*
         * Declaration and initialisation of all the potential exponents to be
         * used
         */
        final double[] exponents = { -5.0, -4.0, -3.0, -2.0, -1.0, -0.5,
                -1.0 / 3.0, -0.25, 0.0, 0.25, 1.0 / 3.0, 0.5, 1.0, 2.0, 3.0,
                4.0, 5.0 };
        /*
         * Declaration and initialisation of the max amount of exponents used in
         * the each of the de Jager calculations
         */
        final int usedExponents = 4;
        // Declaration of double array to store the best exponents
        double[] bestExponents = new double[usedExponents];
        /*
         * Declaration and initialisation of the last usable index for the
         * bestExponents array since Checkstyle is kind of dumb.
         */
        final int lastExponentIndex = 3;
        // Declaration and initialisation of all iteration variables
        int firstIteration = 0, secondIteration = 0, thirdIteration = 0,
                fourthIteration = 0;
        // Prompts the user for a value for mu and stores the response.
        out.println("Firstly, you will enter a value for mu.");
        muValue = getPositiveDouble(in, out);
        // Prompts the user for a value for w and stores the response.
        out.println("You will now enter a value for w.");
        wValue = getPositiveDoubleNotOne(in, out);
        // Prompts the user for a value for x and stores the response.
        out.println("You will now enter a value for x.");
        xValue = getPositiveDoubleNotOne(in, out);
        // Prompts the user for a value for y and stores the response.
        out.println("You will now enter a value for y.");
        yValue = getPositiveDoubleNotOne(in, out);
        // Prompts the user for a value for z and stores the response.
        out.println("You will now enter a value for z.");
        zValue = getPositiveDoubleNotOne(in, out);

        // Declaration of relative error variable for use post-loop
        double relativeError;
        /*
         * Declaration and initialisation of result variable which will store
         * the result of the de Jager formula and the temp variable which will
         * continuously update within the loop to the closest value to the
         * user-entered mu.
         */
        double result = 0, temp = -1;
        // Runs until each exponent is tested for the w value.
        while (firstIteration < exponents.length) {
            // Runs until each exponent is tested for the x value.
            while (secondIteration < exponents.length) {
                // Runs until each exponent is tested for the y value.
                while (thirdIteration < exponents.length) {
                    // Runs until each exponent is tested for the z value.
                    while (fourthIteration < exponents.length) {
                        // Calculates the result of the de Jager formula
                        result = Math.pow(wValue, exponents[firstIteration])
                                * Math.pow(xValue, exponents[secondIteration])
                                * Math.pow(yValue, exponents[thirdIteration])
                                * Math.pow(zValue, exponents[fourthIteration]);
                        /*
                         * Checks if the result is closer to mu than the
                         * previously conceived closest value
                         */
                        if ((Math.abs(muValue - result) < Math
                                .abs(muValue - temp))) {
                            bestExponents[0] = exponents[firstIteration];
                            bestExponents[1] = exponents[secondIteration];
                            bestExponents[2] = exponents[thirdIteration];
                            bestExponents[lastExponentIndex] = exponents[fourthIteration];
                            // Stores the new closest value to temp
                            temp = result;
                        }
                        // Sets the next exponent index to be checked
                        fourthIteration++;
                    }
                    // Resets fourth while loop to check each exponent
                    fourthIteration = 0;
                    // Sets the next exponent index to be checked
                    thirdIteration++;
                }
                // Resets third while loop to check each exponent
                thirdIteration = 0;
                // Sets the next exponent index to be checked
                secondIteration++;
            }
            // Resets second while loop to check each exponent
            secondIteration = 0;
            // Sets the next exponent index to be checked
            firstIteration++;
        }

        /*
         * Calculates the relative error of the de Jager formula using the best
         * combination of exponents.
         */
        relativeError = (Math.abs(temp - muValue) / muValue)
                * percentMultiplier;

        // Prints the best combination of exponents to the user.
        out.print("The best combination of exponents was: ");
        out.println(wValue + "^" + bestExponents[0] + " * " + xValue + "^"
                + bestExponents[1] + " * " + yValue + "^" + bestExponents[2]
                + " * " + zValue + "^" + bestExponents[lastExponentIndex]);
        // Outputs the result of the de Jager formula for the best exponents
        out.println("Result of the de Jager formula using best combination of "
                + "exponents: " + temp);
        // Outputs relative error message
        out.print("The relative error of the de Jager formula: ");
        // Outputs the relative error to 2 decimal places
        out.print(relativeError, 2, false);
        out.println("%");

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
