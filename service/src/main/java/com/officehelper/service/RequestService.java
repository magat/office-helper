package com.officehelper.service;

import com.officehelper.dao.RequestDAO;
import com.officehelper.dto.RequestDTO;
import com.officehelper.entity.Request;
import com.officehelper.entity.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {

    @Inject
    private RequestDAO requestDAO;

    @Transactional(readOnly = true)
    public List<RequestDTO> getRequestList() {
        List<Request> rList = requestDAO.getRequestList();
        return rList.stream().map(RequestDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RequestDTO> getNewRequests() {
        List<Request> rList = requestDAO.getNewRequests();
        return rList.stream().map(RequestDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RequestDTO> getOrderedRequests() {
        List<Request> rList = requestDAO.getOrderedRequests();
        return rList.stream().map(RequestDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RequestDTO> getReceivedRequests() {
        List<Request> rList = requestDAO.getReceivedRequests();
        return rList.stream().map(RequestDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RequestDTO> getArchivedRequests() {
        List<Request> rList = requestDAO.getArchivedRequests();
        return rList.stream().map(RequestDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RequestDTO getRequest(long id) {
        Request req = requestDAO.getRequest(id);
        return new RequestDTO(req);
    }

    @Transactional
    public long addRequest(RequestDTO req) {
        Request r = req.toRequest();
        return requestDAO.addRequest(r);
    }

    @Transactional
    public boolean proceedRequestWorkflow(long id) { //TODO : 2 SQL Requests for one task :/
        Request r = requestDAO.getRequest(id);
        Status currentStatus = r.getStatus();
        boolean dateRefreshed = false;
        if(currentStatus == Status.NEW) {
            currentStatus = Status.ORDERED;
            dateRefreshed = requestDAO.updateRequestOrderDate(id, new Date());
        }
        else if(currentStatus == Status.ORDERED) {
            currentStatus = Status.RECEIVED;
            dateRefreshed = requestDAO.updateRequestDeliveryDate(id, new Date());
        }
        if(dateRefreshed) {
            return requestDAO.updateStatus(id, currentStatus);
        }
        return false;
    }

    @Transactional
    public boolean revertRequestWorkflow(long id) { //TODO : 2 SQL Requests for one task :/
        Request r = requestDAO.getRequest(id);
        Status currentStatus = r.getStatus();
        boolean dateRefreshed = false;
        if(currentStatus == Status.RECEIVED) {
            currentStatus = Status.ORDERED;
            dateRefreshed = requestDAO.updateRequestDeliveryDate(id, null);
        }
        else if(currentStatus == Status.ORDERED) {
            currentStatus = Status.NEW;
            dateRefreshed = requestDAO.updateRequestOrderDate(id, null);
        }
        if(dateRefreshed) {
            return requestDAO.updateStatus(id, currentStatus);
        }
        return false;
    }

    @Transactional
    public boolean refuseRequest(long id) {
        return requestDAO.updateStatus(id,Status.REFUSED);
    }

    @Transactional
    public boolean deleteRequest(long id) {
        return requestDAO.deleteRequest(id);
    }
}
