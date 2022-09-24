package br.com.workbus.core;

import br.com.workbus.exception.InvalidOperationException;
import br.com.workbus.model.WorkflowStepResult;

import java.util.*;

import static java.util.Objects.isNull;

public class WorkflowExecutor<T> {
    private Workflow<T> workflow;
    private Iterator<T> iterator;
    private T last;
    private List<WorkflowStepResult> results = new ArrayList();

    protected WorkflowExecutor(Workflow workflow){
        this.workflow = workflow;
        this.iterator = workflow.executeOrder.iterator();
    }

    public void execute(){
        checkAndExecutePreStep();

        iterator.forEachRemaining(this::call);
    }

    public void executeNextStep(){
        checkAndExecutePreStep();

        if(!iterator.hasNext())
            throw new InvalidOperationException("No more steps to execute");

        this.call(iterator.next());
    }

    public void executePreviousStep(){
        if(isNull(last))
            throw new InvalidOperationException("No steps have been performed");

        this.call(last);
    }

    private void call(T key){
        last = key;
        var step = workflow.steps.get(key);

        step.preProcessor();
        step.execute();
        step.postProcessor();

        results.add(new WorkflowStepResult<T>(key, step.hasSuccess(), step.getResultMessage()));

        Optional
           .ofNullable(workflow.callbacks.get(key))
           .ifPresent(callback -> callback.accept(step.data()));
    }

    private void checkAndExecutePreStep(){
        if(isNull(last)){
            Optional
                .ofNullable(workflow.preStep)
                .ifPresent(step ->{
                    step.preProcessor();
                    step.execute();
                    step.postProcessor();
                });
        }
    }

    public List<WorkflowStepResult> getResults(){
        return results;
    }

    public static <T> WorkflowExecutor of(Workflow workflow){
        return new WorkflowExecutor(workflow);
    }

    @Override
    public String toString() {
        return workflow.toString();
    }
}
