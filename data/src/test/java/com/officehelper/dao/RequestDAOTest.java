package com.officehelper.dao;

import com.officehelper.config.DataTestConfig;
import com.officehelper.entity.Author;
import com.officehelper.entity.Request;
import com.officehelper.entity.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringJUnit4ClassRunner.class) //Allows support of annotations inside jUnits test Classes
@ContextConfiguration(classes = DataTestConfig.class) //Custom context for testing
public class RequestDAOTest {

    @Inject
    private RequestDAO requestDAO;
    @Inject
    private SessionFactory sessionFactory;
    private Session session;
    private Request testingRequest;
    private Author testingUser;

    @Before
    public void testInit() {
        testingUser = new Author();
        testingUser.setLastName("Admin");
        testingUser.setFirstName("Admin");
        testingUser.setEmail("test.test@test.com");

        testingRequest = new Request();
        testingRequest.setAuthor(testingUser);
        testingRequest.setDateCreated(new Date());
        testingRequest.setComments("Testing Comment");
        testingRequest.setTitle("Testing Title");
        testingRequest.setUrl("http://www.test.com");
        testingRequest.setStatus(Status.NEW);

        session = sessionFactory.getCurrentSession();
    }

    public Request generateRequestWithStatus(Status s) {
        Request requestWithStatus = new Request();
        requestWithStatus.setAuthor(testingUser);
        requestWithStatus.setDateCreated(new Date());
        requestWithStatus.setComments("Testing Comment");
        requestWithStatus.setTitle("Testing Title");
        requestWithStatus.setUrl("http://www.test.com");
        requestWithStatus.setStatus(s);
        if(s == Status.ORDERED) {
            requestWithStatus.setDateOrdered(new Date());
        }
        else if (s == Status.RECEIVED) {
            requestWithStatus.setDateOrdered(new Date());
            requestWithStatus.setDateReceived(new Date());
        }
        requestWithStatus.setDateReceived(new Date());
        return requestWithStatus;
    }

    public Request generateArchivedRequest(boolean refused) {
        Request requestWithStatus = new Request();
        requestWithStatus.setAuthor(testingUser);
        requestWithStatus.setDateCreated(new Date());
        requestWithStatus.setComments("Testing Comment");
        requestWithStatus.setTitle("Testing Title");
        requestWithStatus.setUrl("http://www.test.com");
        requestWithStatus.setStatus(Status.REFUSED);
        if(!refused) {
            //Received 8 days ago !
            long DAY_IN_MS = 1000 * 60 * 60 * 24;
            requestWithStatus.setDateReceived(new Date(System.currentTimeMillis() - (8 * DAY_IN_MS)));
            requestWithStatus.setStatus(Status.RECEIVED);
        }
        return requestWithStatus;
    }

    @Test
    @Transactional
    public void testAddRequest() {
        long testRequestId = requestDAO.addRequest(testingRequest);
        assertEquals(testRequestId, testingRequest.getId());
        assertNotNull(testRequestId);
        assertTrue(testRequestId > 0);
        assertNotNull(session.get(Request.class, testRequestId));
    }

    @Test
    @Transactional
    public void testGetRequest_when_no_request() {
        //Id doesn't exist
        assertNull(requestDAO.getRequest(2));
    }

    @Test
    @Transactional
    public void testGetRequest_when_one_request() {
        //Id exists
        long id = (long) session.save(testingRequest);
        Request gReq = requestDAO.getRequest(id);
        assertNotNull(gReq);
        assertEquals(id, gReq.getId());
    }

    @Test
    @Transactional
    public void testGetRequestList_when_empty() {
        //Empty table
        List<Request> requestList = requestDAO.getRequestList();
        assertEquals(requestList.size(), 0);
    }

    @Test
    @Transactional
    public void testGetRequestList_when_not_empty() {
        //Table with some entries
        session.save(testingRequest);
        List<Request> requestList = requestDAO.getRequestList();
        assertEquals(1, requestList.size());
    }

    @Test
    @Transactional
    public void testDeleteRequest_when_undefined() {
        //Remove undefined entity
        assertFalse(requestDAO.deleteRequest(2));
    }

    @Test
    @Transactional
    public void testDeleteRequest_when_defined() {
        //Remove defined entity
        long id = (long) session.save(testingRequest);
        assertNotNull(session.get(Request.class, id));
        assertTrue(requestDAO.deleteRequest(id));
    }

    @Test
    @Transactional
    public void testGetNewRequests_when_no_new_requests() {
        List<Status> sList = new ArrayList<>();
        sList.add(Status.ORDERED);
        sList.add(Status.RECEIVED);
        sList.add(Status.REFUSED);
        for(Status currentStatus : sList) {
            session.save(generateRequestWithStatus(currentStatus));
        }
        assertEquals(requestDAO.getNewRequests().size(),0);
    }

