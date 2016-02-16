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

    //TODO : TO REMOVE, FOR TESTING PURPOSES ONLY
    @SuppressWarnings("SameReturnValue")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<RequestDTO> showIndex() {
        return reqService.getRequestList();
    }

    //TODO : TO REMOVE, FOR TESTING PURPOSES ONLY
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

    @RequestMapping(value = "/delete_request/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteRequest(@PathVariable long id) {
        reqService.deleteRequest(id); //Erase last entry
        return "DELETED : ID - " + id;
    }

    @RequestMapping(value = "/get_request/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RequestDTO getRequest(@PathVariable long id) {
        return reqService.getRequest(id);
    }

    @RequestMapping(value = "/get_author_requests/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<RequestDTO> getAuthorRequests(@PathVariable long id) {
        return authorService.getRequestListFromAuthor(id);
    }

    @RequestMapping(value = "/get_author/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AuthorDTO getAuthor(@PathVariable long id) {
        return authorService.getAuthor(id);
    }

    @RequestMapping(value = "/get_author_list", method = RequestMethod.GET)
    @ResponseBody
    public List<AuthorDTO> getAuthorRequests() {
        return authorService.getAuthorList();
    }

    @RequestMapping(value = "/get_request_list", method = RequestMethod.GET)
    @ResponseBody
    public List<RequestDTO> getRequestList() {
        return reqService.getRequestList();
    }
}
