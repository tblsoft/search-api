package de.tblsoft.search.test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by tbl on 25.11.17.
 */
public class AbstractPipelineTest {


    public void print(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(indented);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
