package de.tblsoft.search.pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tbl on 23.12.17.
 */
public class PipelineContainerDebugException extends Exception {

    private List<Object> debugStack = new ArrayList<>();

    public PipelineContainerDebugException(List<Object> debugStack) {
        this.debugStack = debugStack;
    }

    public List<Object> getDebugStack() {
        return debugStack;
    }
}
