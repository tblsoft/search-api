package de.tblsoft.search.solr;

import com.google.common.base.Optional;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tblsoft on 15.11.16.
 */
public class SolrClientWrapper {

    private static Logger LOG = LoggerFactory.getLogger(SolrClientWrapper.class);


    public static QueryResponse execute(SolrQuery query, String baseUrl) throws IOException, SolrServerException {

        SolrClient solrClient = new HttpSolrClient.Builder(baseUrl).build();

        QueryResponse response = solrClient.query(query);
        LOG.info("solr query: " + query2url(baseUrl, query) + " status: " + response.getStatus());
        return response;
    }




    static String query2url(String solrBase, SolrQuery solrQuery) {
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