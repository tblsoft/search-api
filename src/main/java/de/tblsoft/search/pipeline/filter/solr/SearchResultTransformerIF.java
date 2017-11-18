package de.tblsoft.search.pipeline.filter.solr;

import de.tblsoft.search.response.Document;
import de.tblsoft.search.response.SearchResult;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

/**
 * Created by tblsoft on 21.11.16.
 */
public interface SearchResultTransformerIF {

    public SearchResult transform(QueryResponse queryResponse);

    public Document transformDocument(SolrDocument solrDocument);

    public void transformField(Document document, String name, Object value);
}
