/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * Testing strategy:
     *
     * writtenBy():
     *   - username matches none
     *   - username matches one
     *   - username matches multiple (case insensitive)
     *
     * inTimespan():
     *   - tweet before timespan
     *   - tweet during timespan
     *   - tweet after timespan
     *   - boundary equals start or end
     *
     * containing():
     *   - one matching word
     *   - multiple matching words
     *   - case-insensitive match
     *   - no matching word
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "Let's discuss algorithms later.", d3);

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // --- writtenBy() tests ---
    @Test
    public void testWrittenByNoMatch() {
        List<Tweet> result = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "charlie");
        assertTrue("expected empty list", result.isEmpty());
    }

    @Test
    public void testWrittenBySingleMatch() {
        List<Tweet> result = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        assertEquals("expected one result", 1, result.size());
        assertTrue("expected to contain tweet1", result.contains(tweet1));
    }

    @Test
    public void testWrittenByCaseInsensitiveMultipleMatches() {
        List<Tweet> result = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "ALYSSA");
        assertEquals("expected two results", 2, result.size());
        assertTrue(result.contains(tweet1) && result.contains(tweet3));
    }

    // --- inTimespan() tests ---
    @Test
    public void testInTimespanWithinRange() {
        Instant start = Instant.parse("2016-02-17T09:00:00Z");
        Instant end = Instant.parse("2016-02-17T12:30:00Z");
        List<Tweet> result = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3), new Timespan(start, end));
        assertEquals("expected all tweets in range", 3, result.size());
    }

    @Test
    public void testInTimespanBoundaryConditions() {
        Instant start = Instant.parse("2016-02-17T10:00:00Z");
        Instant end = Instant.parse("2016-02-17T11:00:00Z");
        List<Tweet> result = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3), new Timespan(start, end));
        assertTrue("expected tweet1 and tweet2 only", result.containsAll(Arrays.asList(tweet1, tweet2)));
        assertFalse("tweet3 outside range", result.contains(tweet3));
    }

    // --- containing() tests ---
    @Test
    public void testContainingSingleWordMatch() {
        List<Tweet> result = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        assertTrue("expected both tweets contain 'talk'", result.containsAll(Arrays.asList(tweet1, tweet2)));
    }

    @Test
    public void testContainingMultipleWords() {
        List<Tweet> result = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList("rivest", "algorithms"));
        assertTrue("expected all tweets containing one of the words", result.containsAll(Arrays.asList(tweet1, tweet2, tweet3)));
    }

    @Test
    public void testContainingCaseInsensitive() {
        List<Tweet> result = Filter.containing(Arrays.asList(tweet1), Arrays.asList("RIVEST"));
        assertTrue("expected case-insensitive match", result.contains(tweet1));
    }

    @Test
    public void testContainingNoMatch() {
        List<Tweet> result = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("banana"));
        assertTrue("expected empty list", result.isEmpty());
    }
}
