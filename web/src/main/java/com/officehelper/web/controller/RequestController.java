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

@Controller
public class RequestController {

    @Inject
    RequestService reqService;

    @Inject
    AuthorService authorService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index(Model model) {
        return "redirect:/request/new";
    }

    @RequestMapping(value = "/request/new", method = RequestMethod.GET)
    public String newRequests(Model model) {
        model.addAttribute("requestList", reqService.getNewRequests());
        model.addAttribute("request", new RequestDTO());
        model.addAttribute("author", new AuthorDTO());
        return "request_new.html";
    }

    @RequestMapping(value = "/request/ordered", method = RequestMethod.GET)
    public String orderedRequests(Model model) {
        model.addAttribute("requestList", reqService.getOrderedRequests());
        model.addAttribute("request", new RequestDTO());
        model.addAttribute("author", new AuthorDTO());
        return "request_ordered.html";
    }

    @RequestMapping(value = "/request/received", method = RequestMethod.GET)
    public String receivedRequests(Model model) {
        model.addAttribute("requestList", reqService.getReceivedRequests());
        model.addAttribute("request", new RequestDTO());
        model.addAttribute("author", new AuthorDTO());
        return "request_received.html";
    }

    @RequestMapping(value = "/request/archived", method = RequestMethod.GET)
    public String archivedRequests(Model model) {
        model.addAttribute("requestList", reqService.getArchivedRequests());
        model.addAttribute("request", new RequestDTO());
        model.addAttribute("author", new AuthorDTO());
        return "request_archived.html";
    }

    @RequestMapping(value = "/request/add", method = RequestMethod.POST)
    @ResponseBody
    public ValidationResponse addRequest(@Valid @ModelAttribute AuthorDTO author, BindingResult authorResult,
                                         @Valid @ModelAttribute RequestDTO request, BindingResult requestResult) {
        ValidationResponse response = new ValidationResponse();
        if(authorResult.hasErrors()) {
            response.setErrorMessageList(authorResult.getAllErrors());
        }
        else if(requestResult.hasErrors()) {
            response.setErrorMessageList(requestResult.getAllErrors());
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

    @RequestMapping(value = "/request/{id}/revert_workflow", method = RequestMethod.GET)
    @ResponseBody
    public ValidationResponse revertWorkflow(@PathVariable long id) {
        ValidationResponse response = new ValidationResponse();
        if(!reqService.revertRequestWorkflow(id)) {
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
