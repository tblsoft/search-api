package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.query.SearchQuery;
import de.tblsoft.search.response.SearchResult;
import de.tblsoft.search.solr.SolrClientWrapper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tbl on 11.11.17.
 */
public class SolrDismaxFilter extends AbstractFilter {

    private String pf;
    private String qf;
    private String solrBaseUrl;

    private String resultSetId;

    private Map<String, List<String>> parameters = new HashMap<>();

    private Map<String, String> fieldMapping = new HashMap<>();
    private Map<String, String> facetMapping = new HashMap<>();
    private Map<String, String> facetNameMapping = new HashMap<>();



    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) {
        SearchQuery searchQuery = pipelineContainer.getSearchQuery();
        SolrQuery solrQuery = new SolrQuery(searchQuery.getQ());


        for(Map.Entry<String, List<String>> parameter: parameters.entrySet()) {
            String[] valuesArray = parameter.getValue().toArray(new String[parameter.getValue().size()]);
            solrQuery.add(parameter.getKey(),valuesArray);
        }


        try {
            QueryResponse solrResponse = SolrClientWrapper.execute(solrQuery, solrBaseUrl);

            Solr2SearchResultTransformer transformer = new Solr2SearchResultTransformer();

            transformer.setFieldMapping(fieldMapping);
            transformer.setFacetMapping(facetMapping);
            transformer.setFacetNameMapping(facetNameMapping);

            SearchResult searchResult = transformer.transform(solrResponse);
            searchResult.setTime(getCurrentTime());
            searchResult.setStatusCode(200);
            searchResult.setStatusMessage("OK");

            searchResult.debug(SolrClientWrapper.query2url(solrBaseUrl, solrQuery));
            //searchResult.setRawResponse(solrResponse);
            pipelineContainer.putSearchResponse(resultSetId, searchResult);

        } catch (Exception e) {
            e.printStackTrace();
            SearchResult searchResult = new SearchResult();
            searchResult.setStatusMessage(e.getMessage());
            searchResult.setStatusCode(500);
            searchResult.debug(SolrClientWrapper.query2url(solrBaseUrl, solrQuery));
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

    public void addParam(String name, String value){
        if(parameters == null) {
            parameters = new HashMap<>();
        }
        List<String> values = parameters.get(name);
        if(values == null) {
            values = new ArrayList<>();
        }
        values.add(value);
        parameters.put(name, values);
    }

    public void addFieldMapping(String from, String to) {
        fieldMapping.put(from, to);
    }
    public void addFacetMapping(String from, String to) {
        facetMapping.put(from, to);
    }

    public void addFacetNameMapping(String from, String to) {
        facetNameMapping.put(from, to);
    }
}
