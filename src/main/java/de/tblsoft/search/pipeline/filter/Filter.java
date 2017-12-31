package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.pipeline.PipelineValidation;

/**
 * Created by tbl on 04.11.17.
 */
public interface Filter {

    void start();

    long getCurrentTime();

    void setId(String id);

    String getId();
    void init();

    PipelineContainer filter(PipelineContainer pipelineContainer) throws Exception;

    PipelineContainer onError(PipelineContainer pipelineContainer, Exception e);

    void end();

    boolean isActive();

    StringBuilder print(String indent);

    PipelineValidation validate(PipelineValidation pipelineValidation);

}
