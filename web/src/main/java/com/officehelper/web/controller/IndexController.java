package com.officehelper.web.controller;

import com.officehelper.dto.RequestDTO;
import com.officehelper.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

@Controller
class IndexController {

    @Inject
    RequestService reqService;

    @SuppressWarnings("SameReturnValue")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<RequestDTO> showIndex() {
        return reqService.getRequestList();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public long addRandomStuff() {
        RequestDTO newReq = new RequestDTO();
        newReq.setUrl("http://Valeur-Ajoutée.com");
        newReq.setTitle("Post-its");
        newReq.setStatus("WAIT ...");
        newReq.setAuthor("User");
        return reqService.addRequest(newReq);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public String deleteStuff() {
        List<RequestDTO> temp = reqService.getRequestList();
        long lastId = temp.get(temp.size() - 1).getId();
        reqService.deleteRequest(lastId); //Erase last entry
        return "DELETED : ID - " + lastId;
    }
}
