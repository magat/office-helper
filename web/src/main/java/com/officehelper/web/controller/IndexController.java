package com.officehelper.web.controller;

import com.officehelper.dto.AuthorDTO;
import com.officehelper.dto.RequestDTO;
import com.officehelper.service.AuthorService;
import com.officehelper.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

@Controller
class IndexController {

    @Inject
    RequestService reqService;

    @Inject
    AuthorService authorService;

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
        AuthorDTO author = new AuthorDTO();
        newReq.setAuthor(author);
        return reqService.addRequest(newReq);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteStuff(@PathVariable long id) {
        reqService.deleteRequest(id); //Erase last entry
        return "DELETED : ID - " + id;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RequestDTO getStuff(@PathVariable long id) {
        return reqService.getRequest(id);
    }

    //TODO : To remove, temporary testing Route
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public List<RequestDTO> testingFunction() {
        return authorService.getRequestListFromAuthor(1);
    }
}
