package de.tblsoft.search.pipeline.filter;

/**
 * Created by tbl on 11.11.17.
 */
public class SolrFilterBuilder {

    private SolrFilter solrFilter;

    public static SolrFilterBuilder create() {
        SolrFilterBuilder builder = new SolrFilterBuilder();
        builder.solrFilter = new SolrFilter();
        return builder;
    }


    public SolrFilterBuilder baseUrl(String baseUrl) {
        solrFilter.setSolrBaseUrl(baseUrl);
        return this;
    }


    public SolrFilterBuilder resultSetId(String resultSetId) {
        solrFilter.setResultSetId(resultSetId);
        return this;
    }
    public SolrFilter build() {
        return solrFilter;
    }

    public SolrFilterBuilder param(String name, String value) {
        solrFilter.addParam(name,value);
        return this;
    }

}
