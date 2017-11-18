package de.tblsoft.search.pipeline.filter.solr;

import de.tblsoft.search.response.Document;
import de.tblsoft.search.response.Facet;
import de.tblsoft.search.response.FacetValue;
import de.tblsoft.search.response.SearchResult;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 * Created by tblsoft on 12.11.17.
 */
public class Solr2SearchResultTransformer extends Solr2SearchResultMappingTransformer implements SearchResultTransformerIF {


    public Solr2SearchResultTransformer() {


    }


    protected void mapFacets(QueryResponse queryResponse, SearchResult searchResult) {
        if(queryResponse.getFacetFields() == null) {
            return;
        }
        searchResult.setFacetCount(queryResponse.getFacetFields().size());
        for(FacetField facetField : queryResponse.getFacetFields()) {
            Facet facet = new Facet();
            facet.setId(facetField.getName());
            facet.setName(facetField.getName());

            facet.setCount(facetField.getValues().size());
            for(FacetField.Count count: facetField.getValues()) {
                FacetValue facetValue = new FacetValue(count.getName(), count.getCount());
                facet.getValues().add(facetValue);
            }
            searchResult.getFacets().add(facet);
        }
    }

    public void transformField(Document document, String name, Object value) {
        document.getDocument().put(name, value);
    }
}
