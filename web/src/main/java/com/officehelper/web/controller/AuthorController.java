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
public class AuthorController {

    @Inject
    RequestService reqService;

    @Inject
    AuthorService authorService;

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
