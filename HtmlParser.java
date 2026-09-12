public class HtmlParser {

    public HtmlParser() {

    }

    public void parse(final String theHtml) {
        int currentIndex = 0;
        String body = "";
        // Traverse the entire HTML
        while (currentIndex < theHtml.length()) {
            //System.out.println("Current index: " + currentIndex);
            //System.out.println("Current char: " + theHtml.charAt(currentIndex));
            char currentChar = theHtml.charAt(currentIndex);

            // Parse the tag
            if (currentChar == '<') {
                String tag = getTag(currentIndex + 1, theHtml); // (currentIndex + 1) to get past the starting '<'
                System.out.println("Tag: " + tag);
                currentIndex += tag.length() + 2; // (tag.length() + 2) to account for '<' and '>'

            // Parse the text
            } else {
                body = getBody(currentIndex, theHtml);
                System.out.println("Body: " + body);
                currentIndex += body.length();
                
            }
            //currentIndex++;
        }
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