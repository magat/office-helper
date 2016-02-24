package com.officehelper.dao;

import com.officehelper.entity.Request;
import com.officehelper.entity.Status;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Component
public class RequestDAO {
    @Inject
    private SessionFactory sessionFactory;

    public List<Request> getRequestList() {
        Session hibernateSession = sessionFactory.getCurrentSession();
        return hibernateSession.createQuery("from Request order by dateCreated").list();
    }

    public List<Request> getNewRequests() {
        Session hibernateSession = sessionFactory.getCurrentSession();
        Query query = hibernateSession.createQuery("from Request where status=:newStatus order by dateCreated");
        query.setParameter("newStatus",Status.NEW);
        return query.list();
    }

    public List<Request> getOrderedRequests() {
        Session hibernateSession = sessionFactory.getCurrentSession();
        Query query = hibernateSession.createQuery("from Request where status=:orderedStatus order by dateCreated");
        query.setParameter("orderedStatus",Status.ORDERED);
        return query.list();
    }

    public List<Request> getReceivedRequests() { //TODO : Some logic should be in the service ?
        Session hibernateSession = sessionFactory.getCurrentSession();
        Query query = hibernateSession.createQuery("from Request where status=:receivedStatus and dateReceived>=:sevenDaysAgo order by dateOrdered");
        query.setParameter("receivedStatus",Status.RECEIVED);
        //Seven days ago ... TODO : Java8 LocalDateTime
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        query.setDate("sevenDaysAgo", new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)));
        return query.list();
    }

    public List<Request> getArchivedRequests() { //TODO : Some logic should be in the service ?
        Session hibernateSession = sessionFactory.getCurrentSession();
        Query query = hibernateSession.createQuery("from Request where status=:refusedStatus or (status=:receivedStatus and dateReceived<:sevenDaysAgo) order by dateCreated desc");
        query.setParameter("refusedStatus",Status.REFUSED);
        query.setParameter("receivedStatus",Status.RECEIVED);
        //Seven days ago ... TODO : Java8 LocalDateTime
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        query.setDate("sevenDaysAgo", new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)));
        return query.list();
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

    //TODO : Tests
    public boolean updateRequestOrderDate(long id, Date date) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        Request r = hibernateSession.get(Request.class, id);
        if(r == null) {
            return false;
        }
        r.setDateOrdered(date);
        return true;
    }

    //TODO : Tests
    public boolean updateRequestDeliveryDate(long id, Date date) {
        Session hibernateSession = sessionFactory.getCurrentSession();
        Request r = hibernateSession.get(Request.class, id);
        if(r == null) {
            return false;
        }
        r.setDateReceived(date);
        return true;
    }
}