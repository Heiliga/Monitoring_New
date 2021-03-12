package ru.Senkova.exception;

public enum ResponseCodeException {
    LOGIN_ALREADY_EXISTS(1,"Employee with this login already exists!"),
    EMAIL_ALREADY_EXISTS(2,"Employee with this email already exists!"),
    CONTROLLER_REGISTRATION(3,"Exception in working with the controller "),
    NOT_Creation_Document(4,"Сan't connect to url server. Error creation object Document"),
    NOT_FOUND_INPUT(5,"Didn't found tag-input for page Search");

    private Integer code;
    private String message;

    ResponseCodeException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
