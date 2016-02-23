package com.officehelper.web.controller;

import com.officehelper.dto.AuthorDTO;
import com.officehelper.dto.RequestDTO;
import com.officehelper.service.AuthorService;
import com.officehelper.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {

    @Inject
    RequestService reqService;

    @Inject
    AuthorService authorService;

    //First Thymeleaf implementation :)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("recipient", "World");
        return "index.html";
    }

    @RequestMapping(value = "/request", method = RequestMethod.GET)
    public String getRequestList(Model model) {
        model.addAttribute("requestList", reqService.getRequestList());
        return "request_list.html";
    }

    @RequestMapping(value = "/request/add", method = RequestMethod.POST)
    public String addRequest(Model model,
                             @RequestParam("first_name") String firstName,
                             @RequestParam("last_name") String lastName,
                             @RequestParam("email") String email,
                             @RequestParam("title") String title,
                             @RequestParam("url") String url,
                             @RequestParam("comments") String comments) {
        AuthorDTO author = new AuthorDTO();
        author.setEmail(email);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        RequestDTO request = new RequestDTO();
        request.setAuthor(author);
        request.setComments(comments);
        request.setTitle(title);
        request.setUrl(url);
        request.setDateCreated(new Date());
        request.setStatus("Pending");
        long reqId = reqService.addRequest(request);
        return "request_list.html";
    }

    @RequestMapping(value = "/request/add", method = RequestMethod.GET)
    public String addRequestForm(Model model) {
        return "request_add_form.html";
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

    @RequestMapping(value = "/request/{id}/delete", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteRequest(@PathVariable long id) {
        return reqService.deleteRequest(id);
    }

    @RequestMapping(value = "/request/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RequestDTO getRequest(@PathVariable long id) {
        return reqService.getRequest(id);
    }

    @RequestMapping(value = "/author/{id}/requests", method = RequestMethod.GET)
    @ResponseBody
    public List<RequestDTO> getAuthorRequests(@PathVariable long id) {
        return authorService.getRequestListFromAuthor(id);
    }

    @RequestMapping(value = "/author/{id}/delete", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteAuthor(@PathVariable long id) {
        return authorService.deleteAuthor(id);
    }

    @RequestMapping(value = "/author/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AuthorDTO getAuthor(@PathVariable long id) {
        return authorService.getAuthor(id);
    }

    @RequestMapping(value = "/author", method = RequestMethod.GET)
    @ResponseBody
    public List<AuthorDTO> getAuthorList() {
        return authorService.getAuthorList();
    }
}
