// ARCHIVED:
// Replaced by Java's built-in HttpClient API.
// Retained for reference to manual HTTP response parsing.

package model;

import java.util.HashMap;
import java.util.Map;

// This class takes a raw HTTP response and parses it to extract the status code, headers, and HTML content.
public class HttpResponseParser {

    /** Keeps track of the current index in the response string while parsing */
    private int myCurrentIndex;
    
    /** Constructor initializes the current index to 0 */
    public HttpResponseParser() {
        myCurrentIndex = 0;
    }

    /**
     * Parses the raw HTTP response and returns the HTML content as a string.
     * 
     * @param theResponse the HTML response provided by the URL.
     * @return the HTML parsed including the status code, headers, and chunk size.
     */
    public String parse(String theResponse) {
        myCurrentIndex = 0; // Reset index if a method has already been called that altered the index
        System.out.println("Status: " + getStatus(theResponse));
        Map<String, String> headers = getHeaders(theResponse);
        
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value);
        }

        StringBuilder html = new StringBuilder();
        
        if (headers.containsKey("Transfer-Encoding")) {
            int chunkSize = getChunkSize(theResponse);        
            

            while (chunkSize != 0) {
                System.out.println("-----------------------------------------------------------------");
                System.out.println("Chunk size: " + chunkSize);
                html.append(getHtml(theResponse, chunkSize));

                chunkSize = getChunkSize(theResponse);
                // Prints the HTML including the invisible chars '\n' and '\r'
                // System.out.println("HTML: \n " + html.toString().replace("\r", "\\r").replace("\n", "\\n"));
            }
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Chunk size: " + chunkSize);
            System.out.println("-----------------------------------------------------------------");
        } else if (headers.containsKey("Content-Length")) {
            System.out.println("PARSE");
            int bytes = Integer.parseInt(headers.get("Content-Length"));
            
            html.append(getHtml(theResponse, bytes));
            // Prints the HTML including the invisible chars '\n' and '\r'
            System.out.println("HTML: \n " + html.toString().replace("\r", "\\r").replace("\n", "\\n"));
            

        }
        System.out.println("Return");
        return html.toString();
    }

    /**
     * Returns the HTTP status code from the response.
     * 
     * @param theResponse the HTML response provided by the URL.
     * @return the status code.
     */
    public int getStatus(String theResponse) {
        //int myCurrentIndex = 0;
        StringBuilder result = new StringBuilder();

        // Keep incrementing until the first whitespace is encountered.
        while (myCurrentIndex < theResponse.length() && theResponse.charAt(myCurrentIndex) != ' ') {
            myCurrentIndex++;
        }

        // Skip any whitespaces between protocol and status code
        while (myCurrentIndex < theResponse.length() && theResponse.charAt(myCurrentIndex) == ' ') {
            myCurrentIndex++;
        }

        // Collect digits until the next whitespace
        while (myCurrentIndex < theResponse.length() && Character.isDigit(theResponse.charAt(myCurrentIndex))) {
            result.append(theResponse.charAt(myCurrentIndex));
            myCurrentIndex++;
        }

        if (result.length() == 0) {
            throw new IllegalArgumentException("No status code found.");
        }

        int statusCode = Integer.parseInt(result.toString());

        if (!isValidHttpStatusCode(statusCode)) {
            throw new IllegalArgumentException("Not a valid status code: " + statusCode);
        }

        while ((theResponse.length() - myCurrentIndex) >= 2
                && !theResponse.substring(myCurrentIndex, myCurrentIndex + 2).equals("\r\n")) {
            myCurrentIndex++;
        }

        if ((theResponse.length() - myCurrentIndex) >= 2
                && theResponse.substring(myCurrentIndex, myCurrentIndex + 2).equals("\r\n")) {
            myCurrentIndex += 2; // Consume the \r\n at the end of the status code
        }
        return statusCode;
    }

    /**
     * Parses the headers from the HTTP response and returns them as a map.
     * 
     * @param theResponse the HTML response provided by the URL.
     * @return the headers mapped name to value.
     */
    public Map<String, String> getHeaders(String theResponse) {
        Map<String, String> headers = new HashMap<>();
        //int myCurrentIndex = 0;

        while (myCurrentIndex < theResponse.length()) {
            // Date: Mon, 14 Sep 2026 20:08:22 GMT\r\nContent-Type: text/html\r\n\r\n

            // Return if the end is reached
            if ((theResponse.length() - myCurrentIndex) >= 2
                && theResponse.substring(myCurrentIndex, myCurrentIndex + 2).equals("\r\n")) {

                myCurrentIndex += 2;

                return headers;
            }

            StringBuilder name = new StringBuilder(); // Reset or instantiate the name var
            StringBuilder value = new StringBuilder(); // Reset or instantiate the value var


            // Add the current character to *name* if it is before the colon
            while (myCurrentIndex < theResponse.length() 
                    && theResponse.charAt(myCurrentIndex) != ':') {
            
                name.append(theResponse.charAt(myCurrentIndex));
                myCurrentIndex++;
            }

            if (myCurrentIndex < theResponse.length() 
                    && theResponse.charAt(myCurrentIndex) == ':') {

                myCurrentIndex++; // Accounts for the colon ':'
            }

            // Add the current character to *value* if it is before the string "\r\n"
            while ((theResponse.length() - myCurrentIndex) >= 2
                    && !theResponse.substring(myCurrentIndex, myCurrentIndex + 2).equals("\r\n")) {

                value.append(theResponse.charAt(myCurrentIndex));
                myCurrentIndex++;
            }

            // Make sure we are not at the end of the string and that the next two characters are in fact 'r' and 'n'
            if ((theResponse.length() - myCurrentIndex) >= 2 
                && theResponse.substring(myCurrentIndex, myCurrentIndex + 2).equals("\r\n")) {

                myCurrentIndex += 2; // Accounts for the last string "\r\n"
            }

            headers.put(name.toString(), value.toString().stripLeading()); // Store the name and value info gathered
        }

        return headers;
    }

    /**
     * Returns the size of the next chunk in the HTTP response.
     * 
     * @param theResponse the HTML response provided by the URL.
     * @return the chunk size in hexadecimal bytes.
     */
    public int getChunkSize(String theResponse) {
        StringBuilder chunkSizeHex = new StringBuilder();
        int chunkSize = 0;

        while (myCurrentIndex < theResponse.length() 
                && !(theResponse.length() - myCurrentIndex >= 2
                && theResponse.substring(myCurrentIndex, myCurrentIndex + 2).equals("\r\n"))) {

            chunkSizeHex.append(theResponse.charAt(myCurrentIndex));
            myCurrentIndex++;
        }


        if (chunkSizeHex.length() > 0 && isHex(chunkSizeHex.toString())) {
            chunkSize = hexToDecimal(chunkSizeHex.toString());
            myCurrentIndex += 2;
        } else {
            throw new IllegalArgumentException("Invalid or no chunk size given: " + chunkSizeHex);
        }

        return chunkSize;
    }

    /**
     * Returns the HTML content from the HTTP response based on the given size.
     * 
     * @param theResponse the HTML response provided by the URL.
     * @param theChunkSize the chunk size in hexadecimal bytes.
     * @return The entire HTML as a String.
     */
    public String getHtml(String theResponse, int theSize) {
        
        //int chunkSize = getChunkSize(theResponse);
        StringBuilder html = new StringBuilder();

        for (int i = 0; i < theSize; i++) {
            html.append(theResponse.charAt(myCurrentIndex));
            myCurrentIndex++;
        }
        myCurrentIndex += 2;

        return html.toString();
    }

    /**
     * Checks if the given string is a valid hexadecimal number.
     * 
     * @param theValue the chunk size.
     * @return true if the given value is hexadecimal; false otherwise.
     */
    public boolean isHex(String theValue) {
        for (int i = 0; i < theValue.length(); i++) {
            char c = theValue.charAt(i);

            if (!Character.isDigit(c)
                && !(c >= 'a' && c <= 'f')
                && !(c >= 'A' && c <= 'F')) {
                    return false;
            }
        }
        return !theValue.isEmpty();
    }

    /**
     * Converts a hexadecimal string to its decimal equivalent.
     * 
     * @param theHex the hexadecimal value.
     * @return the decimal equivalent of the hexadecimal input.
     */
    public static int hexToDecimal(String theHex) {
        String digits = "0123456789ABCDEF";
        theHex = theHex.toUpperCase();
        int val = 0;
        for (int i = 0; i < theHex.length(); i++) {
            val = 16 * val + digits.indexOf(theHex.charAt(i));
        }
        return val;
    }

    /**
     * Checks if the given status code is a valid HTTP status code (between 100 and 599).
     * 
     * @param theStatusCode the status code given by the URL.
     * @return true if the status code is a valid code; false otherwise.
     */
    public boolean isValidHttpStatusCode(final int theStatusCode) {
        return theStatusCode >= 100 && theStatusCode <= 599;
    }

}