package com.officehelper.service;

import com.officehelper.dao.AuthorDAO;
import com.officehelper.dto.AuthorDTO;
import com.officehelper.dto.RequestDTO;
import com.officehelper.entity.Author;
import com.officehelper.entity.Request;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Inject
    private AuthorDAO authorDAO;

    @Transactional(readOnly=true)
    public List<AuthorDTO> getAuthorList() {
        List<Author> rList = authorDAO.getAuthorList();
        return rList.stream().map(AuthorDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public AuthorDTO getAuthor(long id) {
        return new AuthorDTO(authorDAO.getAuthor(id));
    }

    @Transactional
    public long addAuthor(AuthorDTO auth) {
        return authorDAO.addAuthor(auth.toAuthor());
    }

    @Transactional
    public boolean deleteAuthor(long id) {
        return authorDAO.deleteAuthor(id);
    }

    @Transactional(readOnly=true)
    public List<RequestDTO> getRequestListFromAuthor(long authorId) {
        Author auth = authorDAO.getAuthor(authorId);
        if(auth != null) {
            List<Request> rList = auth.getRequestList();
            if(rList != null) {
                return rList.stream().map(RequestDTO::new).collect(Collectors.toList());
            }
        }
        return null;
    }
}
