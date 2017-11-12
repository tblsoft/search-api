package de.tblsoft.search.pipeline;

import java.util.concurrent.Callable;

/**
 * Created by tbl on 11.11.17.
 */
public class PipelineCallable implements Callable<PipelineContainer> {

    private Pipeline pipeline;

    private PipelineContainer pipelineContainer;

    public PipelineCallable(Pipeline pipeline, PipelineContainer pipelineContainer) {
        this.pipeline = pipeline;
        this.pipelineContainer = pipelineContainer;
    }

    @Override
    public PipelineContainer call() throws Exception {
        PipelineExecuter pipelineExecuter = new PipelineExecuter(pipeline);
        PipelineContainer processedPipelineContainer = pipelineExecuter.execute(pipelineContainer);
        return processedPipelineContainer;
    }
}
