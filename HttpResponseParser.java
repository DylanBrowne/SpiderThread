import java.util.HashMap;
import java.util.Map;

public class HttpResponseParser {

    
    public HttpResponseParser() {

    }

    public int getStatus(String theResponse) {
        int currentIndex = 0;
        StringBuilder result = new StringBuilder();

        // Keep incrementing until the first whitespace is encountered.
        while (currentIndex < theResponse.length() && theResponse.charAt(currentIndex) != ' ') {
            currentIndex++;
        }

        // Skip any whitespaces between protocol and status code
        while (currentIndex < theResponse.length() && theResponse.charAt(currentIndex) == ' ') {
            currentIndex++;
        }

        // Collect digits until the next whitespace
        while (currentIndex < theResponse.length() && Character.isDigit(theResponse.charAt(currentIndex))) {
            result.append(theResponse.charAt(currentIndex));
            currentIndex++;
        }

        if (result.length() == 0) {
            throw new IllegalArgumentException("No status code found.");
        }

        int statusCode = Integer.parseInt(result.toString());

        if (!isValidHttpStatusCode(statusCode)) {
            throw new IllegalArgumentException("Not a valid status code: " + statusCode);
        }

        return statusCode;
    }

    public HashMap<String, String> getHeaders(String theResponse) {
        HashMap<String, String> headers = new HashMap<>();
        int currentIndex = 0;

        while (currentIndex < theResponse.length()) {
            // Date: Mon, 14 Sep 2026 20:08:22 GMT\r\nContent-Type: text/html\r\n\r\n

            StringBuilder name = new StringBuilder(); // Reset or instantiate the name var
            StringBuilder value = new StringBuilder(); // Reset or instantiate the value var


            // Return if the end is reached
            if ((theResponse.length() - currentIndex) >= 2
                && theResponse.substring(currentIndex, currentIndex + 2).equals("\r\n")) {
                
                return headers;
            }

            // Add the current character to *name* if it is before the colon
            while (currentIndex < theResponse.length() && theResponse.charAt(currentIndex) != ':') {
                name.append(theResponse.charAt(currentIndex));
                currentIndex++;
            }

            if (currentIndex < theResponse.length() && theResponse.charAt(currentIndex) == ':') {
                currentIndex++; // Accounts for the colon ':'
            }

            // Add the current character to *value* if it is before the string "\r\n"
            while ((theResponse.length() - currentIndex) >= 2
                && !theResponse.substring(currentIndex, currentIndex + 2).equals("\r\n")) {

                value.append(theResponse.charAt(currentIndex));
                currentIndex++;
            }

            // Make sure we are not at the end of the string and that the next two characters are in fact 'r' and 'n'
            if ((theResponse.length() - currentIndex) >= 2 
                && theResponse.substring(currentIndex, currentIndex + 2).equals("\r\n")) {

                currentIndex += 2; // Accounts for the last string "\r\n"
            }

            headers.put(name.toString(), value.toString()); // Store the name and value info gathered
        }

        return headers;
    }

    public String getHtml(String theResponse) {
        return "";
    }

    public boolean isValidHttpStatusCode(final int theStatusCode) {
        return theStatusCode >= 100 && theStatusCode <= 599;
    }

}