import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;


// This class takes a URL and fetches its raw HTML content to be passed to WebCrawler.java
public class PageFetcher {

    final private String myURL;

    // Constructor
    public PageFetcher(final String theURL) {
        if (theURL == null || theURL.trim().isEmpty()) {
            throw new IllegalArgumentException("URL field not provided.");
        }

        myURL = theURL;
    }

    public String fetch() {
        final String hostName = getHostName();
        final int portNumber = getPortNumber();
        final String path = getPath();
        
        Socket socket;
        StringBuilder html = new StringBuilder();

        try {
            // Use SSLSocket for HTTPS (Port 443), regular Socket for HTTP (Port 80)
            if (portNumber == 443) {
                SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                socket = factory.createSocket(hostName, portNumber);
            } else {
                socket = new Socket(hostName, portNumber);
            }

            
            // Send HTTP request to GET the HTML
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET " + path + " HTTP/1.1");
            out.println("Host: " + hostName);
            out.println("Connection: close");
            out.println();

            // Receive HTTP response
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String line;

            // Print the HTML
            while ((line = in.readLine()) != null) {
                html.append(line);
                html.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html.toString();
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