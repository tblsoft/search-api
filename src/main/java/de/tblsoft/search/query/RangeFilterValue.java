package de.tblsoft.search.query;

/**
 * Created by tblsoft on 11.11.16.
 */
public class RangeFilterValue<T> {

    private T minValue;
    private T maxValue;


    public T getMinValue() {
        return minValue;
    }

    public void setMinValue(T minValue) {
        this.minValue = minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(T maxValue) {
        this.maxValue = maxValue;
    }
}
