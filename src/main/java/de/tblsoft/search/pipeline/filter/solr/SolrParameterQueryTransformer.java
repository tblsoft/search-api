package de.tblsoft.search.pipeline.filter.solr;

import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.pipeline.filter.web.RequestParser;
import de.tblsoft.search.util.PrintUtil;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.*;

/**
 * Created by tbl on 18.11.17.
 */
public class SolrParameterQueryTransformer implements QueryTransformerIF {

    private Map<String, List<String>> parameters;
    private Set<String> fieldList = new HashSet<>();

    @Override
    public SolrQuery transform(PipelineContainer pipelineContainer) {
        SolrQuery solrQuery = new SolrQuery();

        Map<String, String> replaceMap = RequestParser.getRequestParameter(pipelineContainer);
        StrSubstitutor strSubstitutor = new StrSubstitutor(replaceMap);

        for(Map.Entry<String, List<String>> parameter: parameters.entrySet()) {
            for(String value: parameter.getValue()) {
                String replacedValue = strSubstitutor.replace(value);
                solrQuery.add(parameter.getKey(),replacedValue);
            }
        }

        for(String fieldName:fieldList) {
            solrQuery.addField(fieldName);
        }

        return solrQuery;
    }

    public StringBuilder print(String indent) {
        StringBuilder printer = new StringBuilder();
        PrintUtil.printMap(printer,indent, "parameters", parameters);
        PrintUtil.printKeyValue(printer,indent, "fieldList", fieldList);
        return printer;
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

    public void addFieldListValue(String fieldName) {
        fieldList.add(fieldName);


    }
}
