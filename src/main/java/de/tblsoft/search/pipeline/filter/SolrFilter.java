package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.response.SearchResult;
import de.tblsoft.search.solr.SolrClientWrapper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;

/**
 * Created by tbl on 11.11.17.
 */
public class SolrFilter extends AbstractFilter {

    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) {


        SolrQuery solrQuery = new SolrQuery("*");
        String baseUrl = "http://localhost:8983/solr/gettingstarted";


        try {
            QueryResponse response = SolrClientWrapper.execute(solrQuery, baseUrl);
            SearchResult searchResponse = new SearchResult();
            searchResponse.setRawResponse(response);
            pipelineContainer.putSearchResponse(getPipelineId(), searchResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        return pipelineContainer;
    }
}
