package de.tblsoft.search.pipeline.filter.web;

import com.google.common.base.Strings;
import de.tblsoft.search.pipeline.PipelineContainer;

import javax.servlet.http.Cookie;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by tbl on 02.12.17.
 */
public class RequestParser {


    public  static Map<String, String> getRequestParameter(PipelineContainer pipelineContainer) {
        synchronized (pipelineContainer.getRequest()) {
            Map<String, String> replaceMap = new HashMap<>();
            Enumeration<String> parameterName = pipelineContainer.getRequest().getParameterNames();
            while (parameterName.hasMoreElements()) {
                String name = parameterName.nextElement();
                replaceMap.put(name, pipelineContainer.getRequest().getParameter(name));
                replaceMap.put("query." + name, pipelineContainer.getRequest().getParameter(name));
            }

            Enumeration<String> headerNames =  pipelineContainer.getRequest().getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = pipelineContainer.getRequest().getHeader(name);
                replaceMap.put("header." + name, value);
            }


            if(pipelineContainer.getRequest().getCookies() != null) {
                for (Cookie cookie : pipelineContainer.getRequest().getCookies()) {
                    String name = cookie.getName();
                    String value = cookie.getValue();
                    replaceMap.put("cookie." + name, value);
                }
            }

            String path = pipelineContainer.getRequest().getRequestURI();
            int pathCounter = 0;
            for(String pathPart: path.split(Pattern.quote("/"))) {
                if(!Strings.isNullOrEmpty(pathPart)) {
                    replaceMap.put("path" + pathCounter++ + ".", pathPart);
                }
            }
            return replaceMap;
        }

    }
}
