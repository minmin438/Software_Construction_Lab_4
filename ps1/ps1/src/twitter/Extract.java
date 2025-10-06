package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract {

    /**
     * Get the time period spanned by tweets.
     *
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of every
     *         tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            throw new IllegalArgumentException("Tweet list cannot be empty");
        }

        Instant start = tweets.get(0).getTimestamp();
        Instant end = start;

        for (Tweet t : tweets) {
            Instant time = t.getTimestamp();
            if (time.isBefore(start)) {
                start = time;
            }
            if (time.isAfter(end)) {
                end = time;
            }
        }

        return new Timespan(start, end);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     *
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets. A
     *         username-mention is "@" followed by a Twitter username (as defined by
     *         Tweet.getAuthor()'s spec). The username-mention cannot be immediately
     *         preceded or followed by any character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT
     *         contain a mention of the username mit. Twitter usernames are
     *         case-insensitive, and the returned set may include a username at most
     *         once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();
        Pattern pattern = Pattern.compile("(?<![A-Za-z0-9_])@([A-Za-z0-9_]+)(?![A-Za-z0-9_])");

        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                String username = matcher.group(1).toLowerCase();
                mentionedUsers.add(username);
            }
        }

        return mentionedUsers;
    }
}
