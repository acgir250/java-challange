/**
 *
 *  This is the utility class for the header management to validate header things.
 */
package jp.co.axa.apidemo.utils;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class HeadersUtils {

    private static final String AUTH = "authorization";

    public boolean validateTokenHeader(Map<String, String> httpHeaders) {
        return httpHeaders.containsKey(AUTH) && httpHeaders.get(AUTH) != null;
    }
}
