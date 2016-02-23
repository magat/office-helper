package com.officehelper.dto;

import com.officehelper.entity.Request;
import com.officehelper.entity.Status;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

public class RequestDTO {

    private long id;

    @NotNull
    private Date dateCreated;

    @NotNull @Size(min=3, max=255)
    private String title;

    @URL
    private String url;

    @NotNull
    private Status status;

    private String comments;

    private Date dateOrdered;

    private Date dateReceived;

    @NotNull
    private AuthorDTO author;

    public RequestDTO() {
        this.dateCreated = new Date();
        this.author = new AuthorDTO();
        this.status = Status.NEW;
    }

    public RequestDTO(Request req) {
        if (req != null) {
            this.setAuthor(new AuthorDTO(req.getAuthor()));
            this.setComments(req.getComments());
            this.setDateCreated(req.getDateCreated());
            this.setDateOrdered(req.getDateOrdered());
            this.setDateReceived(req.getDateReceived());
            this.setStatus(req.getStatus());
            this.setTitle(req.getTitle());
            this.setUrl(req.getUrl());
            this.id = req.getId();
        }
    }

    public Request toRequest() {
        Request req = new Request();
        req.setAuthor(this.getAuthor().toAuthor());
        req.setComments(this.getComments());
        req.setDateCreated(this.getDateCreated());
        req.setDateOrdered(this.getDateOrdered());
        req.setDateReceived(this.getDateReceived());
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    public String getDateOrderedToString() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        if (dateOrdered != null) {
            String date = dateFormat.format(dateOrdered);
            return date.substring(0, 1).toUpperCase() + date.substring(1);
        }
        return "";
    }

    public void setDateOrdered(Date dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public Date getDateReceived() { return dateReceived; }

    public void setDateReceived(Date dateReceived) { this.dateReceived = dateReceived; }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getDateCreatedToString() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        if (dateCreated != null) {
            String date = dateFormat.format(dateCreated);
            return date.substring(0, 1).toUpperCase() + date.substring(1);
        }
        return "";
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestDTO that = (RequestDTO) o;
        return id == that.id &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(title, that.title) &&
                Objects.equals(url, that.url) &&
                status == that.status &&
                Objects.equals(comments, that.comments) &&
                Objects.equals(dateOrdered, that.dateOrdered) &&
                Objects.equals(dateReceived, that.dateReceived) &&
                Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateCreated, title, url, status, comments, dateOrdered, dateReceived, author);
    }

    public String getDateReceivedToString() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        if (dateReceived != null) {
            String date = dateFormat.format(dateReceived);
            return date.substring(0, 1).toUpperCase() + date.substring(1);
        }

        return "";
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", comments='" + comments + '\'' +
                ", dateOrdered=" + dateOrdered +
                ", dateReceived=" + dateReceived +
                ", author=" + author +
                '}';
    }

}
