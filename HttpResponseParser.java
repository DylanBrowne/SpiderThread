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

    public String getHeaders(String theResponse) {
        return "";
    }

    public String getHtml(String theResponse) {
        return "";
    }

    public boolean isValidHttpStatusCode(final int theStatusCode) {
        return theStatusCode >= 100 && theStatusCode <= 599;
    }

}