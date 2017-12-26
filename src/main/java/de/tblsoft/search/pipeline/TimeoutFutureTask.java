package de.tblsoft.search.pipeline;

import java.util.concurrent.*;

/**
 * Created by tbl on 26.12.17.
 */
public class TimeoutFutureTask<T> extends FutureTask<T> {

    private long timeout = 1000;


    public TimeoutFutureTask(Callable<T> callable, long timeout) {
        super(callable);
        this.timeout = timeout;
    }

    public TimeoutFutureTask(Runnable runnable, T result, long timeout) {
        super(runnable, result);
        this.timeout = timeout;
    }

    public T getWithTimeout() throws InterruptedException, ExecutionException, TimeoutException {
        return super.get(timeout, TimeUnit.MILLISECONDS);
    }
}
