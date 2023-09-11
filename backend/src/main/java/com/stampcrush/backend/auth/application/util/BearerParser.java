package com.stampcrush.backend.auth.application.util;

public class BearerParser {

    private static final String TOKEN_AUTHORIZATION_HEADER = "bearer";
    private static final String DELIMITER = " ";

    public static boolean isBearerAuthType(String authorization) {
        return haveAuthorizationHeader(authorization) &&
                startWithBearer(authorization) &&
                haveAuthorizationHeader(authorization) &&
                haveAuthorizationValue(authorization);
    }

    private static boolean haveAuthorizationHeader(String authorization) {
        return authorization != null;
    }

    private static boolean startWithBearer(String authorization) {
        return authorization.toLowerCase().startsWith(TOKEN_AUTHORIZATION_HEADER);
    }

    private static boolean haveAuthorizationValue(String authorization) {
        return authorization.split(DELIMITER).length == 2;
    }

    public static String parseAuthorization(String authorization) {
        return authorization.split(DELIMITER)[1].trim();
    }
}
