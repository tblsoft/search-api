package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.response.SearchResult;
import de.tblsoft.search.solr.SolrClientWrapper;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.*;

/**
 * Created by tbl on 11.11.17.
 */
public class SolrFilter extends AbstractFilter {


    private Map<String, List<String>> parameters = new HashMap<>();

    private String solrBaseUrl;

    private String resultSetId;

    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) throws Exception {

        SolrQuery solrQuery = new SolrQuery();

        Map<String, String> replaceMap = new HashMap<>();
        Enumeration<String> parameterName = pipelineContainer.getRequest().getParameterNames();
        while(parameterName.hasMoreElements()) {
            String name = parameterName.nextElement();
            replaceMap.put(name, pipelineContainer.getRequest().getParameter(name));
        }


        StrSubstitutor strSubstitutor = new StrSubstitutor(replaceMap);

        for(Map.Entry<String, List<String>> parameter: parameters.entrySet()) {
            for(String value: parameter.getValue()) {
                String replacedValue = strSubstitutor.replace(value);
                solrQuery.add(parameter.getKey(),replacedValue);
            }
        }

        QueryResponse solrResponse = SolrClientWrapper.execute(solrQuery, solrBaseUrl);
        Solr2SearchResultTransformer transformer = new Solr2SearchResultTransformer();


        SearchResult searchResult = transformer.transform(solrResponse);
        searchResult.setTime(getCurrentTime());
        searchResult.setStatusCode(200);
        searchResult.setStatusMessage("OK");

        searchResult.debug(SolrClientWrapper.query2url(solrBaseUrl, solrQuery));
        pipelineContainer.putSearchResponse(resultSetId, searchResult);

        return pipelineContainer;
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

    public void setSolrBaseUrl(String solrBaseUrl) {
        this.solrBaseUrl = solrBaseUrl;
    }

    public void setResultSetId(String resultSetId) {
        this.resultSetId = resultSetId;
    }
}
