package de.tblsoft.search.pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tbl on 31.12.17.
 */
public class PipelineValidation {

    private List<PipelineValidationError> pipelineValidationErrors = new ArrayList<>();

    public boolean isValid() {
        return pipelineValidationErrors.size() == 0;
    }

    public void error(String message) {
        PipelineValidationError pipelineValidationError = new PipelineValidationError(message);
        pipelineValidationErrors.add(pipelineValidationError);
    }

    public List<PipelineValidationError> getPipelineValidationErrors() {
        return pipelineValidationErrors;
    }
}
