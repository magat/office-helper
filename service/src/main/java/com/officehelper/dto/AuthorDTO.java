package com.officehelper.dto;

import com.officehelper.entity.Author;
import com.officehelper.entity.Request;

import java.util.List;
import java.util.Objects;

public class AuthorDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Request> requestList;

    public AuthorDTO() {
    }

    public AuthorDTO(Author author) {
        if (author != null) {
            this.id = author.getId();
            this.firstName = author.getFirstName();
            this.lastName = author.getLastName();
            this.email = author.getEmail();
        }
    }

    public Author toAuthor() {
        Author author = new Author();
        author.setEmail(this.email);
        author.setFirstName(this.firstName);
        author.setLastName(this.lastName);
        return author;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthorDTO authorDTO = (AuthorDTO) o;
        return id == authorDTO.id &&
                Objects.equals(firstName, authorDTO.firstName) &&
                Objects.equals(lastName, authorDTO.lastName) &&
                Objects.equals(email, authorDTO.email) &&
                Objects.equals(requestList, authorDTO.requestList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, requestList);
    }
}
