package de.tblsoft.search.pipeline.filter;

import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.response.SearchResult;

/**
 * Created by tbl on 11.11.17.
 */
public class SleepFilter extends AbstractFilter {


    private long sleepTime;

    public SleepFilter(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) {
        try {
            SearchResult searchResponse = new SearchResult();
            searchResponse.setStatusCode(200);
            searchResponse.setStatusMessage(Thread.currentThread().getName());
            pipelineContainer.putSearchResult(getId(),searchResponse);
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            // do nothing
        }
        return pipelineContainer;
    }
}
