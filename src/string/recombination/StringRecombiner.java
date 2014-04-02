package string.recombination;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author craigkj
 */
public class StringRecombiner {

    /**
     * Parse incoming file
     * @param args 
     */
    public void ParseFile(String[] args) {
        try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
            String fragmentProblem;
            while ((fragmentProblem = in.readLine()) != null) {
                System.out.println(reassemble(fragmentProblem));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reassembles a series of text fragments into a single string based on the
     * overlapping regions between them.
     *
     * Assumes the following: String is UTF-8 encoded No mistakes are contained
     * in the input text There is a single unambiguous solution
     *
     * @param fragmentProblem - the delimited line of text to be recombined
     * @return recombined - the recombined string
     */
    private static String reassemble(final String fragmentProblem) {

        ArrayList<String> fragments =
                new ArrayList<>(Arrays.asList(fragmentProblem.split(":")));

        if (fragments.isEmpty()) {
            return "";
        }

        // get and remove first element to start.
        String recombined = fragments.get(0);
        fragments.remove(recombined);

        // cycle through rest of the strings and check for overlap.
        int bestOverlap = 0;
        String overlapping = "";
        int numberOfLoops = 0;
        int maxLoops = fragments.size();

        while (fragments.size() > 0 && numberOfLoops < maxLoops) {
            for (final String fragment : fragments) {

                // Check if the recombined overlaps the fragment
                int overlap = overlap(recombined, fragment);
                if (overlap > bestOverlap) {
                    bestOverlap = overlap;
                    overlapping = fragment;
                }

                // check the inverse of the above assertion
                overlap = overlap(fragment, recombined);
                if (overlap > bestOverlap) {
                    bestOverlap = overlap;
                    overlapping = fragment;
                }

                // check whether the fragment is already contained entirely
                if (recombined.contains(fragment)) {
                    bestOverlap = fragment.length();
                    overlapping = fragment;
                } // check whether the frament contains the recombined string
                else if (fragment.contains(recombined)) {
                    bestOverlap = recombined.length();
                    recombined = fragment;
                    overlapping = fragment;
                }
            }
            recombined = recombine(recombined, overlapping, bestOverlap);
            fragments.remove(overlapping);
            bestOverlap = 0;
            overlapping = "";
            numberOfLoops++;
        }

        return recombined;
    }

    /**
     * Recombine the two strings on the provided overlapping area.
     *
     * Outcomes 1 - The last 'overlap' characters of str1 match the first
     * 'overlap' characters of str2 2 - The first 'overlap' characters of str2
     * match the last 'overlap' characters of str1
     *
     * @param str1 - the working string
     * @param str2 - to combine with
     * @param overlap - the length of the overlap
     * @return - The combined string with the overlapping region removed from
     * one of the source strings
     */
    private static String recombine(String str1, String str2, int overlap) {

        if (str1.substring((str1.length() - overlap), str1.length())
                .equals(str2.substring(0, overlap))) {
            // end of 1 matches start of 2
            str2 = str2.substring(overlap, str2.length());

            return str1 + str2;
            // check the opposite
        } else if (str2.substring((str2.length() - overlap), str2.length())
                .equals(str1.substring(0, overlap))) {
            str1 = str1.substring(overlap, str1.length());

            return str2 + str1;
        } else {
            // There appears to be no overlap

            return str1;
        }
    }

    /**
     * Calculates the maximum length of overlapping regions between two strings
     *
     * @param str1 - String to be compared
     * @param str2 - String to be compared
     * @return maxOverlap - length of maximum overlap
     */
    private static int overlap(final String str1, final String str2) {

        // start at the longest possible length and work down
        int maxOverlap = str2.length() - 1;
        while (!str1.regionMatches(str1.length() - maxOverlap, str2, 0,
                maxOverlap)) {
            maxOverlap--;
        }
        return maxOverlap;
    }
}
