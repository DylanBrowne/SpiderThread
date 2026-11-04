public class HtmlParser {

    public HtmlParser() {

    }

    // Parses the given HTML string and prints out the tags and text bodies
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

    // Extracts the tag from the HTML starting at the given index
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

    // Extracts the text body from the HTML starting at the given index
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