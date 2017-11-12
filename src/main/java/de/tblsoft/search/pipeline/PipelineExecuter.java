package de.tblsoft.search.pipeline;

import de.tblsoft.search.pipeline.filter.Filter;

/**
 * Created by tbl on 11.11.17.
 */
public class PipelineExecuter {

    private Pipeline pipeline;

    public PipelineExecuter(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public PipelineContainer execute(PipelineContainer pipelineContainer) {

        pipelineContainer.start();

        for(Filter filter : pipeline.getFilterList()) {
            filter.init();
        }


        for(Filter filter : pipeline.getFilterList()) {
            long start = System.currentTimeMillis();
            filter.setStartTime(start);
            pipelineContainer = filter.filter(pipelineContainer);
            long end = System.currentTimeMillis() - start;
        }



        for(Filter filter : pipeline.getFilterList()) {
            filter.end();
        }

        return pipelineContainer;
    }

}
