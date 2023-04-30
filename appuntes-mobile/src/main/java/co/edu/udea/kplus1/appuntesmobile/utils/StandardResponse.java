package co.edu.udea.kplus1.appuntesmobile.utils;

public class StandardResponse<T> {
    private String description;
    private String message;
    private T body;

    public StandardResponse() {
    }

    public StandardResponse(T body, String message) {
        this.message = message;
        this.body = body;
    }

    public StandardResponse(T body) {
        this.body = body;
    }

    public StandardResponse(String message, String description) {
        this.description = description;
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}

