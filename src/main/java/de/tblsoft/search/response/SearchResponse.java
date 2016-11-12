package de.tblsoft.search.response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tblsoft on 11.11.16.
 */
public class SearchResponse {

    private String statusCode = "200";

    private Map<String, Result> result = new HashMap<>();


    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, Result> getResult() {
        return result;
    }

    public void setResult(Map<String, Result> result) {
        this.result = result;
    }
}