    @Test
    @Transactional
    public void testGetNewRequests_when_new_requests() {
        List<Status> sList = new ArrayList<>();
        sList.add(Status.ORDERED);
        sList.add(Status.RECEIVED);
        sList.add(Status.REFUSED);
        sList.add(Status.NEW);
        sList.add(Status.NEW);
        for(Status currentStatus : sList) {
            session.save(generateRequestWithStatus(currentStatus));
        }
        assertEquals(requestDAO.getNewRequests().size(),2);
    }

    @Test
    @Transactional
    public void testGetOrderedRequests_when_no_ordered_requests() {
        List<Status> sList = new ArrayList<>();
        sList.add(Status.NEW);
        sList.add(Status.RECEIVED);
        sList.add(Status.REFUSED);
        for(Status currentStatus : sList) {
            session.save(generateRequestWithStatus(currentStatus));
        }
        assertEquals(requestDAO.getOrderedRequests().size(),0);
    }

    @Test
    @Transactional
    public void testGetOrderedRequests_when_ordered_requests() {
        List<Status> sList = new ArrayList<>();
        sList.add(Status.ORDERED);
        sList.add(Status.ORDERED);
        sList.add(Status.REFUSED);
        sList.add(Status.NEW);
        sList.add(Status.RECEIVED);
        sList.add(Status.ORDERED);
        for(Status currentStatus : sList) {
            session.save(generateRequestWithStatus(currentStatus));
        }
        assertEquals(requestDAO.getOrderedRequests().size(),3);
    }

    @Test
    @Transactional
    public void testGetReceivedRequests_when_no_ordered_requests() {
        List<Status> sList = new ArrayList<>();
        sList.add(Status.NEW);
        sList.add(Status.ORDERED);
        sList.add(Status.REFUSED);
        for(Status currentStatus : sList) {
            session.save(generateRequestWithStatus(currentStatus));
        }
        assertEquals(requestDAO.getReceivedRequests().size(),0);
    }

    @Test
    @Transactional
    public void testGetReceivedRequests_when_received_requests() {
        List<Status> sList = new ArrayList<>();
        sList.add(Status.RECEIVED);
        sList.add(Status.ORDERED);
        sList.add(Status.REFUSED);
        sList.add(Status.NEW);
        sList.add(Status.RECEIVED);
        for(Status currentStatus : sList) {
            session.save(generateRequestWithStatus(currentStatus));
        }
        assertEquals(requestDAO.getReceivedRequests().size(),2);
    }

    @Test
    @Transactional
    public void testGetArchivedRequests_when_no_archived_requests() {
        List<Status> sList = new ArrayList<>();
        sList.add(Status.NEW);
        sList.add(Status.ORDERED);
        sList.add(Status.RECEIVED);
        for(Status currentStatus : sList) {
            session.save(generateRequestWithStatus(currentStatus));
        }
        assertEquals(requestDAO.getArchivedRequests().size(),0);
    }

    @Test
    @Transactional
    public void testGetArchivedRequests_when_archived_requests() {
        List<Status> sList = new ArrayList<>();
        sList.add(Status.RECEIVED);
        sList.add(Status.ORDERED);
        sList.add(Status.REFUSED); //First archived request
        sList.add(Status.NEW);
        sList.add(Status.RECEIVED);
        for(Status currentStatus : sList) {
            session.save(generateRequestWithStatus(currentStatus));
        }
        session.save(generateArchivedRequest(false)); //Second one (Arrived a 8 days ago)
        assertEquals(requestDAO.getArchivedRequests().size(),2);
    }

    @Test
    @Transactional
    public void testUpdateStatus_when_request_exists() {
        long id = (long) session.save(testingRequest);
        assertEquals(requestDAO.updateStatus(id,Status.REFUSED), true);
        assertEquals(session.get(Request.class,id).getStatus(), Status.REFUSED);
    }

    @Test
    @Transactional
    public void testUpdateStatus_when_request_does_not_exist() {
        assertEquals(requestDAO.updateStatus(5L,Status.REFUSED), false);
        assertNull(session.get(Request.class,5L));
    }

    @Test
    @Transactional
    public void testUpdateRequestOrderDate_when_request_exists() {
        long id = (long) session.save(testingRequest);
        assertEquals(requestDAO.updateRequestOrderDate(id,new Date(0)), true);
        assertEquals(session.get(Request.class,id).getDateOrdered(), new Date(0));
    }

    @Test
    @Transactional
    public void testUpdateRequestOrderDate_when_request_does_not_exist() {
        assertEquals(requestDAO.updateRequestOrderDate(5L,new Date(0)), false);
        assertNull(session.get(Request.class,5L));
    }

    @Test
    @Transactional
    public void testUpdateRequestDeliveryDate_when_request_exists() {
        long id = (long) session.save(testingRequest);
        assertEquals(requestDAO.updateRequestDeliveryDate(id,new Date(0)), true);
        assertEquals(session.get(Request.class,id).getDateReceived(), new Date(0));
    }

    @Test
    @Transactional
    public void testUpdateRequestDeliveryDate_when_request_does_not_exist() {
        assertEquals(requestDAO.updateRequestDeliveryDate(5L,new Date(0)), false);
        assertNull(session.get(Request.class,5L));
    }

}