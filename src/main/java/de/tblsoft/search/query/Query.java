package de.tblsoft.search.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tblsoft on 11.11.16.
 */
public class Query {

    private String q;

    private String requestId;

    private List<Filter> filterList = new ArrayList<>();

    private Sort sort;

    private int page = 1;

    private int rows = 20;

    private boolean debug = false;

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

    public List<Filter> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Filter> filterList) {
        this.filterList = filterList;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
