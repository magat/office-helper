package com.officehelper.service;

import com.officehelper.dao.RequestDAO;
import com.officehelper.dto.RequestDTO;
import com.officehelper.entity.Author;
import com.officehelper.entity.Request;
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
    public RequestDTO getRequest(long id) {
        Request req = requestDAO.getRequest(id);
        return new RequestDTO(req);
    }

    @Transactional
    public long addRequest(RequestDTO req) {
        //TODO : TEMPORARY, TO REPLACE FUTURE SESSION SYSTEM
        Request r = req.toRequest();
        r.setAuthor(new Author());
        return requestDAO.addRequest(r);
    }

    @Transactional
    public boolean deleteRequest(long id) {
        return requestDAO.deleteRequest(id);
    }
}
