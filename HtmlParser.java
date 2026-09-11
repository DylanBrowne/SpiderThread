public class HtmlParser {

    public HtmlParser() {

    }

    public void parse(final String theHtml) {
        int currentIndex = 0;

        // Traverse the entire HTML
        while (currentIndex != theHtml.length()) {

            char currentChar = theHtml.charAt(currentIndex);

            // Parse the tag
            if (currentChar == '<') {
                String tag = getTag(currentIndex + 1, theHtml); // (currentIndex + 1) to get past the starting '<'
                System.out.println("Tag: " + tag);
                currentIndex += tag.length() + 1; // (tag.length() + 1) to get past the ending '>'

            // Parse the text
            } else {

            }
            currentIndex++;
        }
    }

    // Extracts the tag from the HTML starting at the given index
    public String getTag(int theCurrentIndex, final String theHtml) {
        StringBuilder tag = new StringBuilder();
        char theCurrentChar = theHtml.charAt(theCurrentIndex);

        // Traverse until the end of the tag
        while (theCurrentChar != '>') {
            tag.append(theCurrentChar);
            theCurrentIndex++;
            theCurrentChar = theHtml.charAt(theCurrentIndex);
        }
        
        return tag.toString();
    }





}