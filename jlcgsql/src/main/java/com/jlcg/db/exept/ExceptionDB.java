package com.jlcg.db.exept;

public class ExceptionDB extends Exception {

    private static final long serialVersionUID = 1L;
    private String message;

    public ExceptionDB(String message) {
        this.message = "";
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
