package string.recombination;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class recombines string fragments with overlapping sections that are
 * delimited by ':' into a single string
 *
 * @author craigjones
 */
public class RecombinatorApplication {

    /**
     * This method takes the absolute path to a file and attempts to recombine
     * each fragmented line into a single string value.
     *
     * @param args the command line arguments - path to file
     */
    public static void main(String[] args) {
        StringRecombiner recombiner = new StringRecombiner();
        recombiner.ParseFile(args);
    }
}