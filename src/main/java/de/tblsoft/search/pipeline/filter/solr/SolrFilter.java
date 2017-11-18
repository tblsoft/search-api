package de.tblsoft.search.pipeline.filter.solr;

import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.pipeline.filter.AbstractFilter;
import de.tblsoft.search.response.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 * Created by tbl on 11.11.17.
 */
public class SolrFilter extends AbstractFilter {


    private String solrBaseUrl;

    private String resultSetId;

    private SearchResultTransformerIF searchResultTransformer = new Solr2SearchResultTransformer();
    private QueryTransformerIF queryTransformer;

    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) throws Exception {

        SolrQuery solrQuery = queryTransformer.transform(pipelineContainer);

        QueryResponse solrResponse = SolrClientWrapper.execute(solrQuery, solrBaseUrl);


        SearchResult searchResult = searchResultTransformer.transform(solrResponse);
        searchResult.setTime(getCurrentTime());
        searchResult.setStatusCode(200);
        searchResult.setStatusMessage("OK");

        searchResult.debug(SolrClientWrapper.query2url(solrBaseUrl, solrQuery));
        pipelineContainer.putSearchResponse(resultSetId, searchResult);

        return pipelineContainer;
    }

    public void setSolrBaseUrl(String solrBaseUrl) {
        this.solrBaseUrl = solrBaseUrl;
    }

    public void setResultSetId(String resultSetId) {
        this.resultSetId = resultSetId;
    }

    public void setSearchResultTransformer(SearchResultTransformerIF searchResultTransformer) {
        this.searchResultTransformer = searchResultTransformer;
    }

    public void setQueryTransformer(QueryTransformerIF queryTransformer) {
        this.queryTransformer = queryTransformer;
    }
}
