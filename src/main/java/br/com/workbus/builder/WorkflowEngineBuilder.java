package br.com.workbus.builder;

import br.com.workbus.core.Workflow;
import br.com.workbus.core.WorkflowExecutor;
import br.com.workbus.core.WorkflowStep;

public class WorkflowEngineBuilder<T> {
    private Workflow<T> workflow;

    protected WorkflowEngineBuilder(Workflow workflow){
        this.workflow = workflow;
    }

    public WorkflowStepBuilder<T> step(T key, WorkflowStep step){
        workflow.nextStep(key, step);
        return WorkflowStepBuilder.<T>of(workflow, key);
    }

    public WorkflowStepBuilder<T> step(T key, Class<? extends WorkflowStep> step){
        workflow.nextStep(key, step);
        return WorkflowStepBuilder.<T>of(workflow, key);
    }

    public WorkflowExecutor<T> end(){
        return WorkflowExecutor.of(workflow);
    }

    public static WorkflowEngineBuilder of(Workflow workflow){
        return new WorkflowEngineBuilder(workflow);
    }
}
