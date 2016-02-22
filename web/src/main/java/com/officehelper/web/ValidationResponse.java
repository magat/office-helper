package com.officehelper.web;

import java.util.List;

public class ValidationResponse {
    private String status;
    private List errorMessageList;

    public ValidationResponse() {
        this.status = "SUCCESS";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List getErrorMessageList() {
        return this.errorMessageList;
    }

    public void setErrorMessageList(List errorMessageList) {
        if(errorMessageList != null) {
            if(errorMessageList.size() > 0) {
                this.status = "FAIL";
            }
        }
        this.errorMessageList = errorMessageList;
    }
}
