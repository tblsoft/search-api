package de.tblsoft.search.pipeline;

import de.tblsoft.search.mock.Mockfactory;
import de.tblsoft.search.pipeline.filter.solr.MockSolrClient;
import de.tblsoft.search.pipeline.filter.solr.SolrFilterBuilder;
import de.tblsoft.search.response.Document;
import de.tblsoft.search.response.Facet;
import de.tblsoft.search.response.FacetValue;
import de.tblsoft.search.response.SearchResult;
import de.tblsoft.search.test.AbstractPipelineTest;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tbl on 07.11.17.
 */
public class SolrPipelineTest extends AbstractPipelineTest {


    @Test
    public void testSolrPipeline() throws Exception {
        MockSolrClient mockSolrClient = Mockfactory.createSolrClient("http://localhost:8983/solr/gettingstarted");

        Pipeline pipeline = PipelineBuilder.create().
                pipeline("products").
                filter(SolrFilterBuilder.create().
                        solrClient(mockSolrClient).
                        param("q","${q}").
                        param("fq","cat:*").
                        param("sort","id asc").
                        param("rows","9").
                        param("facet","true").
                        param("facet.mincount","1").
                        param("facet.field","price").
                        param("facet.field","inStock").
                        param("facet.field","author").
                        param("facet.field","genre_s").
                        mapField("id","id").
                        mapField("id","productId").
                        mapField("author","author").
                        mapField("price","price").
                        mapField("inStock","stock").
                        mapField("genre_s","genre").
                        resultField("url","http://quasiris.de/shop/products/${id}").
                        mapFacet("genre_s", "genre").
                        mapFacetName("genre", "Genre").
                        mapFacet("author", "author").
                        mapFacetName("author", "Autor").
                        resultSetId("products").
                        build()).
                build();

        HttpServletRequest httpServletRequest = Mockfactory.createHttpServletRequest("http://localhost?q=*:*&foo=bar");
        PipelineExecuter pipelineExecuter = new PipelineExecuter(pipeline);
        PipelineContainer pipelineContainer = new PipelineContainer(httpServletRequest, null);
        pipelineContainer = pipelineExecuter.execute(pipelineContainer);

        SearchResult searchResult = pipelineContainer.getSearchResult("products");
        Assert.assertEquals(Long.valueOf(34), searchResult.getTotal());
        Assert.assertEquals(9,searchResult.getDocuments().size());

        Document document = searchResult.getDocuments().get(0);
        Assert.assertEquals(7, document.getFieldCount());
        Assert.assertEquals("6.99", document.getFieldValue("price"));
        Assert.assertEquals("Roger Zelazny", document.getFieldValue("author"));
        Assert.assertEquals("fantasy", document.getFieldValue("genre"));
        Assert.assertEquals("0380014300", document.getFieldValue("id"));
        Assert.assertEquals("0380014300", document.getFieldValue("productId"));
        Assert.assertEquals("http://quasiris.de/shop/products/0380014300", document.getFieldValue("url"));
        Assert.assertEquals("true", document.getFieldValue("stock"));

        Facet facet = searchResult.getFacetById("author");
        Assert.assertEquals("Autor", facet.getName());
        Assert.assertEquals("author", facet.getId());
        Assert.assertEquals(Long.valueOf(22), facet.getCount());
        Assert.assertEquals(Long.valueOf(32), facet.getResultCount());

        FacetValue facetValue = facet.getValues().get(0);
        Assert.assertEquals("george", facetValue.getValue());
        Assert.assertEquals(Long.valueOf(3), facetValue.getCount());
        Assert.assertEquals("author=george", facetValue.getFilter());

    }


}
