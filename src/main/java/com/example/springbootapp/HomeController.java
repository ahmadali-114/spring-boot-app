package com.example.springbootapp;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
            <!doctype html>
            <html lang="en">
              <head>
                <meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>Spring Boot CI/CD</title>
                <style>
                  body {
                    margin: 0;
                    min-height: 100vh;
                    display: grid;
                    place-items: center;
                    font-family: Arial, sans-serif;
                    background: #f4f7fb;
                    color: #1f2937;
                  }
                  main {
                    max-width: 720px;
                    padding: 32px;
                    text-align: center;
                  }
                  h1 {
                    margin: 0 0 12px;
                    font-size: 40px;
                  }
                  p {
                    font-size: 18px;
                    line-height: 1.6;
                  }
                  code {
                    background: #e5e7eb;
                    padding: 3px 6px;
                    border-radius: 4px;
                  }
                </style>
              </head>
              <body>
                <main>
                  <h1>Spring Boot App is Running</h1>
                  <p>This application was built with Docker, pushed to AWS ECR, and deployed to EC2 using GitHub Actions.</p>
                  <p>Health check: <code>/api/health</code></p>
                </main>
              </body>
            </html>
            """;
    }

    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "application", "spring-boot-app",
            "time", Instant.now().toString()
        );
    }
}
