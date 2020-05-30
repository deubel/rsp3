package org.rspeer.commons;

import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Executor service utilities
 */
public class Executor {

    private static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(
            Runtime.getRuntime().availableProcessors() + 1);
    private static final Consumer<Throwable> DEFAULT_THROWABLE_CONSUMER = Throwable::printStackTrace;
    private static final int DEFAULT_RETRIES = 5;

    public static void execute(Runnable runnable) {
        EXECUTOR.execute(() -> tryExecute(runnable));
    }

    public static void executeWithRetry(Runnable runnable) {
        EXECUTOR.execute(() -> executeWithRetryInternal(runnable));
    }

    public static void executeWithRetry(Runnable runnable, int tries) {
        EXECUTOR.execute(() -> executeWithRetryInternal(runnable, tries));
    }

    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long delay, long interval, TimeUnit unit) {
        return EXECUTOR.scheduleAtFixedRate(() -> tryExecute(runnable), delay, interval, unit);
    }

    public static <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return EXECUTOR.schedule(() -> tryExecuteCallable(callable), delay, unit);
    }

    public static ScheduledFuture<?> schedule(Runnable runnable, long delay, TimeUnit unit) {
        return EXECUTOR.schedule(() -> tryExecute(runnable), delay, unit);
    }

    public static void shutdown() {
        EXECUTOR.shutdown();
        try {
            if (!EXECUTOR.awaitTermination(10, TimeUnit.SECONDS)) {
                EXECUTOR.shutdownNow();
                if (!EXECUTOR.awaitTermination(10, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate.");
                }
            }
        } catch (InterruptedException ignored) {
            EXECUTOR.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private static void tryExecute(Runnable runnable, Consumer<Throwable> error) {
        try {
            runnable.run();
        } catch (Throwable e) {
            error.accept(e);
        }
    }

    private static void executeWithRetryInternal(Runnable runnable, int tries) {
        if (tries == 0) {
            return;
        }

        try {
            runnable.run();
        } catch (Throwable e) {
            DEFAULT_THROWABLE_CONSUMER.accept(e);
            executeWithRetryInternal(runnable, tries - 1);
        }
    }

    private static void executeWithRetryInternal(Runnable runnable) {
        executeWithRetryInternal(runnable, DEFAULT_RETRIES);
    }

    private static <V> V tryExecuteCallable(Callable<V> callable) {
        try {
            return callable.call();
        } catch (Throwable e) {
            DEFAULT_THROWABLE_CONSUMER.accept(e);
            return null;
        }
    }

    private static void tryExecute(Runnable runnable) {
        tryExecute(runnable, DEFAULT_THROWABLE_CONSUMER);
    }
}
