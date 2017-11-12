package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.pipeline.SearchRequest;

/**
 * Created by tbl on 11.11.17.
 */
public class UpperCaseFilter extends AbstractFilter {

    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) {
        SearchRequest searchRequest = pipelineContainer.getSearchRequests();
        String q = searchRequest.getQ();
        searchRequest.setQ(q.toUpperCase());
        return pipelineContainer;
    }
}
