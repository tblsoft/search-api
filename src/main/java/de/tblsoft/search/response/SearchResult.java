package de.tblsoft.search.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tblsoft on 11.11.16.
 */
public class SearchResult{

    private Object rawResponse;

    private Integer statusCode;

    private String name;

    private String statusMessage = "";

    private List<Document> documents = new ArrayList<>();

    private Long time;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Object getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(Object rawResponse) {
        this.rawResponse = rawResponse;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
