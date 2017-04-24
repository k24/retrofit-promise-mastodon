package com.github.k24.mastodon4j;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by k24 on 2017/04/22.
 */
public class MastodonLoggingApiAgent extends MastodonApiAgent {
    public MastodonLoggingApiAgent(Retrofit retrofit) {
        super(retrofit);
    }

    public static MastodonLoggingApiAgent create(String accessToken, Config config, HttpLoggingInterceptor.Level logLevel) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(logLevel);

        SslForLetsEncrypt sslForLetsEncrypt;
        try {
            sslForLetsEncrypt = SslForLetsEncrypt.create();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new MastodonLoggingApiAgent(retrofitBuilder(config)
                .client(okHttpClientBuilder(accessToken, config.userAgent)
                        .addInterceptor(loggingInterceptor)
                        .sslSocketFactory(sslForLetsEncrypt.sslSocketFactory, sslForLetsEncrypt.trustManager)
                        .build())
                .build());
    }

}
