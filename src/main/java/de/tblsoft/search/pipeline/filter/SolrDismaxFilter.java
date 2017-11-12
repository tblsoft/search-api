package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.pipeline.SearchRequest;
import de.tblsoft.search.response.SearchResult;
import de.tblsoft.search.solr.SolrClientWrapper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tbl on 11.11.17.
 */
public class SolrDismaxFilter extends AbstractFilter {

    private String pf;
    private String qf;
    private String solrBaseUrl;

    private String resultSetId;



    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) {
        SearchRequest searchRequest = pipelineContainer.getSearchRequests();
        SolrQuery solrQuery = new SolrQuery(searchRequest.getQ());
        try {
            QueryResponse solrResponse = SolrClientWrapper.execute(solrQuery, solrBaseUrl);

            Solr2SearchResultTransformer transformer = new Solr2SearchResultTransformer();
            Map<String, String> mapping = new HashMap<>();
            mapping.put("id", "id");
            mapping.put("cat", "category");

            transformer.setFieldMapping(mapping);

            SearchResult searchResult = transformer.transform(solrResponse);
            searchResult.setTime(getCurrentTime());
            //searchResult.setRawResponse(solrResponse);
            pipelineContainer.putSearchResponse(resultSetId, searchResult);

        } catch (Exception e) {
            SearchResult searchResult = new SearchResult();
            searchResult.setStatusMessage(e.getMessage());
            searchResult.setStatusCode(500);
            searchResult.setTime(getCurrentTime());
            pipelineContainer.putSearchResponse(resultSetId, searchResult);
        }


        return pipelineContainer;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getQf() {
        return qf;
    }

    public void setQf(String qf) {
        this.qf = qf;
    }

    public String getSolrBaseUrl() {
        return solrBaseUrl;
    }

    public void setSolrBaseUrl(String solrBaseUrl) {
        this.solrBaseUrl = solrBaseUrl;
    }

    public String getResultSetId() {
        return resultSetId;
    }

    public void setResultSetId(String resultSetId) {
        this.resultSetId = resultSetId;
    }
}
