package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;

/**
 * Created by tbl on 04.11.17.
 */
public abstract class AbstractFilter implements Filter {

    private String id;

    private long startTime;

    private boolean active = true;

    @Override
    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void init() {

    }

    @Override
    public void end() {

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

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
