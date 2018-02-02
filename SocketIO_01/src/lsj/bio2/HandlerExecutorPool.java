package lsj.bio2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author TF016519
 * @description:
 * @date 2018-1-24 12:09
 * @version:1.0.0
 */
public class HandlerExecutorPool {
    private ExecutorService executor;

    public HandlerExecutorPool(int maxPoolSize, int queueSize) {
        this.executor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                maxPoolSize,
                120L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize)
        );
    }
    public void execute (Runnable task){
        this.executor.execute(task);
    }
}
