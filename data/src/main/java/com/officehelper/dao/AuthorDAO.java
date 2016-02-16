package com.officehelper.dao;

import com.officehelper.entity.Author;
import com.officehelper.entity.Request;
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
        return hibernateSession.createQuery("from Author a left join fetch a.requests").list();
    }

    public Author getAuthor(long id) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        Author result = hibernateSession.get(Author.class, id);
        if(result != null)
            Hibernate.initialize(result.getRequests());
        return result;
    }

    public long addAuthor(Author req) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        return (long) hibernateSession.save(req);
    }

    //Returns false when the entity couldn't be deleted
    public boolean deleteAuthor(long id) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        Author authorToDelete = getAuthor(id);
        if (authorToDelete != null) {
            hibernateSession.delete(authorToDelete);
            return true;
        }
        return false;
    }
}
