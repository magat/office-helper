package com.officehelper.dao;

import com.officehelper.entity.Request;
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
        return hibernateSession.createQuery("from Request").list();
    }

    public Request getRequest(long id) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        return hibernateSession.get(Request.class, id);
    }

    public long addRequest(Request req) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        return (long) hibernateSession.save(req);
    }

    //Returns false when the entity couldn't be deleted
    public boolean deleteRequest(long id) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        Request reqToDelete = getRequest(id);
        if(reqToDelete != null) {
            hibernateSession.delete(getRequest(id));
            return true;
        }
        return false;
    }

}