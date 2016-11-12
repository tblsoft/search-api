package de.tblsoft.search.response;

/**
 * Created by tblsoft on 11.11.16.
 */
public class DidYouMeanResult implements Result{

    private String name;

    private String didYouMean;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDidYouMean() {
        return didYouMean;
    }

    public void setDidYouMean(String didYouMean) {
        this.didYouMean = didYouMean;
    }
}
