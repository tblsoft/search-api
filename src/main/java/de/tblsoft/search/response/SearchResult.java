package de.tblsoft.search.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tbl on 11.11.16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResult{

    private Object rawResponse;

    private Integer statusCode;

    private String name;

    private String statusMessage = "";

    private List<Document> documents = new ArrayList<>();

    private Long time;

    private String debug;

    private Long total;

    private Long page;

    private Float maxScore;

    private List<Facet> facets = new ArrayList<>();


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

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public void debug(String message) {
        if(this.debug == null) {
            this.debug = message;
            return;
        }

        this.debug = " " + message;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Float getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Float maxScore) {
        this.maxScore = maxScore;
    }

    public List<Facet> getFacets() {
        return facets;
    }

    public void setFacets(List<Facet> facets) {
        this.facets = facets;
    }


    public Facet getFacetById(String id) {
        for(Facet facet : getFacets()) {
            if(id.equals(facet.getId())) {
                return facet;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "rawResponse=" + rawResponse +
                ", statusCode=" + statusCode +
                ", name='" + name + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", documents=" + documents +
                ", time=" + time +
                ", debug='" + debug + '\'' +
                ", total=" + total +
                ", page=" + page +
                ", maxScore=" + maxScore +
                ", facets=" + facets +
                '}';
    }
}
