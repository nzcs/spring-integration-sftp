package hu.erste.slacct.codetables.integration.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Executor {

    public static <R> List<R> execute(List<Callable<R>> callableFunctions) {
        ExecutorService executor = Executors.newFixedThreadPool(callableFunctions.size());

        try {
            List<R> result = new ArrayList<>();
            for (Future<R> future : executor.invokeAll(callableFunctions)) {
                result.add(future.get());
            }

            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);

        } finally {
            executor.shutdown();
        }
    }
}
