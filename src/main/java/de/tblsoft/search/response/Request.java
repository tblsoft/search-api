package de.tblsoft.search.response;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by tbl on 19.11.17.
 */
public class Request {

    public Request(HttpServletRequest httpServletRequest) {
        this.path = httpServletRequest.getPathInfo();
        this.parameters = httpServletRequest.getParameterMap();
    }

    private String path = "";

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

    @Override
    public String toString() {
        return "Request{" +
                "path='" + path + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
