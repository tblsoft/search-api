package de.tblsoft.search.pipeline;

import com.google.common.base.Throwables;
import de.tblsoft.search.query.SearchQuery;
import de.tblsoft.search.response.SearchResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private boolean success = true;

    private StringBuilder message = new StringBuilder();

    private boolean debug = false;

    private List<Object> debugStack = new ArrayList<>();

    private SearchQuery searchQuery = new SearchQuery();
    private Map<String, SearchResult> searchResults = new HashMap<>();

    private Map<String, ?> context = new HashMap<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    private Long startTime;

    public SearchQuery getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(SearchQuery searchQuery) {
        this.searchQuery = searchQuery;
    }

    public SearchResult getSearchResult(String name) {
        return searchResults.get(name);
    }


    public void putSearchResult(String name, SearchResult searchResult) {
        if(isDebugEnabled()) {
            debugStack.add(searchResult);
        }
        searchResults.put(name, searchResult);
    }

    public <T> T getContext(String name, Class<T> clazz) {
        return (T) context.get(name);
    }

    public Map<String, SearchResult> getSearchResults() {
        return searchResults;
    }

    public void start() {
        if(this.startTime == null) {
            this.startTime = System.currentTimeMillis();
        }
    }

    public long currentTime() {
        return System.currentTimeMillis() - this.startTime;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void debug(Object debugObject) {
        if(debug) {
            this.debugStack.add(debugObject);
        }
    }

    public void enableDebug() {
        this.debug = true;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isDebugEnabled() {
        return this.debug;
    }

    public List<Object> getDebugStack() {
        return debugStack;
    }

    public void error(String message) {
        this.success = false;
        if(this.message.length() != 0) {
            this.message.append(" ");
        }
        this.message.append(message);
    }

    public void error(Throwable e) {
        error(e.getMessage());
        error(Throwables.getStackTraceAsString(e));
    }

    public String getMessage() {
        return message.toString();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "PipelineContainer{" +
                "\nsearchQuery=" + searchQuery +
                "\n, searchResponses=" + searchResults +
                "\n, context=" + context +
                '}';
    }
}
