package de.tblsoft.search.pipeline;

import de.tblsoft.search.response.SearchResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tbl on 04.11.17.
 */
public class PipelineContainer {

    public PipelineContainer() {
    }

    public PipelineContainer(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    private SearchRequest searchRequests;
    private Map<String, SearchResult> searchResults = new HashMap<>();

    private Map<String, ?> context = new HashMap<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    private long startTime;

    private long endTime;

    public SearchRequest getSearchRequests() {
        return searchRequests;
    }

    public void setSearchRequests(SearchRequest searchRequests) {
        this.searchRequests = searchRequests;
    }

    public SearchResult getSearchResponse(String name) {
        return searchResults.get(name);
    }


    public void putSearchResponse(String name, SearchResult searchResponse) {
        searchResults.put(name, searchResponse);
    }

    public <T> T getContext(String name, Class<T> clazz) {
        return (T) context.get(name);
    }

    public Map<String, SearchResult> getSearchResults() {
        return searchResults;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public long currentTime() {
        return System.currentTimeMillis() - this.startTime;
    }



    @Override
    public String toString() {
        return "PipelineContainer{" +
                "\nsearchRequests=" + searchRequests +
                "\n, searchResponses=" + searchResults +
                "\n, context=" + context +
                '}';
    }
}
