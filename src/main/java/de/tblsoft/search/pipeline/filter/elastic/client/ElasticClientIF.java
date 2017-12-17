package de.tblsoft.search.pipeline.filter.elastic.client;

import de.tblsoft.search.pipeline.filter.elastic.bean.ElasticResult;

import java.io.IOException;

/**
 * Created by tbl on 16.12.17.
 */
public interface ElasticClientIF {

    ElasticResult request(String elasticBaseUrl, String request) throws IOException;
}
