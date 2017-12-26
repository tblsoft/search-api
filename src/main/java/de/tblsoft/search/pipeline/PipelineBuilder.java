package de.tblsoft.search.pipeline;

import de.tblsoft.search.pipeline.filter.Filter;
import de.tblsoft.search.pipeline.filter.ParallelFilter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tbl on 11.11.17.
 */
public class PipelineBuilder {

    private PipelineBuilder parent;

    private Pipeline pipeline;

    private ParallelFilter parallelFilter;

    public static PipelineBuilder create() {
        return new PipelineBuilder();
    }

    public PipelineBuilder pipeline(String id) {
        if(this.pipeline == null) {
            pipeline = new Pipeline(id);
            return this;
        }

        if(parent == null) {
            PipelineBuilder pipelineBuilder = new PipelineBuilder();
            pipelineBuilder.setParent(this);
            pipelineBuilder.pipeline(id);
            return pipelineBuilder;
        }

        if(parallelFilter == null) {
            parent.parallelFilter.addPipeline(this.build());
            PipelineBuilder pipelineBuilder = new PipelineBuilder();
            pipelineBuilder.setParent(parent);
            pipelineBuilder.pipeline(id);
            return pipelineBuilder;
        }

        parallelFilter.addPipeline(this.build());
        return parent;
    }


    public PipelineBuilder filter(Filter filter, String id) {
        pipeline.addFilter(filter);
        return this;
    }

    public PipelineBuilder filter(Filter filter) {
        return filter(filter, pipeline.getId());
    }

    public PipelineBuilder parallel() {
        parallelFilter = new ParallelFilter(pipeline.getId());
        pipeline.addFilter(parallelFilter);
        return this;
    }

    public PipelineBuilder sequential() {
        parent.parallelFilter.addPipeline(this.build());
        return parent;
    }

    public Pipeline build() {
        ensureFilterIds();
        return pipeline;
    }

    public void setParent(PipelineBuilder parent) {
        this.parent = parent;
    }


    public void ensureFilterIds() {
        Set<String> alreadyUsedIds = new HashSet<>();

        for(Filter filter : pipeline.getFilterList()) {
            if(filter.getId() == null) {
                String suggestedId = pipeline.getId() + "." + filter.getClass().getSimpleName();
                int counter = 1;
                while(alreadyUsedIds.contains(suggestedId)) {
                    suggestedId = suggestedId + counter++;
                }
                alreadyUsedIds.add(suggestedId);
                filter.setId(suggestedId);
            }
        }

    }
}
