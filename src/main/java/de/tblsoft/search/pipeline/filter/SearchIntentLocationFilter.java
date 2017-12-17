package de.tblsoft.search.pipeline.filter;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import de.tblsoft.search.pipeline.PipelineContainer;
import de.tblsoft.search.query.SearchQuery;
import de.tblsoft.search.response.Document;
import de.tblsoft.search.response.SearchResult;
import de.tblsoft.search.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by tbl on 9.12.17.
 */
public class SearchIntentLocationFilter extends AbstractFilter {

    @Override
    public PipelineContainer filter(PipelineContainer pipelineContainer) {
        SearchResult locationLookup = pipelineContainer.getSearchResult("locationLookup");
        SearchQuery searchQuery = pipelineContainer.getSearchQuery();
        if(locationLookup == null) {
            return pipelineContainer;
        }
        String highlightedCityName = locationLookup.getDocuments().stream().findFirst().map(f -> f.getFieldValue("highlight.name")).orElse(null);

        List<String> normalizedValues = Collections.emptyList();
        if(highlightedCityName != null) {
            normalizedValues = getHighlightedValues(highlightedCityName).
                    stream().
                    map(SearchIntentLocationFilter::normalizeToken).
                    collect(Collectors.toList());
        }




        List<String> locationTokens = new ArrayList<>();
        List<String> otherTokens = new ArrayList<>();
        for(String token :Splitter.on(" ").split(searchQuery.getQ())) {
            String normalizedToken = normalizeToken(token);
            if(TextUtils.isGermanPostalCode(token)) {
                otherTokens.add(token);
            } else if(normalizedValues.contains(normalizedToken)) {
                locationTokens.add(token);
            } else {
                otherTokens.add(token);
            }
        }

        SearchResult locationSearchIntent = new SearchResult();
        locationSearchIntent.setStatusCode(200);
        locationSearchIntent.setStatusMessage("OK");
        locationSearchIntent.setTotal(1L);


        Document document = new Document();
        String location = Joiner.on(" ").join(locationTokens);
        String other = Joiner.on(" ").join(otherTokens);

        document.getDocument().put("location", location);
        document.getDocument().put("other", other);
        locationSearchIntent.addDocument(document);

        pipelineContainer.putSearchResult("search-intent",locationSearchIntent);
        return pipelineContainer;
    }




    private static String normalizeToken(String token) {
        token = token.toLowerCase();
        token = TextUtils.replaceGermanUmlaut(token);
        return token;
    }



    private static List<String> getHighlightedValues(String str) {
        if(str==null) {
            return Collections.emptyList();
        }
        Pattern TAG_REGEX = Pattern.compile(".*?<em>(.*?)</em>.*?");
        final List<String> tagValues = new ArrayList<>();
        final Matcher matcher = TAG_REGEX.matcher(str);
        while (matcher.find()) {
            tagValues.add(matcher.group(1));
        }
        return tagValues;
    }

}
