package br.com.workbus.core;

import br.com.workbus.exception.InvalidOperationException;
import br.com.workbus.exception.PreStepFailedException;
import br.com.workbus.model.WorkflowStepResult;

import java.util.*;

import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;

public class WorkflowExecutor<T> {
    private final Workflow<T> workflow;
    private final Iterator<T> iterator;
    private T last;
    private final List<WorkflowStepResult<T>> results = new ArrayList();

    protected WorkflowExecutor(Workflow<T> workflow) {
        this.workflow = workflow;
        this.iterator = workflow.executeOrder.iterator();
    }

    public void execute() {
        checkAndExecutePreStep();

        iterator.forEachRemaining(this::call);
    }

    public void executeNextStep() {
        checkAndExecutePreStep();

        if (!iterator.hasNext())
            throw new InvalidOperationException("No more steps to execute");

        this.call(iterator.next());
    }

    public void executePreviousStep() {
        if (isNull(last))
            throw new InvalidOperationException("No steps have been performed");

        this.call(last);
    }

    private void call(T key) {
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

    private void checkAndExecutePreStep() {
        if (isNull(last)) {
            workflow.preStep
                .ifPresent(step -> {
                    step.preProcessor();
                    step.execute();
                    step.postProcessor();

                    if(!step.hasSuccess())
                        throw new PreStepFailedException("Pre Step failed, message: " + step.getResultMessage());
                });
        }
    }

    public List<WorkflowStepResult<T>> getResults() {
        return results;
    }

    public static <T> WorkflowExecutor<T> of(Workflow<T> workflow) {
        return new WorkflowExecutor<T>(workflow);
    }

    @Override
    public String toString() {
        return workflow.toString();
    }
}
