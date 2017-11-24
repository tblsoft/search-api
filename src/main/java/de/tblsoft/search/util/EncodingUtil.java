package de.tblsoft.search.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by tbl on 18.11.17.
 */
public class EncodingUtil {

    public static String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }
}
