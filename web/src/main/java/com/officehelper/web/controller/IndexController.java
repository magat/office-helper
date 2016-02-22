package com.officehelper.web.controller;

import com.officehelper.dto.AuthorDTO;
import com.officehelper.dto.RequestDTO;
import com.officehelper.service.AuthorService;
import com.officehelper.service.RequestService;
import com.officehelper.web.ValidationResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@Controller
class IndexController {

    @Inject
    RequestService reqService;

    @Inject
    AuthorService authorService;

    //First Thymeleaf implementation :)
    @RequestMapping(value = {"/", "/request"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("requestList", reqService.getRequestList());
        model.addAttribute("request", new RequestDTO());
        model.addAttribute("author", new AuthorDTO());
        return "request_list.html";
    }

    @RequestMapping(value = "/request/add", method = RequestMethod.POST)
    @ResponseBody
    public ValidationResponse addRequest(@Valid @ModelAttribute AuthorDTO author, @Valid @ModelAttribute RequestDTO request, BindingResult bindingResult, Model model) {
        ValidationResponse response = new ValidationResponse();
        if(bindingResult.hasErrors()) {
            response.setErrorMessageList(bindingResult.getAllErrors());
        }
        else {
            request.setAuthor(author);
            reqService.addRequest(request);
        }
        return response;
    }

    @RequestMapping(value = "/request/add", method = RequestMethod.GET)
    public String addRequestForm(Model model) {
        return "request_add_form.html";
    }

    @RequestMapping(value = "/request/{id}/delete", method = RequestMethod.GET)
    @ResponseBody
    public ValidationResponse deleteRequest(@PathVariable long id) {
        ValidationResponse response = new ValidationResponse();
        if(!reqService.deleteRequest(id)) {
            response.setStatus("FAIL");
        }
        return response;
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
