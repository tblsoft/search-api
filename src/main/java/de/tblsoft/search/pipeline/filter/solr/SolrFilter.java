package de.tblsoft.search.pipeline.filter.solr;

import com.google.common.base.Optional;
import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.pipeline.filter.AbstractFilter;
import de.tblsoft.search.response.SearchResult;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by tbl on 11.11.17.
 */
public class SolrFilter extends AbstractFilter {

    private static Logger LOG = LoggerFactory.getLogger(SolrFilter.class);

    private String solrBaseUrl;

    private String resultSetId;

    private SearchResultTransformerIF searchResultTransformer = new Solr2SearchResultTransformer();
    private QueryTransformerIF queryTransformer;

    private SolrClient solrClient;


    @Override
    public void init() {
        super.init();
        if(solrClient == null) {
            solrClient = new HttpSolrClient.Builder(solrBaseUrl).build();
        }
    }

    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) throws Exception {

        SolrQuery solrQuery = queryTransformer.transform(pipelineContainer);
        LOG.info("solr query " + solrQuery.toQueryString());


        QueryResponse solrResponse = solrClient.query(solrQuery);

        SearchResult searchResult = searchResultTransformer.transform(solrResponse);
        searchResult.setTime(getCurrentTime());
        searchResult.setStatusCode(200);
        searchResult.setStatusMessage("OK");

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

    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public static String query2url(SolrQuery solrQuery) {
        return query2url("", solrQuery);
    }

    public static String query2url(String solrBase, SolrQuery solrQuery) {
        Map<String,String[]> parameters = solrQuery.getMap();
        String requestHandler = Optional.fromNullable(solrQuery.getRequestHandler()).or("select");
        StringBuilder url = new StringBuilder(solrBase);
        url.append("/").append(requestHandler).append("?");
        boolean first = true;
        for(Map.Entry<String, String[]> entry : parameters.entrySet()) {
            String key = entry.getKey();
            for(String value: entry.getValue()) {
                if(first) {
                    first = false;
                } else {
                    url.append("&");
                }
                url.append(key).append("=").append(value);
            }

        }
        return url.toString();
    }
}
