package com.officehelper.dto;

import com.officehelper.entity.Request;

import java.util.Date;

public class RequestDTO {

    private long id;
    private Date dateCreated;
    private String title;
    private String url;
    private String status;
    private String comments;
    private Date dateOrdered;
    private String author;

    public RequestDTO() {
        this.dateCreated = new Date();
        this.author = "User";
    }

    public RequestDTO(Request req) {
        this.setAuthor(req.getAuthor());
        this.setComments(req.getComments());
        this.setDateCreated(req.getDateCreated());
        this.setDateOrdered(req.getDateOrdered());
        this.setStatus(req.getStatus());
        this.setTitle(req.getTitle());
        this.setUrl(req.getUrl());
        this.id = req.getId();
    }

    public Request toRequest() {
        Request req = new Request();
        req.setAuthor(this.getAuthor());
        req.setComments(this.getComments());
        req.setDateCreated(this.getDateCreated());
        req.setDateOrdered(this.getDateOrdered());
        req.setStatus(this.getStatus());
        req.setTitle(this.getTitle());
        req.setUrl(this.getUrl());
        return req;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(Date dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }


    @Override
    public String toString() {
        return "RequestDTO{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", status='" + status + '\'' +
                ", comments='" + comments + '\'' +
                ", dateOrdered=" + dateOrdered +
                ", author='" + author + '\'' +
                '}';
    }
}
