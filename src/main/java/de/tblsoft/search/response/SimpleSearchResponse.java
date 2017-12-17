package de.tblsoft.search.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tbl on 03.12.17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleSearchResponse extends HashMap<String,Map<String, Object>> {


}
