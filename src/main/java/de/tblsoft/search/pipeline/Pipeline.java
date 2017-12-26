package de.tblsoft.search.pipeline;

import de.tblsoft.search.pipeline.filter.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tbl on 11.11.17.
 */
public class Pipeline {

    private String id;

    private long timeout = 1000L;

    private List<Filter> filterList = new ArrayList<>();

    public Pipeline(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Filter> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Filter> filterList) {
        this.filterList = filterList;
    }

    public void addFilter(Filter filter) {
        this.filterList.add(filter);
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "Pipeline{" +
                "id='" + id + '\'' +
                ", filterList=" + filterList +
                '}';
    }
}
