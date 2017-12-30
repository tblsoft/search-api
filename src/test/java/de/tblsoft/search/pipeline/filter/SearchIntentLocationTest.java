package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.mock.Mockfactory;
import de.tblsoft.search.pipeline.*;
import de.tblsoft.search.pipeline.filter.elastic.ElasticFilter;
import de.tblsoft.search.pipeline.filter.elastic.MockElasticClient;
import de.tblsoft.search.pipeline.filter.qsql.QSQLRequestFilter;
import de.tblsoft.search.response.Document;
import de.tblsoft.search.response.SearchResult;
import de.tblsoft.search.test.AbstractPipelineTest;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tbl on 07.11.17.
 */
public class SearchIntentLocationTest extends AbstractPipelineTest {


    @Test
    public void testSearchIntentLocation() throws Exception {
        MockElasticClient mockElasticClient = new MockElasticClient();
        //mockElasticClient.setRecord(true);
        QSQLRequestFilter qsqlRequestFilter = new QSQLRequestFilter();

        ElasticFilter elasticFilter = new ElasticFilter();
        elasticFilter.setResultSetId("locationLookup");
        elasticFilter.setElasticBaseUrl("http://localhost:9214/quantum");
        elasticFilter.setProfile("classpath://de/tblsoft/search/elastic/profiles/location.json");
        elasticFilter.setElasticClient(mockElasticClient);

        SearchIntentLocationFilter searchIntentLocationFilter = new SearchIntentLocationFilter();

        Pipeline pipeline = PipelineBuilder.create().
                pipeline("locationLookup").
                timeout(1000L).
                    filter(qsqlRequestFilter).
                    filter(elasticFilter).
                    filter(searchIntentLocationFilter).
                build();

        Assert.assertNotNull(pipeline.print(""));


        HttpServletRequest httpServletRequest = Mockfactory.createHttpServletRequest("http://localhost/foo/bar?q=Dr.%20Thomas%20M%C3%BCller%20Darmstadt");

        PipelineContainer pipelineContainer = PipelineExecuter.create().
                pipeline(pipeline).
                httpRequest(httpServletRequest).
                execute();

        if(!pipelineContainer.isSuccess()) {
            Assert.fail();
        }

        SearchResult searchResult = pipelineContainer.getSearchResult("search-intent");
        searchResult.setTime(pipelineContainer.currentTime());

        Assert.assertEquals(Long.valueOf(1), searchResult.getTotal());
        Assert.assertEquals(1,searchResult.getDocuments().size());

        Document document = searchResult.getDocuments().get(0);
        Assert.assertEquals(2, document.getFieldCount());
        Assert.assertEquals("Darmstadt", document.getFieldValue("location"));
        Assert.assertEquals("Dr. Thomas Müller", document.getFieldValue("other"));

    }
}
