package de.tblsoft.search.pipeline;

/**
 * Created by tbl on 31.12.17.
 */
public class PipelineValidationError {

    public PipelineValidationError(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }
}
