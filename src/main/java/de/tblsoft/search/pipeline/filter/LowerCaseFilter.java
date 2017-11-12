package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.query.SearchQuery;

/**
 * Created by tbl on 04.11.17.
 */
public class LowerCaseFilter extends AbstractFilter {

    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) {
        SearchQuery searchRequest = pipelineContainer.getSearchQuery();
        String q = searchRequest.getQ();
        searchRequest.setQ(q.toLowerCase());
        return pipelineContainer;
    }
}
