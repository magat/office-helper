package com.officehelper.dao;

import com.officehelper.config.DataConfigTest;
import com.officehelper.entity.Request;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.*;

@RunWith(SpringJUnit4ClassRunner.class) //Allows support of annotations inside jUnits test Classes
@ContextConfiguration(classes = DataConfigTest.class) //Custom context for testing
public class RequestDAOTest {

    @Inject
    private RequestDAO requestDAO;
    @Inject
    private SessionFactory sessionFactory;
    private Session session;
    private Request testingRequest;

    @Before
    public void testInit() {
        testingRequest = new Request();
        testingRequest.setAuthor("Testing User");
        testingRequest.setDateCreated(new Date());
        testingRequest.setComments("Testing Comment");
        testingRequest.setTitle("Testing Title");
        testingRequest.setUrl("http://www.test.com");
        testingRequest.setStatus("Test Status");
        session = sessionFactory.getCurrentSession();
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
    public void testGetRequest() {
        //Id doesn't exist
        Request gReq = requestDAO.getRequest(2);
        assertNull(gReq);

        //Id exists
        long id = (long) session.save(testingRequest);
        gReq = requestDAO.getRequest(id);
        assertNotNull(gReq);
        assertEquals(id, gReq.getId());
    }

    @Test
    @Transactional
    public void testGetRequestList() {
        //Empty table
        List<Request> requestList = requestDAO.getRequestList();
        assertEquals(requestList.size(), 0);

        //Table with some entries
        session.save(testingRequest);
        requestList = requestDAO.getRequestList();
        assertEquals(1, requestList.size());
    }

    @Test
    @Transactional
    public void testDeleteRequest() {
        //Remove undefined entity
        requestDAO.deleteRequest(2);

        //Remove defined entity
        long id = (long) session.save(testingRequest);
        assertNotNull(session.get(Request.class, id));
        session.evict(testingRequest);
        requestDAO.deleteRequest(id);
        assertNull(session.get(Request.class, id));
    }

}