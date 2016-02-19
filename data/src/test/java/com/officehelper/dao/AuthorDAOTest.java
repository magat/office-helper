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
public class AuthorDAOTest {
    @Inject
    private AuthorDAO authorDAO;
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
    public void testAddAuthor() {
        long testAuthorId = authorDAO.addAuthor(testingUser);
        assertEquals(testAuthorId, testingUser.getId());
        assertNotNull(testAuthorId);
        assertTrue(testAuthorId > 0);
        assertNotNull(session.get(Author.class, testAuthorId));
    }

    @Test
    @Transactional
    public void testGetAuthor_when_no_author() {
        //Id doesn't exist
        assertNull(authorDAO.getAuthor(2));
    }

    @Test
    @Transactional
    public void testGetAuthor_when_one_author() {
        //Id exists
        long id = (long) session.save(testingUser);
        Author gUsr = authorDAO.getAuthor(id);
        assertNotNull(gUsr);
        assertEquals(id, gUsr.getId());
    }

    @Test
    @Transactional
    public void testGetAuthorList_when_empty() {
        //Empty table
        List<Author> authorsList = authorDAO.getAuthorList();
        assertEquals(authorsList.size(), 0);
    }

    @Test
    @Transactional
    public void testGetAuthorList_when_not_empty() {
        //Table with one user
        session.save(testingUser);
        List<Author> authorsList = authorDAO.getAuthorList();
        assertEquals(1, authorsList.size());

        //With two users
        Author testingUser2 = new Author();
        testingUser2.setLastName("Admin2");
        testingUser2.setFirstName("Admin2");
        testingUser2.setEmail("test2.test@test.com");
        session.save(testingUser2);
        authorsList = authorDAO.getAuthorList();
        assertEquals(2, authorsList.size());
    }

    @Test
    @Transactional
    public void testDeleteAuthor_when_undefined() {
        //Remove undefined entity
        assertFalse(authorDAO.deleteAuthor(2));
    }

    @Test
    @Transactional
    public void testDeleteAuthor_when_defined() {
        //Remove defined entity
        long id = (long) session.save(testingUser);
        assertNotNull(session.get(Author.class, id));
        assertTrue(authorDAO.deleteAuthor(id));
    }
}
