import java.util.Set;
import java.util.HashSet;

// This class takes HTML content fetched by PageFetcher.java, parses it, and extracts links to be crawled further.
public class WebCrawler {

    // Keeps track of URLs already visited to prevent infinite loops
    private HashSet<String> myVisitedUrls;

    private String myHTML;

    // Constructor
    public WebCrawler(final String theHTML) {
        myVisitedUrls = new HashSet<>();
        myHTML = theHTML;
    }

    public String parse() {
        if (!isVisited()) {
            myVisitedUrls.add(myURL);
            // Parsing logic:
            
        } else {
            System.out.println("This URL has already been visited.");
        }
    }

    // Checks if the URL has already been visited or not
    public boolean isVisited() {
        return visitedUrls.contains(myHTML);
    }
}