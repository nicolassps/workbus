package br.com.workbus.core;

import br.com.workbus.builder.WorkflowBuilder;
import br.com.workbus.exception.DuplicatedKeyException;
import br.com.workbus.exception.InvalidKeyException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Consumer;

import static java.lang.Boolean.FALSE;
import static java.lang.String.format;

public class Workflow<T> {
    protected Optional<WorkflowStep<?>> preStep = Optional.empty();
    protected HashMap<T, WorkflowStep<?>> steps = new HashMap<>();
    protected HashMap<T, Consumer<Object>> callbacks = new HashMap<>();
    protected LinkedList<T> executeOrder = new LinkedList<T>();

    protected Workflow(){}

    public static <T> WorkflowBuilder<T> builder() {
        return WorkflowBuilder.<T>of(new Workflow<T>());
    }

    public void setPreStep(WorkflowStep<?> preStep){
        this.preStep = Optional.of(preStep);
    }

    public void nextStep(T key, WorkflowStep<?> step){
        if(this.steps.containsKey(key))
            throw new InvalidKeyException(format("The step key %s already exists", key));

        this.executeOrder.add(key);
        this.steps.put(key, step);
    }

    public void nextStep(T key, Class<? extends WorkflowStep<?>> step){
        if(this.steps.containsKey(key))
            throw new DuplicatedKeyException(format("The step key %s already exists", key));

        Object o = null;
        Constructor<?>[] constructors = step.getDeclaredConstructors();

        try {
            o = Arrays.stream(constructors).findFirst().get().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        this.executeOrder.add(key);
        this.steps.put(key, (WorkflowStep<?>) o);
    }

    public <K> void addCallback(T key, Consumer<K> callback){
        if(FALSE.equals(this.steps.containsKey(key)))
            throw new DuplicatedKeyException(format("The step key %s doesn't exists for set callback", key));

        this.callbacks.put(key, (Consumer<Object>) callback);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        Optional
            .ofNullable(preStep)
            .ifPresent(p -> sb.append(format("Pre step: [%s]%n", p.getClass().getName())));

        sb.append(format("Steps:[%n"));

        steps.forEach((key, c) -> sb.append(format("%s:%s, %n", key, c.getClass().getName())));

        sb.append("]");
        return sb.toString();
    }

}
