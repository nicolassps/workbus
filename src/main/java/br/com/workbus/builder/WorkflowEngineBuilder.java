package br.com.workbus.builder;

import br.com.workbus.core.Workflow;
import br.com.workbus.core.WorkflowExecutor;
import br.com.workbus.core.WorkflowStep;

public class WorkflowEngineBuilder<T> {
    private final Workflow<T> workflow;

    protected WorkflowEngineBuilder(Workflow<T> workflow){
        this.workflow = workflow;
    }

    public WorkflowStepBuilder<T> step(T key, WorkflowStep<?> step){
        workflow.nextStep(key, step);
        return WorkflowStepBuilder.<T>of(workflow, key);
    }

    public WorkflowStepBuilder<T> step(T key, Class<? extends WorkflowStep<?>> step){
        workflow.nextStep(key, step);
        return WorkflowStepBuilder.<T>of(workflow, key);
    }

    public WorkflowExecutor<T> end(){
        return WorkflowExecutor.of(workflow);
    }

    public static <T> WorkflowEngineBuilder<T> of(Workflow<T> workflow){
        return new WorkflowEngineBuilder<T>(workflow);
    }
}
