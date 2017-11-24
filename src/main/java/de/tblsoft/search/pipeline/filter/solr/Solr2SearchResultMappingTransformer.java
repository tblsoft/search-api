package de.tblsoft.search.pipeline.filter.solr;

import com.google.common.base.Strings;
import de.tblsoft.search.response.Document;
import de.tblsoft.search.response.Facet;
import de.tblsoft.search.response.FacetValue;
import de.tblsoft.search.response.SearchResult;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import java.util.*;

/**
 * Created by tblsoft on 11.11.17.
 */
public class Solr2SearchResultMappingTransformer implements SearchResultTransformerIF {

    private Map<String, List<String>> fieldMapping = new HashMap<>();
    private Map<String, String> facetMapping = new HashMap<>();
    private Map<String, String> facetNameMapping = new HashMap<>();

    public Solr2SearchResultMappingTransformer() {


    }

    public SearchResult transform(QueryResponse queryResponse) {
        SearchResult result = new SearchResult();
        result.setTotal(queryResponse.getResults().getNumFound());
        result.setMaxScore(queryResponse.getResults().getMaxScore());
        Iterator<SolrDocument> solrDocumentIterator = queryResponse.getResults().iterator();
        while(solrDocumentIterator.hasNext()) {
            SolrDocument solrDocument = solrDocumentIterator.next();
            Document document = transformDocument(solrDocument);
            result.getDocuments().add(document);
        }

        mapFacets(queryResponse, result);


        return result;
    }

    protected void mapFacets(QueryResponse queryResponse, SearchResult searchResult) {
        if(queryResponse.getFacetFields() == null) {
            return;
        }
        searchResult.setFacetCount(queryResponse.getFacetFields().size());
        for(FacetField facetField : queryResponse.getFacetFields()) {
            Facet facet = new Facet();

            String id = facetMapping.get(facetField.getName());
            facet.setId(id);

            String name = facetNameMapping.get(id);
            facet.setName(name);

            facet.setCount(Long.valueOf(facetField.getValues().size()));
            Long facetReseultCount = 0L;
            for(FacetField.Count count: facetField.getValues()) {
                FacetValue facetValue = new FacetValue(count.getName(), count.getCount());
                facetReseultCount = facetReseultCount + facetValue.getCount();

                // TODO encode the value
                facetValue.setFilter(id + "=" + count.getName());
                facet.getValues().add(facetValue);
            }
            facet.setResultCount(facetReseultCount);
            searchResult.getFacets().add(facet);
        }
    }

    public Document transformDocument(SolrDocument solrDocument) {
        Document document = new Document();
        for(String fieldNames: solrDocument.getFieldNames()) {
            transformField(document, fieldNames,solrDocument.get(fieldNames));


        }
        return document;

    }

    public void transformField(Document document, String name, Object value) {
        List<String> mappedNames = fieldMapping.get(name);
        if(mappedNames == null) {
            return;
        }
        for(String mappedName : mappedNames) {
            if (!Strings.isNullOrEmpty(mappedName)) {
                document.getDocument().put(mappedName, value);
            }
        }
    }

    public void setFieldMapping(Map<String, List<String>> fieldMapping) {
        this.fieldMapping = fieldMapping;
    }

    public void setFacetMapping(Map<String, String> facetMapping) {
        this.facetMapping = facetMapping;
    }

    public void setFacetNameMapping(Map<String, String> facetNameMapping) {
        this.facetNameMapping = facetNameMapping;
    }

    public void addFieldMapping(String from, String to) {
        List<String> mapping = fieldMapping.get(from);
        if(mapping == null) {
            mapping = new ArrayList<>();
        }
        mapping.add(to);

        fieldMapping.put(from, mapping);
    }
    public void addFacetMapping(String from, String to) {
        facetMapping.put(from, to);
    }

    public void addFacetNameMapping(String from, String to) {
        facetNameMapping.put(from, to);
    }
}
