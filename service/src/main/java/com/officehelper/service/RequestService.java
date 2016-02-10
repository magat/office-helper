package com.officehelper.service;

import com.officehelper.dao.RequestDAO;
import com.officehelper.dto.RequestDTO;
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

    @Transactional
    public List<RequestDTO> getRequestList() {
        List<Request> rList = requestDAO.getRequestList();
        List<RequestDTO> rDTOList = new ArrayList<>();
        for (Request req : rList) {
            rDTOList.add(new RequestDTO(req));
        }
        return rDTOList;
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
    public void deleteRequest(long id) { //Garder une primitive (null n'a pas de sens)
        requestDAO.deleteRequest(id);
    }
}
