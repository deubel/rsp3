package org.rspeer.game.script;

public abstract class Task implements Comparable<Task> {

    /**
     * @return {@code true} if this {@code Task} should run, {@code false} otherwise
     */
    public abstract boolean validate();

    /**
     * The code to execute if this {@code Task} validates
     */
    public abstract int execute();

    /**
     * The default behaviour of this method is to return {@code true}
     *
     * @return {@code true} if the task loop should break after executing this {@code Task},
     * {@code false} if it should continue and execute other tasks that validate in the same loop
     */
    public boolean isBlocking() {
        return true;
    }

    /**
     * @return The priority of this Task. A lower priority denotes prioritized execution.
     * The default return of this method is {@link Integer#MAX_VALUE}
     */
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public final int compareTo(Task o) {
        return Integer.compare(o.getPriority(), getPriority());
    }
}
