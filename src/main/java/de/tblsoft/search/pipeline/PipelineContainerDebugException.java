package de.tblsoft.search.pipeline;

import java.util.List;

/**
 * Created by tbl on 23.12.17.
 */
public class PipelineContainerDebugException extends Exception {

    private PipelineContainer pipelineContainer;

    public PipelineContainerDebugException(PipelineContainer pipelineContainer) {
        this.pipelineContainer = pipelineContainer;
    }

    public List<Object> getDebugStack() {
        return this.pipelineContainer.getDebugStack();
    }
}
