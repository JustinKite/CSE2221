import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Takes a user-provided list of words and their definitions and creates
 * individual html pages for them along with an index page of the words
 * alphabetically arranged with hyperlinks to each.
 *
 * @author Justin Imber
 *
 */
public final class Glossary {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Glossary() {
    }

    /**
     * Compares strings to reorder them alphabetically.
     */
    private static class StringCompare implements Comparator<String> {
        /*
         * Side comment for the bug reported for the above class header:
         *
         * This class layout is taken almost directly from the Queue lecture
         * slides and the bug reported has no recognisable bearing on the code's
         * intended use.
         */
        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    }

    /**
     * Reads the words and their respective definitions from the provided file
     * then puts them into a Map.
     *
     * @param wordsAndDefs
     *            blank Map to hold the words and their respective definitions
     * @param inFile
     *            the file that holds the words and their respective definitions
     * @requires inFile is a valid file path and readable
     *
     * @requires wordsAndDefs is an empty Map
     *
     */
    private static void getWordsAndDefinitions(Map<String, String> wordsAndDefs,
            String inFile) {
        // Establishes the stream for reading the user's provided file
        SimpleReader in = new SimpleReader1L(inFile);
        String definition = "";
        String word = "";
        String temp = "";

        /*
         * Loop runs until in reaches the end of its stream. In the loop, each
         * word and its definition is added to the wordsAndDefs Map.
         */
        while (!in.atEOS()) {
            word = in.nextLine();
            definition = in.nextLine();
            /*
             * Loops through to ensure the full definition is acquired if the
             * definition occupies more than a single line in the provided
             * document.
             */
            while (!in.atEOS() && !(temp = in.nextLine()).isBlank()) {
                definition += " " + temp;
            }
            wordsAndDefs.add(word, definition);
        }

        // Closes the input stream
        in.close();
    }

    /**
     * Stores the words in the Queue words and puts the Queue into alphabetical
     * order.
     *
     * @param wordAndDef
     *            holds the user's words and their respective definitions
     * @param words
     *            blank Queue to hold all the words to be sorted in alphabetical
     *            order
     */
    private static void reorderWords(Map<String, String> wordAndDef,
            Queue<String> words) {
        // Creates a comparator to aphabetise the Queue
        Comparator<String> alphabetical = new StringCompare();
        //Runs through each entry of wordAndDef and adds each key to the Queue
        for (Pair<String, String> tempPair : wordAndDef) {
            words.enqueue(tempPair.key());
        }
        // Alphabetically sorts the entries within the Queue
        words.sort(alphabetical);
    }

    /**
     * Creates the index page from the user-provided output folder and
     * alphabetically arranged words.
     *
     * @param outFolder
     *            the folder path provided by the user
     * @param wordList
     *            holds the list of words in alphabetical order
     */
    private static void generateIndex(String outFolder,
            Queue<String> wordList) {
        // Creates the path using the user-provided folder
        String path = outFolder + "\\index.html";
        // Creates a simplwriter using the path
        SimpleWriter out = new SimpleWriter1L(path);
        // Will hold a copy of the Queue to allow for while loop
        Queue<String> wordsCopy = wordList.newInstance();
        // Outputs all of the html formatting
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Glossary</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Glossary</h1>");
        out.println("<hr>");
        out.println("<h2>Index</h2>");
        out.println("<ul>");
        /*
         * Loops through each word in the queue, creating a hyperlink to each in
         * the glossary
         */
        while (wordList.length() > 0) {
            String word = wordList.dequeue();
            out.println("<li>");
            out.println("<a href=\"" + word + ".html\">" + word + "</a>");
            out.println("</li>");
            wordsCopy.enqueue(word);
        }
        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");
        // Closes the output stream
        out.close();
        // Transfers the queue from wordsCopy back to words
        wordList.transferFrom(wordsCopy);
    }

    /**
     * Adjusts each word's definition replacing any words in the glossary with
     * the hyperlinked version of the word to its indivdiual page.
     *
     * @param wordAndDef
     *            holds the user's words and their respective definitions
     * @param wordList
     *            holds the list of words only
     */
    private static void adjustDefinitions(Map<String, String> wordAndDef,
            Queue<String> wordList) {
        String word = "";
        String definition = "";
        String temp = "";
        String adjustedWord = "";
        String regex = "";
        /*
         * Loops through each entry of the Map wordAndDef to update each key's
         * (word's) value (definiiton) with an updated version containing
         * hyperlinks to each original glossary word's entry / html page.
         */
        for (int i = 0; i < wordAndDef.size(); i++) {
            word = wordList.dequeue();
            wordList.enqueue(word);
            definition = wordAndDef.value(word);
            for (int j = 0; j < wordList.length(); j++) {
                temp = wordList.dequeue();
                if (definition.contains(temp)) {
                    adjustedWord = "<a href=\"" + temp + ".html\">" + temp
                            + "</a>";
                    /*
                     * The regex for replacing all instances of the found word.
                     * The following regex only replaces the word if it is
                     * exactly the word with no additional word characters or
                     * digits around it.
                     */
                    regex = "\\b" + temp + "\\b";
                    definition = definition.replaceAll(regex, adjustedWord);
                }
                wordList.enqueue(temp);
            }
            // Updates the definition for the word being worked on.
            wordAndDef.replaceValue(word, definition);
        }
    }

    /**
     * Creates each individual word's html file.
     *
     * @param outFolder
     *            the folder path provided by the user
     * @param word
     *            holds the word whose file is to be generated
     * @param wordAndDef
     *            holds the user's words and their respective definitions
     */
    private static void generateWordFile(String outFolder, String word,
            Map<String, String> wordAndDef) {
        // Creates the path using the user-provided folder
        String path = outFolder + "\\" + word + ".html";
        // Creates a simplwriter using the path
        SimpleWriter out = new SimpleWriter1L(path);
        /*
         * Outputs all of the html formatting for the word html file being
         * generated
         */
        out.println("<html>");
        out.println("<head>");
        // Titles the page with the word
        out.println("<title>" + word + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>");
        // Creates a specially formatted header of the word for its entry
        out.println("<b><i><font color=\"red\">" + word + "</font></i></b>");
        out.println("</h2>");
        // Outputs the html for the definition
        out.println("<blockquote>" + wordAndDef.value(word) + "</blockquote>");
        out.println("<hr>");
        out.println("<p>");
        out.println("Return to <a href=\"index.html\">index</a>.");
        out.println("</p>");
        out.println("</body>");
        out.println("</html>");
        // Closes the output stream
        out.close();
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
        /*
         * Asks the user for both their input file containing their words and
         * definitions, as well as their preferred output folder location in
         * which to store all of the generated .html files
         */
        out.print("Please enter the name of the file containing your terms and "
                + "definitions: ");
        String inputFile = in.nextLine();
        out.print("Please enter the location of the folder in which you would "
                + "like your files created: ");
        String outputFolder = in.nextLine();

        // Creates a map for the words and their definitions
        Map<String, String> words = new Map1L<>();
        // Calls the method to fetch and store the words and definitions
        getWordsAndDefinitions(words, inputFile);

        // Creates a queue for the words to be alphabetised
        Queue<String> wordQueue = new Queue1L<>();
        // Calls the method to alphabetise the words
        reorderWords(words, wordQueue);

        // Calls the method to create the index html file
        generateIndex(outputFolder, wordQueue);
        // Calls the method to adjust each word's definition
        adjustDefinitions(words, wordQueue);
        // Loops through each word to generate its respective html file
        while (wordQueue.length() > 0) {
            generateWordFile(outputFolder, wordQueue.dequeue(), words);
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
