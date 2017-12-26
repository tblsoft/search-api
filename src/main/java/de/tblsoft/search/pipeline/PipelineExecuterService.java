package de.tblsoft.search.pipeline;

import de.tblsoft.search.pipeline.filter.Filter;

/**
 * Created by tbl on 11.11.17.
 */
public class PipelineExecuterService {

    private Pipeline pipeline;

    public PipelineExecuterService(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public PipelineContainer execute(PipelineContainer pipelineContainer) throws PipelineContainerException {

        pipelineContainer.start();

        for(Filter filter : pipeline.getFilterList()) {
            filter.init();
        }


        for(Filter filter : pipeline.getFilterList()) {
            failOnError(pipelineContainer);
            try {
                filter.start();
                if(filter.isActive()) {
                    pipelineContainer = filter.filter(pipelineContainer);
                }
            } catch (Exception e) {
                pipelineContainer.error("error in filter: " + filter.getId());
                pipelineContainer.error(e);
                filter.onError(pipelineContainer, e);
            }
        }

        failOnError(pipelineContainer);

        for(Filter filter : pipeline.getFilterList()) {
            filter.end();
        }

        return pipelineContainer;
    }

    public static void failOnError(PipelineContainer pipelineContainer) throws PipelineContainerException {
        if(pipelineContainer.isFailOnError() && !pipelineContainer.isSuccess()) {
            throw new PipelineContainerException(pipelineContainer.getMessage());
        }
    }

}
