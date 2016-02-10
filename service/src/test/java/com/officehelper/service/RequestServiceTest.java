package com.officehelper.service;

import com.officehelper.dao.RequestDAO;
import com.officehelper.dto.RequestDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


public class RequestServiceTest {

    @Mock
    private RequestDAO mockedRequestDAO;

    @InjectMocks
    private RequestService requestService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void serviceOk() {
        RequestDTO toSend = new RequestDTO();
        when(mockedRequestDAO.addRequest(any())).thenReturn(2L);
        //assertEquals(toSend.getAuthor(), received.getAuthor());
    }

}
