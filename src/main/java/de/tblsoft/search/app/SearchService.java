package de.tblsoft.search.app;

import de.tblsoft.search.pipeline.*;
import de.tblsoft.search.pipeline.filter.QSQLRequestFilter;
import de.tblsoft.search.pipeline.filter.SolrDismaxFilterBuilder;
import de.tblsoft.search.response.SearchResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tblsoft on 11.11.16.
 */
@RestController
public class SearchService {


    @RequestMapping("/search")
    public SearchResponse search(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Pipeline pipeline = PipelineBuilder.create().
            pipeline("pipelineId").
                filter(new QSQLRequestFilter()).
                parallel().
                    pipeline("foo").
                        filter(SolrDismaxFilterBuilder.create().
                            baseUrl("http://localhost:8983/solr/gettingstarted").
                            qf("foo^5 bar^6").
                            pf("aadfa adfa afdsf adf").
                            resultSetId("channel1").
                            param("facet", "true").
                            param("facet.field", "cat").
                            mapField("cat", "category").
                            mapField("author", "author").
                            mapField("id", "id").
                            mapFacet("cat", "category").
                            mapFacetName("category", "Kategorie").
                            build()).
                    pipeline("bar").
                        filter(SolrDismaxFilterBuilder.create().
                            baseUrl("http://localhost:8983/solr/gettingstarted").
                            qf("foo^5 bar^6").
                            pf("aadfa adfa afdsf adf").
                            mapField("cat", "category").
                            resultSetId("channel2").
                            build()).
                sequential().
                build();



        PipelineContainer pipelineContainer = new PipelineContainer(request, response);
        PipelineExecuter executer = new PipelineExecuter(pipeline);
        pipelineContainer = executer.execute(pipelineContainer);

        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setResult(pipelineContainer.getSearchResults());
        searchResponse.setTime(pipelineContainer.currentTime());

        return searchResponse;
    }
}
