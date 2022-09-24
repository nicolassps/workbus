package br.com.workbus.core;

public abstract class WorkflowStep<T> {
    protected T data;
    protected String resultMessage;

    public WorkflowStep(){super();};

    protected abstract void preProcessor();

    protected abstract void execute();

    protected abstract void postProcessor();

    protected abstract Boolean hasSuccess();

    T data(){
        return data;
    }

    String getResultMessage(){
        return resultMessage;
    }
}
