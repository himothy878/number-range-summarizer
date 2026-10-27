package numberrangesummarizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberRangeSummarizerImplTest {

    private NumberRangeSummarizer summarizer;

    @BeforeEach
    public void setUp() {
        summarizer = new NumberRangeSummarizerImpl();
    }

    // helper so each test can go from input string to result in one line
    private String summarize(String input) {
        return summarizer.summarizeCollection(summarizer.collect(input));
    }

    // ---------- basic behaviour ----------

    /*
     * Assumption: the example in the interface says "13-15", but the input contains 12, 13, 14, 15,
     * so I am assuming this is a typo and the correct result is "12-15".
     */
    @Test
    public void testSampleInputFromInterface() {
        String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", summarize(input));
    }

    @Test
    public void testSingleNumber() {
        assertEquals("5", summarize("5"));
    }

    @Test
    public void testNoSequentialNumbers() {
        assertEquals("1, 3, 5, 7", summarize("1,3,5,7"));
    }

    @Test
    public void testAllNumbersSequential() {
        assertEquals("1-5", summarize("1,2,3,4,5"));
    }

    @Test
    public void testCollectReturnsTheNumbers() {
        Collection<Integer> result = summarizer.collect("1,3,6");
        assertEquals(Arrays.asList(1, 3, 6), new ArrayList<>(result));
    }

    // ---------- assumptions ----------

    // Assumption: two numbers next to each other already count as a range.
    @Test
    public void testTwoSequentialNumbersMakeARange() {
        assertEquals("6-7", summarize("6,7"));
    }

    // Assumption: the input might not be sorted, so the numbers are sorted first.
    @Test
    public void testUnsortedInputIsSorted() {
        assertEquals("1-3, 7-8", summarize("8,3,1,7,2"));
    }

    // Assumption: duplicate numbers are only counted once.
    @Test
    public void testDuplicatesAreIgnored() {
        assertEquals("1-3", summarize("1,1,2,2,3"));
    }

    // Assumption: spaces around the numbers are allowed.
    @Test
    public void testSpacesAreIgnored() {
        assertEquals("1-2, 4", summarize(" 1 , 2,  4 "));
    }

    // Assumption: negative numbers are allowed and use the same "start-end" format.
    @Test
    public void testNegativeNumbers() {
        assertEquals("-3--1, 5", summarize("-3,-2,-1,5"));
    }

    // Assumption: empty or null input gives an empty result instead of an error.
    @Test
    public void testEmptyInput() {
        assertTrue(summarizer.collect("").isEmpty());
        assertEquals("", summarize(""));
        assertEquals("", summarize("   "));
    }

    @Test
    public void testNullInput() {
        assertTrue(summarizer.collect(null).isEmpty());
        assertEquals("", summarizer.summarizeCollection(null));
    }

    // Assumption: anything that is not a whole number is invalid and an exception is thrown.
    @Test
    public void testInvalidInputThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> summarizer.collect("1,abc,3"));
        assertThrows(IllegalArgumentException.class, () -> summarizer.collect("1,2.5,3"));
        assertThrows(IllegalArgumentException.class, () -> summarizer.collect("1,,3"));
    }

    // summarizeCollection should not change the list that was passed in
    @Test
    public void testInputCollectionIsNotChanged() {
        Collection<Integer> numbers = new ArrayList<>(Arrays.asList(3, 1, 2));
        summarizer.summarizeCollection(numbers);
        assertEquals(Arrays.asList(3, 1, 2), numbers);
    }
}
