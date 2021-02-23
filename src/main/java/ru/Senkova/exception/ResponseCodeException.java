package ru.Senkova.exception;

public enum ResponseCodeException {
    INT_CODE_1_LOGIN_ALREADY_EXISTS(1,"Employee with this login already exists!"),
    INT_CODE_2_EMAIL_ALREADY_EXISTS(2,"Employee with this email already exists!"),
    INT_CODE_3_NOT_DELETE_LINKS(3,"Unable to delete user links"),
    INT_CODE_4_NOT_FIND_ARTICLE(4,"Can't find the latest article"),
    INT_CODE_5_NOT_FIND_TAG_FOR_ARTICLE(5,"Unable to get tag for the last article"),
    INT_CODE_6_CONTOLLER_REGISTRATION(6,"Exception in working with the controller "),
    INT_CODE_7_NOT_Creation_Document(7,"Сan't connect to url server. Error creation object Document"),
    INT_CODE_8_NOT_FOUND_INPUT(8,"Didn't found tag-input for page Search"),
    INT_CODE_9_NOT_SAVE_LINKS(9,"It is not possible to save links for last element and page search"),
    INT_CODE_10_NOT_SAVE_LINKS_USER(10,"It is not possible to save user links");

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
