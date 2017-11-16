package de.tblsoft.search.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tblsoft on 11.11.16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponse {

    private Integer statusCode = 200;

    private Map<String, SearchResult> result = new HashMap<>();

    private long time;

    private Date currentTime = new Date();


    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
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

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }
}
