package com.pe.vietanhs0817.pecrudapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by vietanhs0817 on 10/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response implements Serializable {

    private String Message;
    private boolean Status;
    private Object Object;

    @JsonProperty("Message")
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
    @JsonProperty("Status")
    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
    @JsonProperty("Object")
    public java.lang.Object getObject() {
        return Object;
    }

    public void setObject(java.lang.Object object) {
        Object = object;
    }
}
