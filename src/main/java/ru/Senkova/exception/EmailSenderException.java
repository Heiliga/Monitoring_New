package ru.Senkova.exception;

public class EmailSenderException extends RuntimeException {
    private Integer code;
    private String message;


    public EmailSenderException (ResponseCodeException responseCodeException){
        super(responseCodeException.getMessage());

    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}