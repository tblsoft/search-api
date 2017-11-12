package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;

/**
 * Created by tbl on 04.11.17.
 */
public interface Filter {

    void setStartTime(long startTime);

    long getCurrentTime();

    void setPipelineId(String id);
    void init();

    PipelineContainer filter(PipelineContainer pipelineContainer);

    void end();
}
