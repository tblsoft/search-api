package de.tblsoft.search.response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tblsoft on 11.11.16.
 */
public class SearchResponse {

    private String statusCode = "200";

    private Map<String, SearchResult> result = new HashMap<>();

    private long time;


    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, SearchResult> getResult() {
        return result;
    }

    public void setResult(Map<String, SearchResult> result) {
        this.result = result;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
