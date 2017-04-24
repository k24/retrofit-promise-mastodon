package com.github.k24.mastodon4j.oauth;

import com.github.scribejava.core.builder.api.DefaultApi20;

import java.util.Locale;

/**
 * Created by k24 on 2017/04/23.
 */
public class MastodonOAuthApi extends DefaultApi20 {
    public static final String URL_TOKEN_FORMAT = "https://%s/oauth/token";
    public static final String URL_AUTHORIZE_FORMAT = "https://%s/oauth/authorize";
    private final String host;

    protected MastodonOAuthApi(String host) {
        this.host = host;
    }

    public static MastodonOAuthApi instance(String host) {
        return new MastodonOAuthApi(host);
    }

    public static String buildUrl(String format, String host) {
        return String.format(Locale.US, format, host);
    }

    @Override
    public String getAccessTokenEndpoint() {
        return buildUrl(URL_TOKEN_FORMAT, host);
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return buildUrl(URL_AUTHORIZE_FORMAT, host);
    }
}
