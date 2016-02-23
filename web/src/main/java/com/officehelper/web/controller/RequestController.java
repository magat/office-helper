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
class RequestController {

    @Inject
    RequestService reqService;

    @Inject
    AuthorService authorService;

    @RequestMapping(value = {"/", "/request"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("requestList", reqService.getNewRequests());
        model.addAttribute("request", new RequestDTO());
        model.addAttribute("author", new AuthorDTO());
        return "request_new.html";
    }

    @RequestMapping(value = "/request/ordered", method = RequestMethod.GET)
    @ResponseBody
    public List<RequestDTO> orderedRequests(Model model) {
        return reqService.getOrderedRequests();
    }

    @RequestMapping(value = "/request/received", method = RequestMethod.GET)
    @ResponseBody
    public List<RequestDTO> receivedRequests(Model model) {
        return reqService.getReceivedRequests();
    }

    @RequestMapping(value = "/request/archived", method = RequestMethod.GET)
    @ResponseBody
    public List<RequestDTO> archivedRequests(Model model) {
        return reqService.getArchivedRequests();
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

    @RequestMapping(value = "/request/{id}/delete", method = RequestMethod.GET)
    @ResponseBody
    public ValidationResponse deleteRequest(@PathVariable long id) {
        ValidationResponse response = new ValidationResponse();
        if(!reqService.deleteRequest(id)) {
            response.setStatus("FAIL");
        }
        return response;
    }

    @RequestMapping(value = "/request/{id}/proceed_workflow", method = RequestMethod.GET)
    @ResponseBody
    public ValidationResponse proceedWorkflow(@PathVariable long id) {
        ValidationResponse response = new ValidationResponse();
        if(!reqService.proceedRequestWorkflow(id)) {
            response.setStatus("FAIL");
        }
        return response;
    }

    @RequestMapping(value = "/request/{id}/refuse", method = RequestMethod.GET)
    @ResponseBody
    public ValidationResponse refuseRequest(@PathVariable long id) {
        ValidationResponse response = new ValidationResponse();
        if(!reqService.refuseRequest(id)) {
            response.setStatus("FAIL");
        }
        return response;
    }

    @RequestMapping(value = "/request/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RequestDTO getRequest(@PathVariable long id) {
        return reqService.getRequest(id);
    }
}
