package de.tblsoft.search;

import com.google.common.base.Splitter;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.mockito.Mockito;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URI;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by tbl on 25.11.17.
 */
public class Mockfactory {

    public static HttpServletRequest createHttpServletRequest(String url){
        try {
            URI uri = new URI(url);


            final Map<String, String> parametersMap = Splitter.on("&")
                    .omitEmptyStrings()
                    .trimResults()
                    .withKeyValueSeparator("=")
                    .split(uri.getQuery());


            HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
            Enumeration<String> parameterNames = Collections.enumeration(parametersMap.keySet());
            Mockito.when(httpServletRequest.getParameterNames()).thenReturn(parameterNames);
            for (String key : parametersMap.keySet()) {
                Mockito.when(httpServletRequest.getParameter(key)).thenReturn(parametersMap.get(key));
            }

            Mockito.when(httpServletRequest.getHeaderNames()).thenReturn(parameterNames);
            Cookie[] cookies = {};
            Mockito.when(httpServletRequest.getCookies()).thenReturn(cookies);

            return httpServletRequest;

        } catch (Exception e ) {
            throw new RuntimeException(e);
        }
    }
}
