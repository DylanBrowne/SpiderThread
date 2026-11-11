package model;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;


// This class takes a URL and fetches its raw HTML content to be passed to WebCrawler.java
public class PageFetcher {

    /** The URL being requested */
    final private String myURL;

    /**
     * Constructs a PageFetcher using the specified URL.
     * Validates that the provided URL is not null or empty before storing it.
     *
     * @param theURL the URL of the webpage to fetch
     * @throws IllegalArgumentException if the provided URL is null or empty
     */
    public PageFetcher(final String theURL) {
        if (theURL == null || theURL.trim().isEmpty()) {
            throw new IllegalArgumentException("URL field not provided.");
        }

        myURL = theURL;
    }

    /**
     * Fetches the HTML response from the specified host using an HTTP GET request.
     * Establishes a secure connection using {@link SSLSocket} when connecting
     * through port 443, or a regular {@link Socket} for other ports.
     * Reads the server's response until the connection is closed and returns
     * the response as a string.
     *
     * @return the HTTP response received from the server, or an empty string
     * if an exception occurs
     */
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
            InputStream in = socket.getInputStream();
            
            int character;

            // Print the HTML
            while ((character = in.read()) != -1) {
                html.append((char) character);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html.toString();
    }


    /**
     * Extracts the host name from the stored URL.
     * Identifies the host by locating the characters following the protocol
     * separator ("://") and ending at the next forward slash or the end
     * of the URL if no path is present.
     *
     * @return the host name extracted from the URL, or null if the URL is null
     */
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

    /**
     * Determines the port number based on the protocol specified in the stored URL.
     * Uses port 443 for HTTPS connections and port 80 for HTTP connections.
     *
     * @return the port number corresponding to the URL protocol
     * @throws IllegalArgumentException if the URL uses an unsupported protocol
     * or does not specify a protocol
     */
    private int getPortNumber() {
        if (myURL.toLowerCase().startsWith("https://")) {
            return 443;
        } else if (myURL.toLowerCase().startsWith("http://")) {
            return 80;
        } else {
            throw new IllegalArgumentException("Unsupported or missing protocol in URL: " + myURL);
        }
    }

    /**
     * Extracts the resource path from the stored URL.
     * Locates the portion of the URL immediately following the host name.
     * Returns "/" if the URL does not contain a path.
     *
     * @return the resource path specified in the URL, or "/" if no path exists
     */
    private String getPath() {
        String host = getHostName();
        int pathStart = myURL.indexOf(host) + host.length();

        if (pathStart >= myURL.length()) {
            return "/";
        }

        return myURL.substring(pathStart);
    }

    
}