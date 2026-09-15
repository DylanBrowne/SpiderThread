import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;


public class Main {
    public static void main(String[] theArgs) {
        System.out.println("PROGRAM STARTED");
        // I want to develop a web crawler that has multithreading, PostgreSQL, indexing/searching, Spring Boot API, and Docker support.

        // I dont know where to begin.

        // Give my Java program a URL, download the webpage, and print its contents.

        // ----------------- URL RESPONSE RETRIEVER -----------------

        Scanner sc = new Scanner(System.in);


        //System.out.print("Please enter the URL: ");
        //final String theURL = sc.nextLine();

        //System.out.println("You entered: " + theURL);

        //PageFetcher fetcher = new PageFetcher(theURL);
        //System.out.println(fetcher.fetch());
        
        sc.close();


        // --------------- HTML PARSER ------------------

        //HtmlParser parser = new HtmlParser();

        //parser.parse("<div><p>Hello</p></div>");

        //System.out.println("END OF PROGRAM");
        //System.out.println("Expected:\nTag: div\nTag: p\nTag: /p\nTag: /div");

        // --------------- RAW HTTP RESPONSE PARSER ----------------

        HttpResponseParser parser = new HttpResponseParser();

        String theInput = "HTTP/1.1 200 OK\r\n" +
                          "Date: Mon, 14 Sep 2026 20:08:22 GMT\r\n" + 
                          "Content-Type: text/html\r\n" + 
                          "Transfer-Encoding: chunked\r\n" + 
                          "\r\n";

        System.out.println("Status code: " + parser.getStatus(theInput));

        HashMap<String, String> headers = new HashMap<>();
        headers = parser.getHeaders(theInput);
        
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value);
        }

    }
}

