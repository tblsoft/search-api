package de.tblsoft.search.query;

import java.util.List;

/**
 * Created by tblsoft on 11.11.16.
 */
public class Query {

    private String q;

    private List<Filter> filterList;

    private Sort sort;

    private int page = 1;

    private int rows = 20;

    void test() {
        Filter f = new Filter();
        //f.getValues().add("foo");

        RangeFilterValue<Double> rfv = new RangeFilterValue<Double>();


        rfv.setMaxValue(0.7);
        rfv.setMinValue(0.2);

        f.getValues().add(rfv);

        List<RangeFilterValue> foo = f.getValues();


        //filterList.add(f);
    }


}
