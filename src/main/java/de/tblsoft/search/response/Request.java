package de.tblsoft.search.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by tbl on 19.11.17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request {

    public Request(HttpServletRequest httpServletRequest) {
        this.path = httpServletRequest.getRequestURI();
        this.parameters = httpServletRequest.getParameterMap();

        this.query = httpServletRequest.getQueryString();
        if(Strings.isNullOrEmpty(this.query)) {
            this.url = httpServletRequest.getRequestURL().toString();
        } else {
            this.url = httpServletRequest.getRequestURL().append("?").append(this.query).toString();
        }
    }

    private String url;

    private String path;

    private String query;

    private Map<String, String[]> parameters;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String[]> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", path='" + path + '\'' +
                ", query='" + query + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
