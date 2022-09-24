package br.com.workbus.model;

import static java.lang.String.format;

public class WorkflowStepResult<T> {
    private T key;
    private Boolean success;
    private String message;

    public WorkflowStepResult(T key,
                              Boolean success,
                              String message) {
        this.key = key;
        this.success = success;
        this.message = message;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return format("{key: %s, success: %s, message: \"%s\"}", key, success, message);
    }
}
