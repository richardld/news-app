package com.example.richard.newsapplication;

public class NewsArticle {
    private String title, author, source, date, urlToPage, urlToImage;

    // Instantiate NewsArticle
    // Parameters title, author, source, date, urlToPage, urlToImage
    NewsArticle(String title, String author, String source, String date, String urlToPage, String urlToImage) {
        this.title = title;
        this.author = author;
        this.source = source;
        this.date = date;
        this.urlToPage = urlToPage;
        this.urlToImage = urlToImage;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSource() {
        return source;
    }

    public String getDate() {
        return date;
    }

    public String getUrlToPage() {
        return urlToPage;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    // Returns string representation of NewsArticle
    public String toString() {
        return title;
    }
}
