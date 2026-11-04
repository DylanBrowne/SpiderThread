package controller;

import model.HtmlParser;
import model.HttpResponseParser;
import model.PageFetcher;
import model.WebCrawler;

import java.util.Scanner;

public class Main {
    public static void main(String[] theArgs) {
        System.out.println("PROGRAM STARTED");
        // I want to develop a web crawler that has multithreading, PostgreSQL, indexing/searching, Spring Boot API, and Docker support.

        // I dont know where to begin.

        // Give my Java program a URL, download the webpage, and print its contents.

        // ----------------- URL RESPONSE RETRIEVER -----------------

        Scanner sc = new Scanner(System.in);


        System.out.print("Please enter the URL: ");
        final String theURL = sc.nextLine();

        // System.out.println("You entered: " + theURL);

        PageFetcher fetcher = new PageFetcher(theURL);
        // System.out.println(fetcher.fetch());

        HttpResponseParser parser = new HttpResponseParser();
        String theHtml = parser.parse(fetcher.fetch());

        HtmlParser htmlParser = new HtmlParser();
        htmlParser.parse(theHtml);
        
        sc.close();


        // --------------- HTML PARSER ------------------

        //HtmlParser parser = new HtmlParser();

        //parser.parse("<div><p>Hello</p></div>");

        //System.out.println("END OF PROGRAM");
        //System.out.println("Expected:\nTag: div\nTag: p\nTag: /p\nTag: /div");

        // --------------- RAW HTTP RESPONSE PARSER ----------------

        // HttpResponseParser parser = new HttpResponseParser();

        // String theInput =
        //     "HTTP/1.1 200 OK\r\n" +
        //     "Date: Mon, 14 Sep 2026 20:08:22 GMT\r\n" +
        //     "Content-Type: text/html\r\n" +
        //     "Transfer-Encoding: chunked\r\n" +
        //     "\r\n" +
        //     "22f\r\n";

        String theInput =
            "HTTP/1.1 200 OK\r\n" +
            "Date: Thu, 17 Sep 2026 17:35:17 GMT\r\n" + 
            "Content-Type: text/html\r\n" + 
            "Transfer-Encoding: chunked\r\n" + 
            "Connection: close\r\n" + 
            "Server: cloudflare\r\n" + 
            "Last-Modified: Tue, 15 Sep 2026 23:41:26 GMT\r\n" + 
            "Allow: GET, HEAD\r\n" +
            "Accept-Ranges: bytes\r\n" +
            "Age: 9596\r\n" +
            "cf-cache-status: HIT\r\n" +
            "CF-RAY: a3c9ddd7bba076a6-SEA\r\n" +
            "\r\n" +
            "22f\r\n" +
            "<!doctype html><html lang=\"en\"><head><title>Example Domain</title>" +
            "<link rel=\"icon\" href=\"data:,\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
            "<style>body{background:#eee;width:60vw;margin:15vh auto;font-family:system-ui,sans-serif}" +
            "h1{font-size:1.5em}div{opacity:0.8}a:link,a:visited{color:#348}</style></head><body><div><h1>Example Domain</h1>" +
            "<p>This domain is for use in documentation examples without needing permission. Avoid use in operations.</p><p>" +
            "<a href=\"https://iana.org/domains/example\">Learn more</a></p></div></body></html>\r\n" +
            "\r\n" +
            "0\r\n" +
            "\r\n";

        // parser.parse(theInput);

        // System.out.println("Status code: " + parser.getStatus(theInput));

        // Map<String, String> headers = parser.getHeaders(theInput);
        
        // for (Map.Entry<String, String> entry : headers.entrySet()) {
        //     String key = entry.getKey();
        //     String value = entry.getValue();
        //     System.out.println("Key: " + key + ", Value: " + value);
        // }

        // // System.out.println("Chunk size: " + parser.getChunkSize(theInput));

        // System.out.println("HTML: " + parser.getHtml(theInput).replace("\r", "\\r")
        // .replace("\n", "\\n"));

        

        // System.out.println("Next chunk size: " + parser.getChunkSize(theInput));

    }
}

