package de.tblsoft.search.pipeline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.*;

/**
 * Created by tbl on 26.12.17.
 */
public class PipelineExecuter {

    private Pipeline pipeline;

    private PipelineContainer pipelineContainer;

    public static PipelineExecuter create() {
        return new PipelineExecuter();
    }

    public PipelineExecuter httpRequest(HttpServletRequest httpServletRequest) {
        getPipelineContainer().setRequest(httpServletRequest);
        return this;
    }

    public PipelineExecuter httpResponse(HttpServletResponse httpServletResponse) {
        getPipelineContainer().setResponse(httpServletResponse);
        return this;
    }

    public PipelineExecuter debug(boolean debug) {
        getPipelineContainer().setDebug(debug);
        return this;
    }

    public PipelineExecuter enableDebug() {
        getPipelineContainer().enableDebug();
        return this;
    }

    public PipelineExecuter pipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }



    public PipelineContainer execute() throws PipelineContainerException {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            FutureTask<PipelineContainer> futureTask = new FutureTask<>(new PipelineCallable(pipeline, pipelineContainer));
            executorService.execute(futureTask);
            executorService.shutdown();
            pipelineContainer = futureTask.get(pipeline.getTimeout(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            pipelineContainer.error(e);
            PipelineExecuterService.failOnError(pipelineContainer);
        }
        return pipelineContainer;
    }

    public PipelineContainer getPipelineContainer() {
        if(this.pipelineContainer == null) {
            this.pipelineContainer = new PipelineContainer();
        }
        return pipelineContainer;
    }
}
