# SpiderThread

SpiderThread is a Java-based web crawler being built from the ground up to explore how web crawlers work at a lower level.

The project focuses on understanding the process of retrieving webpages, communicating over HTTP, parsing raw HTTP responses, and processing HTML before eventually expanding into a larger crawling and indexing system.

## Current Status

**In development**

Currently implemented:

* HTTP and HTTPS webpage retrieval
* Raw HTTP GET requests using Java sockets
* HTTP response status-code parsing
* Basic HTML tag and text parsing
* URL host, port, and path extraction

Planned features include:

* Recursive webpage crawling
* Visited-URL tracking
* Multithreaded crawling
* PostgreSQL persistence
* Search and indexing
* Spring Boot REST API
* Docker support

## Technologies

* **Java**
* TCP/IP Sockets
* HTTP / HTTPS
* `Socket` / `SSLSocket`
* Java I/O
* Data Structures (`HashSet`, `StringBuilder`)

## Architecture

SpiderThread is being developed as a collection of components with separate responsibilities:

```text
URL
 │
 ▼
PageFetcher
 │
 │ Raw HTTP Response
 ▼
HttpResponseParser
 │
 │ HTML Content
 ▼
HtmlParser
 │
 │ Parsed Content
 ▼
WebCrawler
 │
 ▼
Future: Indexing / Storage / Search
```

### PageFetcher

Responsible for communicating with the target server and retrieving its response.

It currently:

* Extracts the hostname, port, and path from a URL
* Opens a regular socket for HTTP
* Opens an SSL socket for HTTPS
* Sends a raw HTTP `GET` request
* Reads the server's response

### HttpResponseParser

Responsible for interpreting the raw HTTP response.

Currently, it extracts and validates the HTTP status code from the response.

Future responsibilities include extracting:

* Response headers
* HTML response body

### HtmlParser

Responsible for traversing raw HTML and separating tags from text content.

The current parser operates directly on the HTML string and identifies content between `<` and `>` as tags and content between tags as document text.

### WebCrawler

The crawler component is currently under development.

Its eventual responsibility will be to coordinate webpage traversal, keep track of visited URLs, and pass retrieved pages through the parsing pipeline.

## Example

The current HTTP response parser can process a raw HTTP status line:

```java
HttpResponseParser parser = new HttpResponseParser();

int statusCode = parser.getStatus("HTTP/1.1 200 OK");

System.out.println("Status code: " + statusCode);
```

Output:

```text
Status code: 200
```

## Goals

SpiderThread is primarily a learning project focused on understanding the underlying systems involved in web crawling rather than relying entirely on high-level libraries.

The long-term goal is to build a complete crawler that can:

1. Retrieve webpages
2. Parse HTTP responses
3. Extract useful HTML content and links
4. Crawl additional pages
5. Process pages concurrently
6. Store crawled data
7. Index content for searching
8. Expose the system through an API
9. Package and run the application with Docker

## Project Structure

```text
SpiderThread/
├── Main.java
├── PageFetcher.java
├── HttpResponseParser.java
├── HtmlParser.java
├── WebCrawler.java
└── README.md
```

## Learning Objectives

This project is being used to develop practical understanding of:

* HTTP and networking
* TCP/IP communication
* Java sockets
* Parsing
* Object-oriented design
* Data structures
* Concurrency
* Databases
* REST APIs
* Software architecture
* Containerization
* Search and indexing systems
