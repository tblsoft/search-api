package de.tblsoft.search.query.parser;

import de.tblsoft.search.query.Query;
import org.junit.*;

import java.util.*;

/**
 * Created by tblsoft on 13.11.16.
 */
public class SaqlParserTest {

    @Test
    public void testEmty() {
        Map<String,String[]> emtyParameter = new HashMap<>();
        SaqlParser saqlParser = new SaqlParser(emtyParameter);
        Query query = saqlParser.getQuery();
        Assert.assertNotNull(query.getRequestId());
    }


    @Test(expected=IllegalArgumentException.class)
    public void testValidationForMultipleParameters() {
        Map<String,String[]> parameter = new HashMap<>();
        addParameter(parameter,"q","foo");
        addParameter(parameter,"q","bar");

        SaqlParser saqlParser = new SaqlParser(parameter);
        saqlParser.getQuery();

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
