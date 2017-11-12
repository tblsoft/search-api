package de.tblsoft.search.response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tblsoft on 11.11.16.
 */
public class Document {

    private Map<String, Object> document = new HashMap<>();

    public Map<String, Object> getDocument() {
        return document;
    }

    public void setDocument(Map<String, Object> document) {
        this.document = document;
    }
}
