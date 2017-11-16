package de.tblsoft.search.query.parser;

import com.google.common.base.Strings;
import de.tblsoft.search.query.Filter;
import de.tblsoft.search.query.SearchQuery;
import de.tblsoft.search.query.RangeFilterValue;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tblsoft on 13.11.16.
 */
public class SaqlParser {

    private static final Pattern filterPattern = Pattern.compile("f\\.([^\\.]+)(.*)");


    private Map<String, String[]> parameters;

    private SearchQuery query = null;

    public SaqlParser(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }


    SearchQuery parse() {
        SearchQuery query = new SearchQuery();
        query.setQ(getParameter("q"));
        query.setRequestId(getParameter("requestId", UUID.randomUUID().toString()));
        query.setPage(getParameterAsInt("page", query.getPage()));
        query.setRows(getParameterAsInt("rows", query.getRows()));
        query.setDebug(getParameterAsBoolean("debug", query.isDebug()));

        parseFilter(query);
        return query;

    }

    void parseFilter(SearchQuery query) {
        for(String name: getParameterNames()) {
            Matcher m = filterPattern.matcher(name);
            if(m.matches()) {
                String filterName = m.group(1);
                String[] filterValues = parameters.get(name);
                String filterType = m.group(2);
                if(Strings.isNullOrEmpty(filterType)) {
                    Filter<String> filter = new Filter<>();
                    filter.setName(filterName);
                    filter.setValues(Arrays.asList(filterValues));
                    query.getFilterList().add(filter);
                } else if (".range".equals(filterType)) {
                    Filter<RangeFilterValue<Number>> filter = new Filter<>();
                    RangeFilterValue<Number> rangeFilterValue = new RangeFilterValue<>();
                    for(String value : filterValues) {
                        String[] valueSplitted = value.split(Pattern.quote(","));
                        if(valueSplitted.length != 2) {
                            throw new IllegalArgumentException("The value of a range filter must be in the format parameter=v1-v2");
                        }
                        String min = valueSplitted[0];
                        String max = valueSplitted[1];
                        try {
                            if (min.equals("min")) {
                                rangeFilterValue.setMinValue(Double.MIN_VALUE);
                            } else {
                                rangeFilterValue.setMinValue(Double.valueOf(min));
                            }

                            if (max.equals("max")) {
                                rangeFilterValue.setMaxValue(Double.MAX_VALUE);
                            } else {
                                rangeFilterValue.setMaxValue(Double.valueOf(max));
                            }
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("The min value " + min + " or max value " + max + " is no number value.");
                        }
                    }




                    filter.setName(filterName);
                    filter.getValues().add(rangeFilterValue);
                    query.getFilterList().add(filter);
                }

            }
        }

    }


    public SearchQuery getQuery() {
        if(this.query == null) {
            this.query = parse();
        }
        return this.query;
    }

    String getParameter(String name) {
        String[] values = this.parameters.get(name);

        if(values == null || values.length == 0) {
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

    Boolean getParameterAsBoolean(String name) {
        String value = getParameter(name);
        if("true".equals(value)) {
            return Boolean.TRUE;
        }
        if("false".equals(value)) {
            return Boolean.FALSE;
        }
        return null;
    }

    Boolean getParameterAsBoolean(String name, Boolean defaultValue) {
        Boolean value = getParameterAsBoolean(name);
        if(value == null) {
            return defaultValue;
        }
        return value;
    }

    Set<String> getParameterNames() {
        return this.parameters.keySet();
    }


}
