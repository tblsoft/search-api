package de.tblsoft.search.pipeline;

import de.tblsoft.search.pipeline.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tbl on 11.11.17.
 */
public class PipelineExecuterService {

    private static Logger LOG = LoggerFactory.getLogger(PipelineExecuterService.class);

    private Pipeline pipeline;

    public PipelineExecuterService(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public PipelineContainer execute(PipelineContainer pipelineContainer) throws PipelineContainerException {

        pipelineContainer.start();
        long start = System.currentTimeMillis();

        for(Filter filter : pipeline.getFilterList()) {
            filter.init();
        }


        for(Filter filter : pipeline.getFilterList()) {
            failOnError(pipelineContainer);
            try {
                filter.start();
                if(filter.isActive() && pipelineContainer.isFilterActive(filter.getId())) {
                    pipelineContainer = filter.filter(pipelineContainer);
                } else {
                    LOG.debug("The filter: " + filter.getId() + " is not active.");
                }
                LOG.debug("The filter: " + filter.getId() + " took: " + filter.getCurrentTime() + " ms.");
            } catch (Exception e) {
                filter.onError(pipelineContainer, e);
            }
        }

        failOnError(pipelineContainer);

        for(Filter filter : pipeline.getFilterList()) {
            filter.end();
        }
        long took = System.currentTimeMillis() - start;
        LOG.debug("The pipeline: " + pipeline.getId() + " took: " + took + " ms.");
        return pipelineContainer;
    }

    public static void failOnError(PipelineContainer pipelineContainer) throws PipelineContainerException {
        if(pipelineContainer.isFailOnError() && !pipelineContainer.isSuccess()) {
            throw new PipelineContainerException(pipelineContainer.getMessage());
        }
    }

}
