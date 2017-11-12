package de.tblsoft.search.pipeline;

/**
 * Created by tbl on 11.11.17.
 */
public class SearchRequest {

    private String q;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }


    public SearchRequest clone() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setQ(this.q);
        return searchRequest;
    }

    @Override
    public String toString() {
        return "\nSearchRequest{" +
                "q='" + q + '\'' +
                '}';
    }
}
