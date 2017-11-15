package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.Pipeline;
import de.tblsoft.search.pipeline.PipelineCallable;
import de.tblsoft.search.pipeline.PipelineContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by tbl on 04.11.17.
 */
public class ParallelFilter extends AbstractFilter {


    private List<Pipeline> pipelines = new ArrayList<>();

    private String sourcePipelineId;

    private ExecutorService executorService;

    public ParallelFilter(String sourcePipelineId) {
        this.sourcePipelineId = sourcePipelineId;
    }

    public void addPipeline(Pipeline pipeline) {
        pipelines.add(pipeline);
    }

    @Override
    public void init() {
        if(pipelines.size() > 0) {
            executorService = Executors.newFixedThreadPool(pipelines.size());
        }
    }

    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) throws Exception {
        List<FutureTask<PipelineContainer>> futureTaskList = new ArrayList<>();

        for(Pipeline pipeline: pipelines) {
            futureTaskList.add(new FutureTask<>(new PipelineCallable(pipeline, pipelineContainer)));
        }


        for(FutureTask<PipelineContainer> futureTask :futureTaskList) {
            executorService.execute(futureTask);
        }


        List<PipelineContainer> results = new ArrayList<>();
        try {
            for(FutureTask<PipelineContainer> futureTask :futureTaskList) {
                PipelineContainer value = futureTask.get();
                results.add(value);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // TODO merge searchRequest object

        return pipelineContainer;
    }
}
