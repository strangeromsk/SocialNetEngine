package ru.skillbox.socialnetworkimpl.sn.domain.enums;


public enum ErrorMessages {
    INTERROR("Internal error"),
    USER_INVALID("User is invalid"),
    USER_EXISTS("User already exist"),
    USER_NOTEXIST("User does not exist"),
    PASS_EMAIL_INC("Password or e-mail is incorrect"),
    INCORRECT_EMAIL("E-mail is incorrect");

    private String title;

    ErrorMessages(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
