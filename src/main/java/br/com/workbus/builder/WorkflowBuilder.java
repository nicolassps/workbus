package br.com.workbus.builder;

import br.com.workbus.core.Workflow;
import br.com.workbus.core.WorkflowStep;

public class WorkflowBuilder<T> {
    private final Workflow<T> workflow;

    protected WorkflowBuilder(Workflow<T> workflow){
        this.workflow = workflow;
    }

    public WorkflowEngineBuilder<T> pre(WorkflowStep<?> step){
        workflow.setPreStep(step);
        return WorkflowEngineBuilder.<T>of(workflow);
    }

    public WorkflowStepBuilder<T> step(T key, WorkflowStep<T> step){
        workflow.nextStep(key, step);
        return WorkflowStepBuilder.<T>of(workflow, key);
    }

    public WorkflowStepBuilder<T> step(T key, Class<? extends WorkflowStep<?>> step){
        workflow.nextStep(key, step);
        return WorkflowStepBuilder.<T>of(workflow, key);
    }

    public static <T> WorkflowBuilder<T> of(Workflow<T> workflow){
        return new WorkflowBuilder<T>(workflow);
    }
}
