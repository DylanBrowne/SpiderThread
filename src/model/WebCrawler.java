package model;

import java.util.HashSet;

// This class takes HTML content fetched by PageFetcher.java, parses it, and extracts links to be crawled further.
public class WebCrawler {

    /** Keeps track of URLs already visited to prevent infinite loops */
    final private HashSet<String> myVisitedUrls;

    /** The HTML provided by the URL */
    final private String myHTML;

    /**
     * Constructs a WebCrawler using the specified HTML content.
     * Initializes an empty set to keep track of visited URLs and stores
     * the provided HTML content.
     *
     * @param theHTML the HTML content to be parsed by the crawler
     */
    public WebCrawler(final String theHTML) {
        myVisitedUrls = new HashSet<>();
        myHTML = theHTML;
    }

    /**
     * Parses the current webpage if its URL has not already been visited.
     * Checks whether the URL exists in the visited URLs collection before
     * proceeding with the parsing logic. Prints a message if the URL has
     * already been visited.
     *
     * @return the parsed content of the webpage, or an empty string if the
     * URL has already been visited or no content is produced
     */
    public String parse() {
        if (!isVisited()) {
            // myVisitedUrls.add(myURL);
            // Parsing logic:
            
        } else {
            System.out.println("This URL has already been visited.");
        }

        return "";
    }

    /**
     * Checks whether the current HTML URL has already been visited.
     *
     * @return true if the current HTML URL exists in the visited URLs collection;
     * false otherwise
     */
    public boolean isVisited() {
        return myVisitedUrls.contains(myHTML);
    }
}