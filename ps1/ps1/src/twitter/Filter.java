/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        List<Tweet> result = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getAuthor().equalsIgnoreCase(username)) {
                result.add(tweet);
            }
        }
        return result;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        List<Tweet> result = new ArrayList<>();
        Instant start = timespan.getStart();
        Instant end = timespan.getEnd();

        for (Tweet tweet : tweets) {
            Instant timestamp = tweet.getTimestamp();
            if ((timestamp.equals(start) || timestamp.isAfter(start)) &&
                    (timestamp.equals(end) || timestamp.isBefore(end))) {
                result.add(tweet);
            }
        }
        return result;
    }

    /**
     * Find tweets that contain certain words.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        List<Tweet> result = new ArrayList<>();

        for (Tweet tweet : tweets) {
            String text = tweet.getText().toLowerCase();
            for (String word : words) {
                if (text.contains(word.toLowerCase())) {
                    result.add(tweet);
                    break; // move to next tweet after first match
                }
            }
        }

        return result;
    }
}
