package de.tblsoft.search.query;

import com.google.common.base.Strings;

import java.util.List;
import java.util.UUID;

/**
 * Created by tblsoft on 11.11.16.
 */
public class Query {

    private String q;

    private String requestId;

    private List<Filter> filterList;

    private Sort sort;

    private int page = 1;

    private int rows = 20;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        if(Strings.isNullOrEmpty(requestId)) {
            requestId = UUID.randomUUID().toString();
        }
        this.requestId = requestId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
