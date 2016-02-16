package com.officehelper.service;

import com.officehelper.dao.AuthorDAO;
import com.officehelper.dto.AuthorDTO;
import com.officehelper.dto.RequestDTO;
import com.officehelper.entity.Author;
import com.officehelper.entity.Request;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class AuthorServiceTest {

    @Mock
    AuthorDAO mockedAuthorDAO;

    @InjectMocks
    AuthorService authorService;

    Author authorSample;
    AuthorDTO authorDTOSample;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        authorSample = new Author();
        authorSample.setEmail("test@test.com");
        authorSample.setFirstName("Test first name");
        authorSample.setLastName("Test last name");

        authorDTOSample = new AuthorDTO(authorSample);
    }

    @Test
    public void testAddAuthor() {
        when(mockedAuthorDAO.addAuthor(any())).thenReturn(2L);
        assertEquals(authorService.addAuthor(authorDTOSample),2L);
    }

    @Test
    public void testGetAuthor_when_entry_found() {
        when(mockedAuthorDAO.getAuthor(anyLong())).thenReturn(authorSample);
        assertEquals(authorService.getAuthor(5L), authorDTOSample);
    }

    @Test
    public void testGetAuthor_when_no_entry_found() {
        when(mockedAuthorDAO.getAuthor(anyLong())).thenReturn(null);
        assertEquals(authorService.getAuthor(5L), new AuthorDTO(null));
    }

    @Test
    public void testDeleteAuthor_when_id_incorrect() {
        when(mockedAuthorDAO.deleteAuthor(anyLong())).thenReturn(false);
        assertFalse(authorService.deleteAuthor(4L));
    }

    @Test
    public void testDeleteAuthor_when_id_correct() {
        when(mockedAuthorDAO.deleteAuthor(anyLong())).thenReturn(true);
        assertTrue(authorService.deleteAuthor(4L));
    }

    @Test
    public void testGetAuthorList_when_entries_found() {
        List<Author> aList = new ArrayList<>();
        aList.add(authorSample);

        List<AuthorDTO> aDTOList = new ArrayList<>();
        aDTOList.add(authorDTOSample);

        when(mockedAuthorDAO.getAuthorList()).thenReturn(aList);
        assertEquals(authorService.getAuthorList(),aDTOList);
    }

    @Test
    public void testGetAuthorList_when_no_entries_found() {
        when(mockedAuthorDAO.getAuthorList()).thenReturn(new ArrayList<>());
        assertEquals(authorService.getAuthorList().size(),0);
    }

    @Test
    public void testGetRequestListFromAuthor_when_no_entries_found() {
        when(mockedAuthorDAO.getAuthor(anyLong())).thenReturn(authorSample);
        assertNull(authorService.getRequestListFromAuthor(5L));
    }

    @Test
    public void testGetRequestListFromAuthor_when_entries_found() {
        Request reqSample = new Request();
        List<Request> reqL = new ArrayList<>();
        reqL.add(reqSample);
        authorSample.setRequestList(reqL);
        when(mockedAuthorDAO.getAuthor(anyLong())).thenReturn(authorSample);

        assertEquals(authorService.getRequestListFromAuthor(5L).size(),1);
        assertEquals(authorService.getRequestListFromAuthor(5L).get(0),new RequestDTO(reqSample));
    }

}
