package com.officehelper.dao;

import com.officehelper.entity.Request;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class RequestDAO {
    @Inject
    private SessionFactory sessionFactory;

    public List<Request> getRequestList() {
        Session hibernateSession = sessionFactory.getCurrentSession();
        //List<Request> results = hibernateSession.createQuery("from Request r left join fetch r.author a left join fetch a.requests").list();
        return hibernateSession.createQuery("from Request r left join fetch r.author").list();
    }

    public Request getRequest(long id) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        Request result = hibernateSession.get(Request.class, id);
        if(result != null)
            Hibernate.initialize(result.getAuthor());
        return result;
        //Query query =  hibernateSession.createQuery("from Request r left join fetch r.author where r.id=:id");
        //query.setParameter("id",id);
        //return (Request) query.list().get(0);
    }

    public long addRequest(Request req) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        return (long) hibernateSession.save(req);
    }

    //Returns false when the entity couldn't be deleted
    public boolean deleteRequest(long id) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        Request reqToDelete = getRequest(id);
        if (reqToDelete != null) {
            hibernateSession.delete(getRequest(id));
            return true;
        }
        return false;
    }
}