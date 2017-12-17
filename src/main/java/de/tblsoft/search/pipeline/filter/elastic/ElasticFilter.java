package de.tblsoft.search.pipeline.filter.elastic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.pipeline.filter.AbstractFilter;
import de.tblsoft.search.pipeline.filter.elastic.bean.ElasticResult;
import de.tblsoft.search.pipeline.filter.elastic.bean.Hit;
import de.tblsoft.search.pipeline.filter.elastic.client.ElasticClientIF;
import de.tblsoft.search.pipeline.filter.elastic.client.StandardElasticClient;
import de.tblsoft.search.pipeline.filter.web.RequestParser;
import de.tblsoft.search.response.Document;
import de.tblsoft.search.response.SearchResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by tbl on 19.11.17.
 */
public class ElasticFilter extends AbstractFilter {

    private static Logger LOG = LoggerFactory.getLogger(ElasticFilter.class);

    private String profile;
    private String elasticBaseUrl;

    private String resultSetId;

    ElasticClientIF elasticClient = new StandardElasticClient();

    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) throws Exception {
        Map<String, String> replaceMap = RequestParser.getRequestParameter(pipelineContainer);
        String request = loadProfile(profile, replaceMap);

        ElasticResult elasticResult = elasticClient.request(elasticBaseUrl + "/_search", request);

        SearchResult searchResult = new SearchResult();
        searchResult.initDocuments();
        searchResult.setTotal(elasticResult.getHits().getTotal());
        searchResult.setStatusMessage("OK");

        for(Hit hit :elasticResult.getHits().getHits()) {
            ObjectNode objectNode = hit.get_source();
            Iterator<Map.Entry<String, JsonNode>> it = objectNode.fields();
            Document document = new Document();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                document.getDocument().put(entry.getKey(), entry.getValue());
            }

            for(Map.Entry<String, List<String>> entry : hit.getHighlight().entrySet()) {
                document.getDocument().put("highlight." + entry.getKey(), entry.getValue());

            }
            searchResult.addDocument(document);
        }


        pipelineContainer.putSearchResult(resultSetId,searchResult);

        return pipelineContainer;
    }

    private String loadProfileFromFile(String filename) throws IOException {
        File file = new File(filename);
        String profile = Files.toString(file, Charsets.UTF_8);
        return profile;
    }

    private String loadProfileFromClasspath(String filename) throws IOException {

        String resource = filename.replaceFirst("classpath://", "");
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream(resource);

        String profile = IOUtils.toString(in, Charset.forName("UTF-8"));
        IOUtils.closeQuietly(in);
        return profile;


    }

    private String loadProfile(String filename, Map<String, String> vars) throws IOException {
        String profile = null;
        if(filename.startsWith("classpath://")) {
            profile = loadProfileFromClasspath(filename);
        } else {
            profile = loadProfileFromFile(filename);
        }


        StrSubstitutor strSubstitutor = new StrSubstitutor(vars);
        profile = strSubstitutor.replace(profile);
        return profile;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getElasticBaseUrl() {
        return elasticBaseUrl;
    }

    public void setElasticBaseUrl(String elasticBaseUrl) {
        this.elasticBaseUrl = elasticBaseUrl;
    }

    public String getResultSetId() {
        return resultSetId;
    }

    public void setResultSetId(String resultSetId) {
        this.resultSetId = resultSetId;
    }

    public void setElasticClient(ElasticClientIF elasticClient) {
        this.elasticClient = elasticClient;
    }
}
