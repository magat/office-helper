package com.officehelper.dao;

import com.officehelper.entity.Author;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class AuthorDAO {
    @Inject
    private SessionFactory sessionFactory;

    public List<Author> getAuthorList() {
        Session hibernateSession = sessionFactory.getCurrentSession();
        return hibernateSession.createQuery("from Author").list();
    }

    public Author getAuthor(long id) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        return hibernateSession.get(Author.class, id);
    }

    public long addAuthor(Author author) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        return (long) hibernateSession.save(author);
    }

    //Returns false when the entity couldn't be deleted
    public boolean deleteAuthor(long id) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        int authorDeleted = hibernateSession.createQuery("delete Author where id = :id")
                .setParameter("id", id)
                .executeUpdate();
        if (authorDeleted == 1) {
            return true;
        }
        return false;
    }
}
