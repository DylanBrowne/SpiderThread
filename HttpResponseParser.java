import java.util.HashMap;
import java.util.Map;

public class HttpResponseParser {
    private int currentIndex;
    
    public HttpResponseParser() {
        this.currentIndex = 0;
    }

    public String parse(String theResponse) {
        currentIndex = 0; // Reset index if a method has already been called that altered the index
        System.out.println("Status: " + getStatus(theResponse));
        Map<String, String> headers = getHeaders(theResponse);
        
        // for (Map.Entry<String, String> entry : headers.entrySet()) {
        //     String key = entry.getKey();
        //     String value = entry.getValue();
        //     String str = "Key: " + key.replace("\r", "\\r").replace("\n", "\\n") + "(INDEX " + currentIndex + " CHAR " + theResponse.charAt(currentIndex - 3) + "), Value: " + value.replace("\r", "\\r").replace("\n", "\\n");
        //     System.out.println(str);
        // }

        int chunkSize = getChunkSize(theResponse);        
        StringBuilder html = new StringBuilder();

        while (chunkSize != 0) {
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Chunk size: " + chunkSize);
            html.append(getHtml(theResponse, chunkSize));

            chunkSize = getChunkSize(theResponse);
            System.out.println("HTML: \n " + html.toString());
        }
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Chunk size: " + chunkSize);
        System.out.println("-----------------------------------------------------------------");
        return html.toString();
    }

    public int getStatus(String theResponse) {
        //int currentIndex = 0;
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

        while ((theResponse.length() - currentIndex) >= 2
                && !theResponse.substring(currentIndex, currentIndex + 2).equals("\r\n")) {
            currentIndex++;
        }

        if ((theResponse.length() - currentIndex) >= 2
                && theResponse.substring(currentIndex, currentIndex + 2).equals("\r\n")) {
            currentIndex += 2; // Consume the \r\n at the end of the status code
        }
        return statusCode;
    }

    public Map<String, String> getHeaders(String theResponse) {
        Map<String, String> headers = new HashMap<>();
        //int currentIndex = 0;

        while (currentIndex < theResponse.length()) {
            // Date: Mon, 14 Sep 2026 20:08:22 GMT\r\nContent-Type: text/html\r\n\r\n

            // Return if the end is reached
            if ((theResponse.length() - currentIndex) >= 2
                && theResponse.substring(currentIndex, currentIndex + 2).equals("\r\n")) {

                currentIndex += 2;

                return headers;
            }

            StringBuilder name = new StringBuilder(); // Reset or instantiate the name var
            StringBuilder value = new StringBuilder(); // Reset or instantiate the value var


            // Add the current character to *name* if it is before the colon
            while (currentIndex < theResponse.length() 
                    && theResponse.charAt(currentIndex) != ':') {
            
                name.append(theResponse.charAt(currentIndex));
                currentIndex++;
            }

            if (currentIndex < theResponse.length() 
                    && theResponse.charAt(currentIndex) == ':') {

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

            headers.put(name.toString(), value.toString().stripLeading()); // Store the name and value info gathered
        }

        return headers;
    }

    public int getChunkSize(String theResponse) {
        StringBuilder chunkSizeHex = new StringBuilder();
        int chunkSize = 0;

        while (currentIndex < theResponse.length() 
                && !(theResponse.length() - currentIndex >= 2
                && theResponse.substring(currentIndex, currentIndex + 2).equals("\r\n"))) {

            chunkSizeHex.append(theResponse.charAt(currentIndex));
            currentIndex++;
        }


        if (chunkSizeHex.length() > 0 && isHex(chunkSizeHex.toString())) {
            chunkSize = hexToDecimal(chunkSizeHex.toString());
            currentIndex += 2;
        } else {
            throw new IllegalArgumentException("Invalid or no chunk size given: " + chunkSizeHex);
        }

        return chunkSize;
    }

    public String getHtml(String theResponse, int theChunkSize) {
        
        //int chunkSize = getChunkSize(theResponse);
        StringBuilder html = new StringBuilder();

        for (int i = 0; i < theChunkSize; i++) {
            html.append(theResponse.charAt(currentIndex));
            currentIndex++;
        }
        currentIndex += 2;

        return html.toString();
    }

    public boolean isHex(String value) {
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);

            if (!Character.isDigit(c)
                && !(c >= 'a' && c <= 'f')
                && !(c >= 'A' && c <= 'F')) {
                    return false;
            }
        }
        return !value.isEmpty();
    }

    public static int hexToDecimal(String theHex) {
        String digits = "0123456789ABCDEF";
        theHex = theHex.toUpperCase();
        int val = 0;
        for (int i = 0; i < theHex.length(); i++) {
            val = 16 * val + digits.indexOf(theHex.charAt(i));
        }
        return val;
    }

    public boolean isValidHttpStatusCode(final int theStatusCode) {
        return theStatusCode >= 100 && theStatusCode <= 599;
    }

}