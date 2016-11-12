package de.tblsoft.search.app;

import de.tblsoft.search.response.DidYouMeanResult;
import de.tblsoft.search.response.Document;
import de.tblsoft.search.response.SearchResponse;
import de.tblsoft.search.response.SearchResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tblsoft on 11.11.16.
 */
@RestController
public class SearchService {


    @RequestMapping("/search")
    public SearchResponse index() {

        SearchResponse searchResponse = new SearchResponse();
        SearchResult sr1 = new SearchResult();
        Document d1 = new Document();
        d1.getDocument().put("foo", "bar");

        sr1.getDocuments().add(d1);
        sr1.setName("treffer");
        searchResponse.getResult().put("treffer", sr1);


        SearchResult sr2 = new SearchResult();
        searchResponse.getResult().put("nulltreffer", sr2);


        DidYouMeanResult dymr = new DidYouMeanResult();
        dymr.setDidYouMean("Meinten sie tblsfot");
        dymr.setName("didyoumean");
        searchResponse.getResult().put("didyoumean", dymr);


        return searchResponse;
    }
}
