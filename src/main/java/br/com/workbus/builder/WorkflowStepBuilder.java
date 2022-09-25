package br.com.workbus.builder;

import br.com.workbus.core.Workflow;
import br.com.workbus.core.WorkflowExecutor;
import br.com.workbus.core.WorkflowStep;

import java.util.function.Consumer;

public class WorkflowStepBuilder<T> {
    private final Workflow<T> workflow;
    private T key;

    protected WorkflowStepBuilder(Workflow<T> workflow, T key){
        this.workflow = workflow;
        this.key = key;
    }

    public WorkflowStepBuilder<T> step(T key, WorkflowStep<?> step){
        workflow.nextStep(key, step);
        return WorkflowStepBuilder.<T>of(workflow, key);
    }

    public WorkflowStepBuilder<T> step(T key, Class<? extends WorkflowStep<?>> step){
        workflow.nextStep(key, step);
        return WorkflowStepBuilder.<T>of(workflow, key);
    }

    public <K> WorkflowEngineBuilder<T> callback(Consumer<K> callback){
        workflow.addCallback(key, callback);
        return WorkflowEngineBuilder.<T>of(workflow);
    }

    public WorkflowExecutor<T> end(){
        return WorkflowExecutor.<T>of(workflow);
    }

    public static <T> WorkflowStepBuilder<T> of(Workflow<T> workflow, T key){
        return new WorkflowStepBuilder<T>(workflow, key);
    }
}
