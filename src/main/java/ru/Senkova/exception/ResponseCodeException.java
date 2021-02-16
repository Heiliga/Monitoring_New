package ru.Senkova.exception;

public enum ResponseCodeException {
    INT_CODE_1_LOGIN_ALREADY_EXISTS(1,"Employee with this login already exists!"),
    INT_CODE_2_EMAIL_ALREADY_EXISTS(2,"Employee with this email already exists!"),
    INT_CODE_3_LOGIN_ALREADY_EXISTS(3,"Employee with this login already exists!"),
    INT_CODE_4_INVALID_ROLE_REGISTRATION(4,"Invalid role was given for registration"),
    INT_CODE_5_NOT_NAME_WITH_ROLE_DB(5,"Could not find provided role by role name in the database"),
    INT_CODE_6_EXCEPTION_CONTOLLER_REGISTRATION(6,"Exception in working with the controller ");

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
