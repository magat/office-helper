package com.officehelper.dao;

import com.officehelper.entity.Request;
import com.officehelper.entity.Status;
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
        return hibernateSession.createQuery("from Request order by dateCreated").list();
    }

    public Request getRequest(long id) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        return hibernateSession.get(Request.class, id);
    }

    public long addRequest(Request req) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        return (long) hibernateSession.save(req);
    }

    /**
     * @param id
     * @return Returns false when the entity couldn't be deleted
     */
    public boolean deleteRequest(long id) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        int requestDeleted = hibernateSession.createQuery("delete Request where id = :id")
                .setParameter("id", id)
                .executeUpdate();
        return (requestDeleted != 0); // int -> bool
    }

    public boolean updateStatus(long id, Status status) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        Request r = hibernateSession.get(Request.class, id);
        if(r == null) {
            return false;
        }
        r.setStatus(status);
        return true;
    }
}