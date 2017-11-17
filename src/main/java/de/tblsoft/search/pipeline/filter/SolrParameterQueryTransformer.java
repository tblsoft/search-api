package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.solr.QueryTransformerIF;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.*;

/**
 * Created by tbl on 18.11.17.
 */
public class SolrParameterQueryTransformer implements QueryTransformerIF {

    private Map<String, List<String>> parameters;

    @Override
    public SolrQuery transform(PipelineContainer pipelineContainer) {
        SolrQuery solrQuery = new SolrQuery();

        Map<String, String> replaceMap = new HashMap<>();

        synchronized (pipelineContainer.getRequest()) {
            Enumeration<String> parameterName = pipelineContainer.getRequest().getParameterNames();
            while (parameterName.hasMoreElements()) {
                String name = parameterName.nextElement();
                replaceMap.put(name, pipelineContainer.getRequest().getParameter(name));
            }
        }


        StrSubstitutor strSubstitutor = new StrSubstitutor(replaceMap);

        for(Map.Entry<String, List<String>> parameter: parameters.entrySet()) {
            for(String value: parameter.getValue()) {
                String replacedValue = strSubstitutor.replace(value);
                solrQuery.add(parameter.getKey(),replacedValue);
            }
        }
        return solrQuery;
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
}