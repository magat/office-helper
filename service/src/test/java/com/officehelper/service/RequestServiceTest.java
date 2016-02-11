package com.officehelper.service;

import com.officehelper.dao.RequestDAO;
import com.officehelper.dto.RequestDTO;
import com.officehelper.entity.Request;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
    public void testAddRequest() { //Testing if we get the new ID when we add a request
        RequestDTO toSend = new RequestDTO();
        when(mockedRequestDAO.addRequest(any())).thenReturn(2L);
        assertEquals(requestService.addRequest(toSend), 2L);
    }

    @Test
    public void testGetRequest() { //Testing if we get a request when we ask for it
        /* If there is a match in the database */
        Request received = new Request();
        received.setDateCreated(new Date());
        received.setAuthor("User");
        when(mockedRequestDAO.getRequest(10L)).thenReturn(received);
        assertEquals(requestService.getRequest(10L), new RequestDTO(received));
    }

    @Test
    public void testGetRequestList() {
        /* Testing when the database contains entries */
        //Request 1
        Request receivedRequest = new Request();
        receivedRequest.setDateCreated(new Date());
        receivedRequest.setAuthor("User");

        //Request 2
        Request receivedRequest2 = new Request();
        receivedRequest2.setDateCreated(new Date());
        receivedRequest2.setAuthor("User 2");

        List<Request> receivedRequestList = new ArrayList<>();
        receivedRequestList.add(receivedRequest);
        receivedRequestList.add(receivedRequest2);

        //DTO RequestList
        List<RequestDTO> sentRequestListDTO = new ArrayList<>();
        sentRequestListDTO.add(new RequestDTO(receivedRequest));
        sentRequestListDTO.add(new RequestDTO(receivedRequest2));

        when(mockedRequestDAO.getRequestList()).thenReturn(receivedRequestList);
        assertEquals(requestService.getRequestList(), sentRequestListDTO);

        /*Testing when the database is empty*/
        when(mockedRequestDAO.getRequestList()).thenReturn(new ArrayList<Request>());
        assertEquals(requestService.getRequestList(), new ArrayList<RequestDTO>());
    }

}
