package com.officehelper.service;

import com.officehelper.dao.RequestDAO;
import com.officehelper.dto.RequestDTO;
import com.officehelper.entity.Author;
import com.officehelper.entity.Request;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
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

    public Request generateRequest() {
        Request generatedReq = new Request();
        generatedReq.setDateCreated(new Date(0));
        generatedReq.setAuthor(new Author());
        return generatedReq;
    }

    public List<Request> generateRequestList(int size) {
        List<Request> requestList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            requestList.add(generateRequest());
        }
        return requestList;
    }

    public RequestDTO generateRequestDTO() {
        return new RequestDTO(generateRequest());
    }

    public List<RequestDTO> generateRequestDTOList(int size) {
        List<RequestDTO> requestList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            requestList.add(generateRequestDTO());
        }
        return requestList;
    }

    @Test
    public void testAddRequest() { //Testing if we get the new ID when we add a request
        RequestDTO toSend = new RequestDTO();
        when(mockedRequestDAO.addRequest(any())).thenReturn(2L);
        assertEquals(requestService.addRequest(toSend), 2L);
    }

    @Test
    public void testGetRequest_when_entry_found() { //Testing if we get a request when we ask for it
        /* If there is a match in the database */
        Request received = new Request();
        received.setDateCreated(new Date());
        received.setAuthor(new Author());
        when(mockedRequestDAO.getRequest(10L)).thenReturn(received);
        assertEquals(requestService.getRequest(10L), new RequestDTO(received));
    }

    @Test
    public void testGetRequest_when_no_entry_found() {
        when(mockedRequestDAO.getRequest(10L)).thenReturn(null);
        assertEquals(requestService.getRequest(10L), new RequestDTO(null));
    }

    @Test
    public void testDeleteRequest_when_id_incorrect() {
        when(mockedRequestDAO.deleteRequest(10L)).thenReturn(false);
        assertFalse(requestService.deleteRequest(10L));
    }

    @Test
    public void testDeleteRequest_when_id_correct() {
        when(mockedRequestDAO.deleteRequest(10L)).thenReturn(true);
        assertTrue(requestService.deleteRequest(10L));
    }

    @Test
    public void testGetRequestList_when_entries_found() {
        /* Testing when the database contains entries */
        //Request 1
        Request receivedRequest = new Request();
        receivedRequest.setDateCreated(new Date());
        receivedRequest.setAuthor(new Author());

        //Request 2
        Request receivedRequest2 = new Request();
        receivedRequest2.setDateCreated(new Date());
        receivedRequest2.setAuthor(new Author());

        List<Request> receivedRequestList = new ArrayList<>();
        receivedRequestList.add(receivedRequest);
        receivedRequestList.add(receivedRequest2);

        //DTO RequestList
        List<RequestDTO> sentRequestListDTO = new ArrayList<>();
        sentRequestListDTO.add(new RequestDTO(receivedRequest));
        sentRequestListDTO.add(new RequestDTO(receivedRequest2));

        when(mockedRequestDAO.getRequestList()).thenReturn(receivedRequestList);
        assertEquals(requestService.getRequestList(), sentRequestListDTO);
    }

    @Test
    public void testGetRequestList_when_no_entries_found() {
        /*Testing when the database is empty*/
        when(mockedRequestDAO.getRequestList()).thenReturn(new ArrayList<Request>());
        assertEquals(requestService.getRequestList(), new ArrayList<RequestDTO>());
    }

    @Test
    public void testGetNewRequests_when_entries_found() {
        when(mockedRequestDAO.getNewRequests()).thenReturn(generateRequestList(5));
        assertEquals(requestService.getNewRequests(), generateRequestDTOList(5));
    }

    @Test
    public void testGetNewRequests_when_no_entries_found() {
        when(mockedRequestDAO.getNewRequests()).thenReturn(new ArrayList<Request>());
        assertEquals(requestService.getNewRequests(), new ArrayList<RequestDTO>());
    }

    @Test
    public void testGetOrderedRequests_when_entries_found() {
        when(mockedRequestDAO.getOrderedRequests()).thenReturn(generateRequestList(5));
        assertEquals(requestService.getOrderedRequests(), generateRequestDTOList(5));
    }

    @Test
    public void testGetOrderedRequests_when_no_entries_found() {
        when(mockedRequestDAO.getOrderedRequests()).thenReturn(new ArrayList<Request>());
        assertEquals(requestService.getOrderedRequests(), new ArrayList<RequestDTO>());
    }

    @Test
    public void testGetReceivedRequests_when_entries_found() {
        when(mockedRequestDAO.getReceivedRequests()).thenReturn(generateRequestList(5));
        assertEquals(requestService.getReceivedRequests(), generateRequestDTOList(5));
    }

    @Test
    public void testGetReceivedRequests_when_no_entries_found() {
        when(mockedRequestDAO.getReceivedRequests()).thenReturn(new ArrayList<Request>());
        assertEquals(requestService.getReceivedRequests(), new ArrayList<RequestDTO>());
    }

    @Test
    public void testGetArchivedRequests_when_entries_found() {
        when(mockedRequestDAO.getArchivedRequests()).thenReturn(generateRequestList(5));
        assertEquals(requestService.getArchivedRequests(), generateRequestDTOList(5));
    }

    @Test
    public void testGetArchivedRequests_when_no_entries_found() {
        when(mockedRequestDAO.getArchivedRequests()).thenReturn(new ArrayList<Request>());
        assertEquals(requestService.getArchivedRequests(), new ArrayList<RequestDTO>());
    }

    @Test
    public void testRefuseRequest_when_request_exists() {
        when(mockedRequestDAO.updateStatus(anyLong(), any())).thenReturn(true);
        assertTrue(requestService.refuseRequest(5));
    }

    @Test
    public void testRefuseRequest_when_request_does_not_exist() {
        when(mockedRequestDAO.updateStatus(anyLong(), any())).thenReturn(false);
        assertFalse(requestService.refuseRequest(5));
    }
}
