package de.tblsoft.search.pipeline.filter.solr;

/**
 * Created by tbl on 11.11.17.
 */
public class SolrFilterBuilder {

    private SolrFilter solrFilter;

    private Solr2SearchResultMappingTransformer mappingTransformer;

    private SolrParameterQueryTransformer solrParameterQueryTransformer;

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
        if(mappingTransformer != null) {
            solrFilter.setSearchResultTransformer(mappingTransformer);
        }

        if(solrParameterQueryTransformer != null) {
            solrFilter.setQueryTransformer(solrParameterQueryTransformer);
        }
        return solrFilter;
    }

    public SolrFilterBuilder param(String name, String value) {
        getSolrParameterQueryTransformer().addParam(name,value);
        return this;
    }

    private Solr2SearchResultMappingTransformer getMappingTransformer() {
        if(mappingTransformer == null) {
            mappingTransformer = new Solr2SearchResultMappingTransformer();
        }
        return mappingTransformer;
    }

    private SolrParameterQueryTransformer getSolrParameterQueryTransformer() {
        if(solrParameterQueryTransformer == null) {
            solrParameterQueryTransformer = new SolrParameterQueryTransformer();
        }
        return solrParameterQueryTransformer;
    }

    public SolrFilterBuilder mapField(String from, String to) {
        getMappingTransformer().addFieldMapping(from, to);
        return this;
    }

    public SolrFilterBuilder mapFacet(String from, String to) {
        getMappingTransformer().addFacetMapping(from, to);
        return this;
    }
    public SolrFilterBuilder mapFacetName(String from, String to) {
        getMappingTransformer().addFacetNameMapping(from, to);
        return this;
    }


    public SolrFilterBuilder mapFilter(String from, String to) {
        return this;
    }

    public SolrFilterBuilder queryTransformer(QueryTransformerIF queryTransformer) {
        if(queryTransformer instanceof SolrParameterQueryTransformer) {
            this.solrParameterQueryTransformer = (SolrParameterQueryTransformer) queryTransformer;
        } else {
            throw new IllegalArgumentException("The query transformer " + queryTransformer.getClass().getName() + " is not supported.");
        }

        return this;
    }


}