package br.com.workbus.builder;

import br.com.workbus.core.Workflow;
import br.com.workbus.core.WorkflowStep;

public class WorkflowBuilder<T> {
    private Workflow<T> workflow;

    protected WorkflowBuilder(Workflow<T> workflow){
        this.workflow = workflow;
    }

    protected WorkflowEngineBuilder pre(WorkflowStep step){
        workflow.setPreStep(step);
        return WorkflowEngineBuilder.of(workflow);
    }

    public WorkflowStepBuilder<T> step(T key, WorkflowStep step){
        workflow.nextStep(key, step);
        return WorkflowStepBuilder.<T>of(workflow, key);
    }

    public WorkflowStepBuilder<T> step(T key, Class<? extends WorkflowStep> step){
        workflow.nextStep(key, step);
        return WorkflowStepBuilder.<T>of(workflow, key);
    }

    public static <T> WorkflowBuilder of(Workflow<T> workflow){
        return new WorkflowBuilder<T>(workflow);
    }
}
