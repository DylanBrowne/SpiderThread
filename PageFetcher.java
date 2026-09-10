import java.net.Socket;
import java.net.ssl.SSLSocket;
import java.net.ssl.SSLSocketFactory;


// This class takes a URL and fetches its raw HTML content to be passed to WebCrawler.java
public class PageFetcher {

    private String myURL;

    // Constructor
    public PageFetcher(final String theURL) {
        myURL = theURL;
    }

    public static void fetch(String url) {
        String hostName = getHostName();
        int portNumber = getPortNumber();

        try {
            Socket echoSocket = new Socket(hostName, portNumber);
        }
    }

    private static String getHostName() {
        // 0123456789
        // https://www.google.com/blah/blah
        int start = myURL.indexOf("://") + 3;
        int end = myURL.indexOf("/", start);

        return myURL.substring(start, end);
    }

    private static int getPortNumber() {
        if (myURL.toLowerCase().startsWith("https://")) {
            return 443;
        } else if (myURL.toLowerCase().startsWith("http://")) {
            return 80;
        } else {
            throw new IllegalArgumentException("Unsupported or missing protocol in URL: " + myURL);
        }
    }

    
}