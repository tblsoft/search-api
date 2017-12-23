package de.tblsoft.search.test;

import de.tblsoft.search.util.JsonUtil;

/**
 * Created by tbl on 25.11.17.
 */
public class AbstractPipelineTest {


    public void print(Object object) {
        try {
            System.out.println(JsonUtil.toPrettyString(object));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
