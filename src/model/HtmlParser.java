package model;

public class HtmlParser {
    /**
     * Parses an HTML string by traversing its contents and identifying HTML tags
     * and text bodies. Extracts and prints each tag and its corresponding text
     * content as they are encountered.
     *
     * @param theHtml the HTML string to parse
     */
    public void parse(final String theHtml) {
        int currentIndex = 0;
        // Traverse the entire HTML
        while (currentIndex < theHtml.length() && theHtml.charAt(currentIndex) != '\n') {
            //System.out.println("Current index: " + currentIndex);
            //System.out.println("Current char: " + theHtml.charAt(currentIndex));
            char currentChar = theHtml.charAt(currentIndex);

            // Parse the tag
            if (currentChar == '<') {
                String tag = getTag(currentIndex + 1, theHtml); // (currentIndex + 1) to get past the starting '<'
                System.out.println("Tag: " + tag);
                currentIndex += tag.length() + 2; // (tag.length() + 2) to account for '<' and '>'

            // Parse the body
            } else {
                String body = getBody(currentIndex, theHtml);
                System.out.println("Body: " + body);
                currentIndex += body.length();
                
            }
            //currentIndex++;
        }
        currentIndex++; // moves past the last '\n' at the end of the HTML
    }

    /**
     * Extracts an HTML tag beginning at the specified index in an HTML string.
     * Traverses the HTML until the closing angle bracket ('>') is encountered.
     *
     * @param theCurrentIndex the starting index of the HTML tag
     * @param theHtml the HTML string containing the tag
     * @return the extracted tag content, excluding the closing angle bracket
     * @throws IllegalArgumentException if the tag reaches the end of the HTML
     * string without encountering a closing angle bracket
     */
    public String getTag(int theCurrentIndex, final String theHtml) {
        StringBuilder tag = new StringBuilder();
        char theCurrentChar = theHtml.charAt(theCurrentIndex);

        // Traverse until the end of the tag
        while (theCurrentChar != '>') {
            if (theCurrentIndex == theHtml.length() - 1) {
                throw new IllegalArgumentException("HTML tag is missing closing '>'");
            }
            tag.append(theCurrentChar);
            theCurrentIndex++;
            theCurrentChar = theHtml.charAt(theCurrentIndex);
        }
        
        return tag.toString();
    }

    /**
     * Extracts the text content beginning at the specified index in an HTML string.
     * Traverses the HTML until the beginning of the next tag ('<') is encountered.
     *
     * @param theCurrentIndex the starting index of the text content in the HTML string
     * @param theHtml the HTML string to extract text from
     * @return the extracted text content preceding the next HTML tag
     * @throws IllegalArgumentException if the text content reaches the end of the
     * HTML string without encountering another tag
     */
    public String getBody(int theCurrentIndex, final String theHtml) {
        StringBuilder body = new StringBuilder();
        char theCurrentChar = theHtml.charAt(theCurrentIndex);

        // Traverse until the beginning of the next tag
        while (theCurrentChar != '<') {
            if (theCurrentIndex == theHtml.length() - 1) {
                throw new IllegalArgumentException("HTML text body has no ending point");
            }
            body.append(theCurrentChar);
            theCurrentIndex++;
            theCurrentChar = theHtml.charAt(theCurrentIndex);
        }
        
        return body.toString();
    }





}