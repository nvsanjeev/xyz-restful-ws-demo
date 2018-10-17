package com.xyz.cta.rest.ws.exception;

import java.time.LocalDateTime;

public class XyzExceptionResponse {
    private LocalDateTime time_stamp;
    private String message;
    private String details;

    public XyzExceptionResponse() {
    }

    public XyzExceptionResponse(LocalDateTime time_stamp, String message, String details) {
        this.time_stamp = time_stamp;
        this.message = message;
        this.details = details;
    }


    public LocalDateTime getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(LocalDateTime time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
