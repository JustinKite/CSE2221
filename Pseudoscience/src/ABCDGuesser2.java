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
public final class ABCDGuesser2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser2() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param userNums
     *            double array containing the user's input values
     * @param bestExponents
     *            double array containing best combination of de Jager exponents
     * @param bestResult
     *            the best result of the de Jager formula
     * @param error
     *            the relative error value of de Jager formula vs user's mu
     * @param out
     *            the output stream
     */
    private static void printResults(double[] userNums, double[] bestExponents,
            double bestResult, double error, SimpleWriter out) {
        /*
         * Declaration and initialisation of the last usable index for the
         * userValues array since Checkstyle is kind of dumb.
         */
        final int lastUserValIndex = 3;
        /*
         * Declaration and initialisation of the last usable index for the
         * bestExponents array since Checkstyle is kind of dumb.
         */
        final int lastExponentIndex = 3;
        // Prints the best combination of exponents to the user.
        out.print("The best combination of exponents was: ");
        out.println(userNums[0] + "^" + bestExponents[0] + " * " + userNums[1]
                + "^" + bestExponents[1] + " * " + userNums[2] + "^"
                + bestExponents[2] + " * " + userNums[lastUserValIndex] + "^"
                + bestExponents[lastExponentIndex]);
        // Outputs the result of the de Jager formula for the best exponents
        out.println("Result of the de Jager formula using best combination of "
                + "exponents: " + bestResult);
        // Outputs relative error message
        out.print("The relative error of the de Jager formula: ");
        // Outputs the relative error to 2 decimal places
        out.print(error, 2, false);
        out.println("%");
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
                    && Double.parseDouble(response) >= 0.0) {
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
        // Declaration of user mu variable
        double muValue;
        /*
         * Declaration and initialisation of all the potential exponents to be
         * used
         */
        final double[] exponents = { -5, -4, -3, -2, -1, -0.5, -1 / 3, -0.25, 0,
                0.25, 1 / 3, 0.5, 1, 2, 3, 4, 5 };
        /*
         * Declaration and initialisation of the max amount of exponents and
         * user values used in the each of the de Jager calculations
         */
        final int arrayLength = 4;
        // Declaration of double array to store the best exponents
        double[] bestExponents = new double[arrayLength];
        // Declaration of double array to store the user's values
        double[] userValues = new double[arrayLength];
        /*
         * Declaration and initialisation of the last usable index for the
         * bestExponents array since Checkstyle is kind of dumb.
         */
        final int lastExponentIndex = 3;
        /*
         * Declaration and initialisation of the last usable index for the
         * userValues array since Checkstyle is kind of dumb.
         */
        final int lastUserValIndex = 3;

        // Prompts the user for a value for mu and stores the response.
        out.println("Firstly, you will enter a value for mu.");
        muValue = getPositiveDouble(in, out);
        // Prompts the user for a value for w and stores the response.
        out.println("You will now enter a value for w.");
        userValues[0] = getPositiveDoubleNotOne(in, out);
        // Prompts the user for a value for x and stores the response.
        out.println("You will now enter a value for x.");
        userValues[1] = getPositiveDoubleNotOne(in, out);
        // Prompts the user for a value for y and stores the response.
        out.println("You will now enter a value for y.");
        userValues[2] = getPositiveDoubleNotOne(in, out);
        // Prompts the user for a value for z and stores the response.
        out.println("You will now enter a value for z.");
        userValues[lastUserValIndex] = getPositiveDoubleNotOne(in, out);

        // Declaration of relative error variable for use post for loops
        double relativeError;
        /*
         * Declaration and initialisation of result variable which will store
         * the result of the de Jager formula and the temp variable which will
         * continuously update within the loop to the closest value to the
         * user-entered mu.
         */
        double result = 0, temp = -1;
        // Runs until each exponent is tested for the w value.
        for (int h = 0; h < exponents.length; h++) {
            // Runs until each exponent is tested for the x value.
            for (int i = 0; i < exponents.length; i++) {
                // Runs until each exponent is tested for the y value.
                for (int j = 0; j < exponents.length; j++) {
                    // Runs until each exponent is tested for the z value.
                    for (int k = 0; k < exponents.length; k++) {
                        // Calculates the result of the de Jager formula
                        result = Math.pow(userValues[0], exponents[h])
                                * Math.pow(userValues[1], exponents[i])
                                * Math.pow(userValues[2], exponents[j])
                                * Math.pow(userValues[lastUserValIndex],
                                        exponents[k]);
                        /*
                         * Checks if the result is closer to mu than the
                         * previously conceived closest value
                         */
                        if ((Math.abs(muValue - result) < Math
                                .abs(muValue - temp))) {
                            bestExponents[0] = exponents[h];
                            bestExponents[1] = exponents[i];
                            bestExponents[2] = exponents[j];
                            bestExponents[lastExponentIndex] = exponents[k];
                            // Stores the new closest value to temp
                            temp = result;
                        }
                    }
                }
            }
        }

        /*
         * Calculates the relative error of the de Jager formula using the best
         * combination of exponents.
         */
        relativeError = (Math.abs(temp - muValue) / muValue)
                * percentMultiplier;

        printResults(userValues, bestExponents, temp, relativeError, out);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
