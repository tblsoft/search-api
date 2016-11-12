package de.tblsoft.search.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tblsoft on 11.11.16.
 */
public class SearchResult implements Result{

    private String name;

    private List<Document> documents = new ArrayList<>();


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
}
