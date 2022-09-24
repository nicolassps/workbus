package br.com.workbus.builder;

import br.com.workbus.core.Workflow;
import br.com.workbus.core.WorkflowExecutor;
import br.com.workbus.core.WorkflowStep;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class WorkflowStepBuilder<T> {
    private Workflow<T> workflow;
    private T key;

    protected WorkflowStepBuilder(Workflow workflow, T key){
        this.workflow = workflow;
        this.key = key;
    }

    public WorkflowStepBuilder<T> step(T key, WorkflowStep step){
        workflow.nextStep(key, step);
        return WorkflowStepBuilder.<T>of(workflow, key);
    }

    public WorkflowStepBuilder<T> step(T key, Class<? extends WorkflowStep> step){
        workflow.nextStep(key, step);
        return WorkflowStepBuilder.<T>of(workflow, key);
    }

    public WorkflowEngineBuilder<T> callback(Consumer<Object> callback){
        workflow.addCallback(key, callback);
        return WorkflowEngineBuilder.<T>of(workflow);
    }

    public WorkflowExecutor<T> end(){
        return WorkflowExecutor.of(workflow);
    }

    public static WorkflowStepBuilder of(Workflow workflow, Object key){
        return new WorkflowStepBuilder(workflow, key);
    }
}
