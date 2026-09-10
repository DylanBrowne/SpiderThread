import java.net.Socket;
import java.net.ssl.SSLSocket;
import java.net.ssl.SSLSocketFactory;


// This class takes a URL and fetches its raw HTML content to be passed to WebCrawler.java
public class PageFetcher {

    private String myURL;

    // Constructor
    public PageFetcher(final String theURL) {
        if (theURL == null) {
            throw new IllegalArgumentException("URL field not provided.");
        }

        myURL = theURL;
    }

    public void fetch() {
        String hostName = getHostName();
        int portNumber = getPortNumber();

        try {
            Socket socket = new Socket(hostName, portNumber);
        }
    }


    // Extracts the host name from the URL, which is the part between "://" and the next "/"
    private String getHostName() {
        if (myURL == null) return null;
        // 0123456789
        // https://www.google.com/blah/blah
        int start = myURL.indexOf("://") + 3;
        int end = myURL.indexOf("/", start);

        if (end == -1) {
            end = myURL.length(); // Handle URLs without a trailing path like "https://google.com"
        }

        return myURL.substring(start, end);
    }

    // Determines the port number based on the protocol specified in the URL. Defaults to 80 for HTTP and 443 for HTTPS. Throws an exception for unsupported protocols.
    private int getPortNumber() {
        if (myURL.toLowerCase().startsWith("https://")) {
            return 443;
        } else if (myURL.toLowerCase().startsWith("http://")) {
            return 80;
        } else {
            throw new IllegalArgumentException("Unsupported or missing protocol in URL: " + myURL);
        }
    }

    // Extracts the path from the URL, which is everything after the host name
    private String getPath() {
        String host = getHostName();
        int pathStart = myURL.indexOf(host) + host.length();

        if (pathStart >= myURL.length()) {
            return "/";
        }

        return myURL.substring(pathStart);
    }

    
}