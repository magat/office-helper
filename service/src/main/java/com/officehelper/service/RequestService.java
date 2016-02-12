package com.officehelper.service;

import com.officehelper.dao.RequestDAO;
import com.officehelper.dto.RequestDTO;
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

    @Transactional
    public List<RequestDTO> getRequestList() {
        List<Request> rList = requestDAO.getRequestList();
        return rList.stream().map(RequestDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public RequestDTO getRequest(long id) {
        return new RequestDTO(requestDAO.getRequest(id));
    }

    @Transactional
    public long addRequest(RequestDTO req) {
        return requestDAO.addRequest(req.toRequest());
    }

    @Transactional
    public boolean deleteRequest(long id) {
        return requestDAO.deleteRequest(id);
    }
}
