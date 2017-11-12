package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.pipeline.SearchRequest;

/**
 * Created by tbl on 04.11.17.
 */
public class MyFilter extends AbstractFilter {

    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) {
        SearchRequest searchRequest = pipelineContainer.getSearchRequests();

        String q = searchRequest.getQ() + "-- foo";
        searchRequest.setQ(q);

        return pipelineContainer;
    }
}
