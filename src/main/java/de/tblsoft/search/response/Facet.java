package de.tblsoft.search.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tbl on 12.11.17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Facet {

    private String name;

    private String id;

    private List<FacetValue> values = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FacetValue> getValues() {
        return values;
    }

    public void setValues(List<FacetValue> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Facet{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", values=" + values +
                '}';
    }
}
