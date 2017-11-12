package de.tblsoft.search.pipeline.filter;

/**
 * Created by tbl on 11.11.17.
 */
public class SolrDismaxFilterBuilder {

    private SolrDismaxFilter solrDismaxFilter;

    public static SolrDismaxFilterBuilder create() {
        SolrDismaxFilterBuilder builder = new SolrDismaxFilterBuilder();
        builder.solrDismaxFilter = new SolrDismaxFilter();

        return builder;
    }

    public SolrDismaxFilterBuilder qf(String qf) {
        solrDismaxFilter.setQf(qf);
        return this;
    }

    public SolrDismaxFilterBuilder param(String name, String value) {
        solrDismaxFilter.addParam(name,value);
        return this;
    }


    public SolrDismaxFilterBuilder pf(String pf) {
        solrDismaxFilter.setPf(pf);
        return this;
    }
    public SolrDismaxFilterBuilder baseUrl(String baseUrl) {
        solrDismaxFilter.setSolrBaseUrl(baseUrl);
        return this;
    }


    public SolrDismaxFilterBuilder resultSetId(String resultSetId) {
        solrDismaxFilter.setResultSetId(resultSetId);
        return this;
    }
    public SolrDismaxFilter build() {
        return solrDismaxFilter;
    }

    public SolrDismaxFilterBuilder mapField(String from, String to) {
        solrDismaxFilter.addFieldMapping(from, to);
        return this;
    }

    public SolrDismaxFilterBuilder mapFacet(String from, String to) {
        solrDismaxFilter.addFacetMapping(from, to);
        return this;
    }
    public SolrDismaxFilterBuilder mapFacetName(String from, String to) {
        solrDismaxFilter.addFacetNameMapping(from, to);
        return this;
    }
}
