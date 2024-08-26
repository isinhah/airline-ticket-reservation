package com.airline_ticket.api.exceptions.handler;

import java.time.LocalDateTime;

public class ExceptionFields extends ExceptionResponse {
    private final String fields;
    private final String fieldsMessage;

    public ExceptionFields(String title, int status, String exception, String detail, LocalDateTime timestamp, String fields, String fieldsMessage) {
        super(title, status, exception, detail, timestamp);
        this.fields = fields;
        this.fieldsMessage = fieldsMessage;
    }

    public String getFields() {
        return fields;
    }

    public String getFieldsMessage() {
        return fieldsMessage;
    }
}
