package de.tblsoft.search.solr;

import de.tblsoft.search.query.SearchQuery;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created by tblsoft on 21.11.16.
 */
public interface QueryTransformerIF {


    public SolrQuery transform(SearchQuery query);
}
