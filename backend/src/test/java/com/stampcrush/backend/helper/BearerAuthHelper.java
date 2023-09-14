package com.stampcrush.backend.helper;

import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.application.util.JwtTokenProvider;

public final class BearerAuthHelper {

    private static AuthTokensGenerator authTokensGenerator =
            new AuthTokensGenerator(new JwtTokenProvider("eyJpc3MiOiJ2ZWxvcGVydC5jb20iLCJleHAiOiIxNDg1MjcwMDAwMDAwIiwiaHR0cHM6Ly92ZWxvcGVydC5jb20vand0X2NsYWltcy9pc19hZG1pbiI6dHJ1ZSwidXNlcklkIjoiMTEwMjgzNzM3MjcxMDIiLCJ1c2VybmFtZSI6InZlbG9wZXJ0In0"));

    public static String generateToken(Long id) {
        return authTokensGenerator.generate(id).getAccessToken();
    }
}
