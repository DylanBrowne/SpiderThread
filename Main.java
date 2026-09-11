import java.util.Scanner;

public class Main {
    public static void main(String[] theArgs) {
        System.out.println("PROGRAM STARTED");
        // I want to develop a web crawler that has multithreading, PostgreSQL, indexing/searching, Spring Boot API, and Docker support.

        // I dont know where to begin.

        // Give my Java program a URL, download the webpage, and print its contents.

        Scanner sc = new Scanner(System.in);


        //System.out.print("Please enter the URL: ");
        //final String theURL = sc.nextLine();

        //System.out.println("You entered: " + theURL);

        //PageFetcher fetcher = new PageFetcher(theURL);
        //System.out.println(fetcher.fetch());
        
        //sc.close();



        HtmlParser parser = new HtmlParser();

        parser.parse("<div><p>Hello</p></div>");

        System.out.println("END OF PROGRAM");
        System.out.println("Expected:\nTag: div\nTag: p\nTag: /p\nTag: /div");
    }
}

