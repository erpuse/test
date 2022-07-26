package com.erelmanagement.registerlogin.model;

import java.util.List;

public class View {

    String id;
    String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<View> getResult() {
        return result;
    }

    public void setResult(List<View> result) {
        this.result = result;
    }

    String value;
    String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    List<View> result;


}
