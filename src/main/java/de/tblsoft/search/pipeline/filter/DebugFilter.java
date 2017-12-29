package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;

/**
 * Created by tbl on 30.12.17.
 */
public class DebugFilter extends AbstractFilter {

    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) {
        if("true".equals(pipelineContainer.getRequest().getParameter("debug"))) {
            pipelineContainer.setDebug(true);
        }
        return pipelineContainer;
    }
}
