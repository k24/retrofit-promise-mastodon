package com.github.k24.mastodon4j.oauth;

import com.github.k24.mastodon4j.model.App;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.oauth.OAuth20Service;

/**
 * Created by k24 on 2017/04/23.
 */
public class MastodonOAuthServiceFactory {
    private final String host;
    private HttpClient httpClient;
    private String userAgent;

    public MastodonOAuthServiceFactory(String host) {
        this.host = host;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public OAuth20Service create(App app) {
        return create(app.client_id, app.client_secret, app.redirect_uri);
    }

    public OAuth20Service create(String cliendId, String clientSecret, String redirectUri) {
        return new ServiceBuilder()
                .apiKey(cliendId)
                .apiSecret(clientSecret)
                .callback(redirectUri)
                .httpClient(httpClient)
                .userAgent(userAgent)
                .build(MastodonOAuthApi.instance(host));
    }
}
