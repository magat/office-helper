package com.officehelper.dao;

import com.officehelper.config.DataTestConfig;
import com.officehelper.entity.Author;
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

}