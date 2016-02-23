package com.officehelper.service;

import com.officehelper.dao.RequestDAO;
import com.officehelper.dto.RequestDTO;
import com.officehelper.entity.Request;
import com.officehelper.entity.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
    public RequestDTO getRequest(long id) {
        Request req = requestDAO.getRequest(id);
        return new RequestDTO(req);
    }

    @Transactional
    public long addRequest(RequestDTO req) {
        Request r = req.toRequest();
        r.setAuthor(req.getAuthor().toAuthor());
        return requestDAO.addRequest(r);
    }

    @Transactional
    public boolean proceedRequestWorkflow(long id) {
        Request r = requestDAO.getRequest(id);
        Status currentStatus = r.getStatus();
        if(currentStatus == Status.NEW) {
            currentStatus = Status.ORDERED;
        }
        else if(currentStatus == Status.ORDERED) {
            currentStatus = Status.RECEIVED;
        }
        return requestDAO.updateStatus(id,currentStatus);
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
