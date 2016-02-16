package com.officehelper.service;

import com.officehelper.dao.AuthorDAO;
import com.officehelper.dao.RequestDAO;
import com.officehelper.dto.RequestDTO;
import com.officehelper.entity.Author;
import com.officehelper.entity.Request;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    @Inject
    private RequestDAO requestDAO;

    //TODO : TO BE REMOVED
    @Inject
    private AuthorDAO authorDAO;

    @Transactional
    public List<RequestDTO> getRequestList() {
        List<Request> rList = requestDAO.getRequestList();
        List<RequestDTO> dtoList = new ArrayList<>();
        for(Request r : rList) {
            r.getAuthor().setRequests(null); //Prevents infinite Author <-> Request Loop
            dtoList.add(new RequestDTO(r));
        }
        //rList.stream().map(RequestDTO::new).collect(Collectors.toList());
        return dtoList;
    }

    @Transactional
    public RequestDTO getRequest(long id) {
        return new RequestDTO(requestDAO.getRequest(id));
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
