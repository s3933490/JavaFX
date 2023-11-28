package com.example.socialmediafx;
import java.io.Serializable;

public class SocialMediaPost implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String content;
    private String author;
    private int likes;
    private int shares;
    private String dateTime;
    private int mainPostId;

    // Constructors
    public SocialMediaPost(int id, String content, String author, int likes, int shares, String dateTime, int mainPostId) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.shares = shares;
        this.dateTime = dateTime;
        this.mainPostId = this.mainPostId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getMainPostId() {
        return mainPostId;
    }

    // Other methods for post modification or additional functionality
    // ...

    @Override
    public String toString() {
        return "SocialMediaPost{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", likes=" + likes +
                ", shares=" + shares +
                ", dateTime=" + dateTime +
                '}';
    }

    public String toCSVString() {
        return String.format("%d,%s,%s,%d,%d,%s,%d", id, content, author, likes, shares, dateTime, mainPostId);
    }
}
