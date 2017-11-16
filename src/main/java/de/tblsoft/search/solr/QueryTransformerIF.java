package de.tblsoft.search.solr;

import de.tblsoft.search.pipeline.PipelineContainer;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created by tblsoft on 21.11.16.
 */
public interface QueryTransformerIF {


    SolrQuery transform(PipelineContainer pipelineContainer);
}
