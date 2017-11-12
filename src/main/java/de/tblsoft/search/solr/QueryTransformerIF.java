package de.tblsoft.search.solr;

import de.tblsoft.search.query.Query;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created by tblsoft on 21.11.16.
 */
public interface QueryTransformerIF {


    public SolrQuery transform(Query query);
}
