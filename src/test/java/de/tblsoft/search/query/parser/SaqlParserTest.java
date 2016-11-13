package de.tblsoft.search.query.parser;

import de.tblsoft.search.query.Query;
import org.junit.*;

import java.util.*;

/**
 * Created by tblsoft on 13.11.16.
 */
public class SaqlParserTest {


    @Test
    public void testParameterQ() {
        Query query = createQuery("q=foo");
        Assert.assertEquals("foo", query.getQ());
    }

    @Test
    public void testParameterRequestId() {
        Query query = createQuery("requestId=0815");
        Assert.assertEquals("0815", query.getRequestId());
    }

    @Test
    public void testEmty() {
        Query query = createQuery();
        Assert.assertNull(query.getQ());
        Assert.assertNotNull(query.getRequestId());
    }


    @Test(expected=IllegalArgumentException.class)
    public void testValidationForMultipleParameters() {
        createQuery("q=foo","q=bar");
    }

    Query createQuery(String... parameters) {
        Map<String,String[]> parameter = new HashMap<>();
        for(String param: parameters) {
            String[] paramSplitted = param.split("=");
            addParameter(parameter,paramSplitted[0],paramSplitted[1]);
        }
        SaqlParser saqlParser = new SaqlParser(parameter);
        Query query = saqlParser.getQuery();
        return query;
    }

    void addParameter(Map<String,String[]> parameter, String name, String value) {
        String[] values = parameter.get(name);
        if(values == null) {
            values = new String[0];
        }

        List<String> valueList = new ArrayList<>(Arrays.asList(values));
        valueList.add(value);
        values = valueList.toArray(new String[valueList.size()]);
        parameter.put(name,values);
    }
}
