package org.gruppe2.game;

public class Query<T> {
    private volatile Status status = Status.RUNNING;
    private T result = null;

    private enum Status {
        RUNNING, CANCELLED, DONE
    }

    public T get() {
        while (status == Status.RUNNING) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return status == Status.DONE ? result : null;
    }

    public T poll() {
        if (status == Status.DONE) {
            return result;
        }

        return null;
    }

    public void set(T result) {
        this.result = result;

        status = Status.DONE;
    }

    public void cancel() {
        status = Status.CANCELLED;
    }

    public boolean isRunning() {
        return status == Status.RUNNING;
    }

    public boolean isDone() {
        return status == Status.DONE;
    }

    public boolean isCancelled() {
        return status == Status.CANCELLED;
    }
}