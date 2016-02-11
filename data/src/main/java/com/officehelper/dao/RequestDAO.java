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
        return hibernateSession.createCriteria(Request.class).list();
    }

    public Request getRequest(long id) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        return hibernateSession.get(Request.class, id);
    }

    public Request addRequest(Request req) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        hibernateSession.save(req);
        return req;
    }

    public void deleteRequest(long id) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        Request rToDelete = new Request();
        rToDelete.setId(id);
        hibernateSession.delete(rToDelete);
    }

}