package de.tblsoft.search.pipeline;

import java.util.concurrent.*;

/**
 * Created by tbl on 26.12.17.
 */
public class PipelineExecuter {

    public static PipelineContainer execute(Pipeline pipeline, PipelineContainer pipelineContainer) {

        try {
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            FutureTask<PipelineContainer> futureTask = new FutureTask<>(new PipelineCallable(pipeline, pipelineContainer));
            executorService.execute(futureTask);
            executorService.shutdown();
            return futureTask.get(pipeline.getTimeout(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return pipelineContainer;
    }
}
