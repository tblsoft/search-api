package de.tblsoft.search.query.parser;

import de.tblsoft.search.query.Filter;
import de.tblsoft.search.query.Query;
import de.tblsoft.search.query.RangeFilterValue;
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
    public void testParameterPage() {
        Query query = createQuery("page=5");
        Assert.assertEquals(5, query.getPage());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testParameterPageInvalidValue() {
        createQuery("page=foo");
    }

    @Test
    public void testParameterRows() {
        Query query = createQuery("rows=50");
        Assert.assertEquals(50, query.getRows());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testParameterRowsInvalidValue() {
        createQuery("rows=foo");
    }

    @Test
    public void testEmty() {
        Query query = createQuery();
        Assert.assertNull(query.getQ());
        Assert.assertNotNull(query.getRequestId());
        Assert.assertEquals(1, query.getPage());
        Assert.assertEquals(20, query.getRows());
    }


    @Test(expected=IllegalArgumentException.class)
    public void testValidationForMultipleParameters() {
        createQuery("q=foo","q=bar");
    }

    @Test
    public void testFilter() {
        Query query = createQuery("f.foo=bar");
        Filter<String> filter = query.getFilterList().get(0);
        Assert.assertEquals(filter.getName(), "foo");
        Assert.assertEquals(filter.getValues().get(0), "bar");
    }

    @Test
    public void testFilterWithMultipleValues() {
        Query query = createQuery("f.foo=bar", "f.foo=alice");
        Filter<String> filter = query.getFilterList().get(0);
        Assert.assertEquals("foo",filter.getName());
        Assert.assertEquals("bar", filter.getValues().get(0));
        Assert.assertEquals("alice",filter.getValues().get(1));
    }

    @Test
    public void testRangeFilterForDoubleValues() {
        Query query = createQuery("f.foo.range=0.1,5.2");
        Filter<RangeFilterValue<Double>> filter = query.getFilterList().get(0);
        Assert.assertEquals("foo", filter.getName());
        Assert.assertEquals(Double.valueOf(0.1), filter.getValues().get(0).getMinValue());
        Assert.assertEquals(Double.valueOf(5.2), filter.getValues().get(0).getMaxValue());
    }

    @Test
    public void testRangeFilterForLongValues() {
        Query query = createQuery("f.foo.range=3,5");
        Filter<RangeFilterValue<Double>> filter = query.getFilterList().get(0);
        Assert.assertEquals("foo", filter.getName());
        Assert.assertEquals(Double.valueOf(3.0), filter.getValues().get(0).getMinValue());
        Assert.assertEquals(Double.valueOf(5.0), filter.getValues().get(0).getMaxValue());
    }

    @Test
    public void testRangeFilterForMinMaxValues() {
        Query query = createQuery("f.foo.range=min,max");
        Filter<RangeFilterValue<Double>> filter = query.getFilterList().get(0);
        Assert.assertEquals("foo", filter.getName());
        Assert.assertEquals(Double.valueOf(Double.MIN_VALUE), filter.getValues().get(0).getMinValue());
        Assert.assertEquals(Double.valueOf(Double.MAX_VALUE), filter.getValues().get(0).getMaxValue());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testRangeFilterWithWrongDelimiter() {
        createQuery("f.foo.range=0.1-5.2");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testRangeFilterWithWrongValue() {
        createQuery("f.foo.range=0.1,bar");
    }

    Query createQuery(String... parameters) {
        Map<String,String[]> parameter = new HashMap<String,String[]>();
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
