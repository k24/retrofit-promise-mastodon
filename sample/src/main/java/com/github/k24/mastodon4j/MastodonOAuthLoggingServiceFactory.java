package com.github.k24.mastodon4j;

import com.github.k24.mastodon4j.oauth.MastodonOAuthServiceFactory;
import com.github.scribejava.httpclient.okhttp.OkHttpHttpClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by k24 on 2017/04/24.
 */
public class MastodonOAuthLoggingServiceFactory extends MastodonOAuthServiceFactory {
    public MastodonOAuthLoggingServiceFactory(String host, HttpLoggingInterceptor.Level logLevel) {
        super(host);

        SslForLetsEncrypt sslForLetsEncrypt;
        try {
            sslForLetsEncrypt = SslForLetsEncrypt.create();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(logLevel);

        setHttpClient(new OkHttpHttpClient(new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .sslSocketFactory(sslForLetsEncrypt.sslSocketFactory, sslForLetsEncrypt.trustManager)
                .build()));
    }
}
