package de.tblsoft.search.query.parser;

import de.tblsoft.search.query.Query;
import org.apache.commons.lang.ArrayUtils;

import java.util.Map;

/**
 * Created by tblsoft on 13.11.16.
 */
public class SaqlParser {


    private Map<String, String[]> parameters;

    private Query query = null;

    public SaqlParser(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }


    Query parse() {
        Query query = new Query();
        query.setQ(getParameter("q"));
        query.setRequestId(getParameter("requestId"));
        query.setPage(getParameterAsInt("page",query.getPage()));
        query.setRows(getParameterAsInt("rows",query.getRows()));
        return query;

    }

    public Query getQuery() {
        if(this.query == null) {
            this.query = parse();
        }
        return this.query;
    }

    String getParameter(String name) {
        String[] values = this.parameters.get(name);

        if(ArrayUtils.isEmpty(values)) {
            return null;
        }

        if(values.length != 1) {
            throw new IllegalArgumentException("The parameter " + name + " must have exactly one value.");
        }
        return values[0];
    }

    String getParameter(String name, String defaultValue) {
        String value = getParameter(name);
        if(value == null) {
            return defaultValue;
        }
        return value;
    }

    Integer getParameterAsInt(String name) {
        String value = getParameter(name);
        if(value == null) {
            return null;
        }
        try {
            Integer intValue = Integer.parseInt(value);
            return intValue;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    Integer getParameterAsInt(String name, Integer defaultValue) {
        Integer value = getParameterAsInt(name);
        if(value == null) {
            return defaultValue;
        }
        return value;
    }


}
