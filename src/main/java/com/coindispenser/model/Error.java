package com.coindispenser.model;

public class Error {
    private String errorType;
    private String errorMessage;
    private int stutus;


    public Error(int stutus, String errorType, String errorMessage) {
        this.stutus = stutus;
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }

    public int getStutus() {
        return stutus;
    }

    public void setStutus(int stutus) {
        this.stutus = stutus;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
