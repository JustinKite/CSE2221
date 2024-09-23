import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;

/**
 *
 * @author Justin Imber
 *
 */
public class StringReassemblyTest {

    /*
     * combination method test cases
     */

    @Test
    public void testCombination_untested_tested() {
        String expected = "untested code";
        String str1 = "untested";
        String str2 = "tested code";
        final int overlap = 6;
        String combination = StringReassembly.combination(str1, str2, overlap);
        assertEquals(expected, combination);
    }

    @Test
    public void testCombination_we_the_people() {
        String expected = "we, the people, are great";
        String str1 = "we, the people";
        String str2 = "the people, are great";
        final int overlap = 10;
        String combination = StringReassembly.combination(str1, str2, overlap);
        assertEquals(expected, combination);
    }

    @Test
    public void testCombination_corrupted_potato() {
        String expected = "the potato is indeed quite corrupted";
        String str1 = "the potato is indeed qu";
        String str2 = "quite corrupted";
        final int overlap = 2;
        String combination = StringReassembly.combination(str1, str2, overlap);
        assertEquals(expected, combination);
    }

    /*
     * addToSetAvoidingSubstrings method test cases
     */

    @Test
    public void testAddToSetAvoidingSubstrings_substring_contained() {
        Set<String> expected = new Set1L<>();
        String str = "this is a test case";
        expected.add("the president of the US");
        expected.add("campaign wars");
        expected.add("this is a test case");
        expected.add("we, the people");
        Set<String> strSet = new Set1L<>();
        strSet.add("the president of the US");
        strSet.add("campaign wars");
        strSet.add("a test");
        strSet.add("we, the people");
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(expected, strSet);
    }

    @Test
    public void testAddToSetAvoidingSubstrings_new_entry() {
        Set<String> expected = new Set1L<>();
        String str = "a wonder of the world";
        expected.add("the president of the US");
        expected.add("campaign wars");
        expected.add("this is a test case");
        expected.add("a wonder of the world");
        expected.add("we, the people");
        Set<String> strSet = new Set1L<>();
        strSet.add("the president of the US");
        strSet.add("campaign wars");
        strSet.add("this is a test case");
        strSet.add("we, the people");
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(expected, strSet);
    }

    @Test
    public void testAddToSetAvoidingSubstrings_is_substring() {
        Set<String> expected = new Set1L<>();
        String str = "wonder of";
        expected.add("the president of the US");
        expected.add("campaign wars");
        expected.add("this is a test case");
        expected.add("a wonder of the world");
        expected.add("we, the people");
        Set<String> strSet = new Set1L<>();
        strSet.add("the president of the US");
        strSet.add("campaign wars");
        strSet.add("this is a test case");
        strSet.add("a wonder of the world");
        strSet.add("we, the people");
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(expected, strSet);
    }
}
