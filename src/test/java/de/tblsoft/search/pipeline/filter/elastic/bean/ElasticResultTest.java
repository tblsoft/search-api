package de.tblsoft.search.pipeline.filter.elastic.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;

/**
 * Created by tbl on 19.11.17.
 */
public class ElasticResultTest {

    @Test
    public void deserialize() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String file = "src/test/resources/de/tblsoft/search/pipeline/filter/elastic/bean/elastic.json";
        ElasticResult elasticResult = objectMapper.readValue(new File(file), ElasticResult.class);
        System.out.println(elasticResult);

    }
}
