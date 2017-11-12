package de.tblsoft.search.pipeline.filter;

import com.google.common.base.Strings;
import de.tblsoft.search.response.Document;
import de.tblsoft.search.response.SearchResult;
import de.tblsoft.search.solr.SearchResultTransformerIF;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tblsoft on 12.11.17.
 */
public class Solr2SearchResultTransformer implements SearchResultTransformerIF {

    private Map<String, String> fieldMapping = new HashMap<>();

    public Solr2SearchResultTransformer() {


    }

    public SearchResult transform(QueryResponse queryResponse) {
        SearchResult result = new SearchResult();
        Iterator<SolrDocument> solrDocumentIterator = queryResponse.getResults().iterator();
        while(solrDocumentIterator.hasNext()) {
            SolrDocument solrDocument = solrDocumentIterator.next();
            Document document = transformDocument(solrDocument);
            result.getDocuments().add(document);
        }

        return result;
    }

    public Document transformDocument(SolrDocument solrDocument) {
        Document document = new Document();
        for(String fieldNames: solrDocument.getFieldNames()) {
            transformField(document, fieldNames,solrDocument.get(fieldNames));


        }
        return document;

    }

    public void transformField(Document document, String name, Object value) {
        String mappedName = fieldMapping.get(name);
        if(!Strings.isNullOrEmpty(mappedName)) {
            document.getDocument().put(mappedName, value);
        }
    }

    public void setFieldMapping(Map<String, String> fieldMapping) {
        this.fieldMapping = fieldMapping;
    }
}
