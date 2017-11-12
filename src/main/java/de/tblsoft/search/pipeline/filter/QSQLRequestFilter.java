package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.query.SearchQuery;
import de.tblsoft.search.query.parser.SaqlParser;

/**
 * Created by tbl on 12.11.17.
 */
public class QSQLRequestFilter extends AbstractFilter {


    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) {
        SaqlParser saqlParser = new SaqlParser(pipelineContainer.getRequest().getParameterMap());
        SearchQuery searchQuery = saqlParser.getQuery();
        pipelineContainer.setSearchQuery(searchQuery);

        return pipelineContainer;
    }
}
