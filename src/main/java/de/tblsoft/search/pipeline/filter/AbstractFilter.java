package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;

/**
 * Created by tbl on 04.11.17.
 */
public abstract class AbstractFilter implements Filter {

    private String pipelineId;

    private long startTime;

    @Override
    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    @Override
    public void init() {

    }

    @Override
    public void end() {

    }

    public String getPipelineId() {
        return pipelineId;
    }

    @Override
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public long getCurrentTime() {
        return System.currentTimeMillis() - this.startTime;
    }

    @Override
    public PipelineContainer onError(PipelineContainer pipelineContainer, Exception e) {
        e.printStackTrace();
        return pipelineContainer;
    }
}
