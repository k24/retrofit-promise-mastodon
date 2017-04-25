package com.github.k24.mastodon4j;

import com.github.k24.deferred.Deferred;
import com.github.k24.mastodon4j.api.*;
import com.github.k24.mastodon4j.model.App;
import com.github.k24.retrofit2.adapter.promise.PromiseCallAdapterFactory;
import com.github.k24.retrofit2.converter.success.SuccessConverterFactory;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Created by k24 on 2017/04/20.
 */
@SuppressWarnings("WeakerAccess")
public class MastodonApiAgent {
    public static final String URL_BASE_FORMAT = "https://%s/api/v1/";
    private final Retrofit retrofit;

    public MastodonApiAgent(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public static Deferred.Promise<App> registerApp(Config config, AppsApi.AppFieldMap appFieldMap) {
        return create(null, config).retrofit.create(AppsApi.class).postApp(appFieldMap);
    }

    public static MastodonApiAgent create(String accessToken, Config config) {
        return new MastodonApiAgent(retrofitBuilder(config)
                .client(okHttpClientBuilder(accessToken, config.userAgent).build())
                .build());
    }

    public static class Config {
        public final Converter.Factory converterFactory;
        public final Deferred.Factory deferredFactory;
        public final String baseUrl;
        public final String userAgent;

        public Config(Converter.Factory converterFactory, Deferred.Factory deferredFactory) {
            this(converterFactory, deferredFactory, null, null);
        }

        private Config(Converter.Factory converterFactory, Deferred.Factory deferredFactory, String baseUrl, String userAgent) {
            this.converterFactory = converterFactory;
            this.deferredFactory = deferredFactory;
            this.baseUrl = baseUrl != null ? baseUrl : String.format(Locale.US, URL_BASE_FORMAT, BuildConfig.URL_HOST_DEFAULT);
            this.userAgent = userAgent != null ? userAgent : "MastodonApiAgent/" + BuildConfig.AGENT_VERSION;
        }

        private Config(Builder builder) {
            this(builder.converterFactory, builder.deferredFactory, builder.baseUrl, builder.userAgent);
        }

        public static class Builder {
            Converter.Factory converterFactory;
            Deferred.Factory deferredFactory;
            String baseUrl;
            String userAgent;

            public Builder converterFactory(Converter.Factory converterFactory) {
                this.converterFactory = converterFactory;
                return this;
            }

            public Builder deferredFactory(Deferred.Factory deferredFactory) {
                this.deferredFactory = deferredFactory;
                return this;
            }

            public Builder baseUrl(String baseUrl) {
                this.baseUrl = baseUrl;
                return this;
            }

            public Builder userAgent(String userAgent) {
                this.userAgent = userAgent;
                return this;
            }

            public Config build() {
                return new Config(this);
            }
        }
    }

    public static String buildBaseUrl(String host) {
        return String.format(Locale.US, URL_BASE_FORMAT, host);
    }

    protected static Retrofit.Builder retrofitBuilder(Config config) {
        return new Retrofit.Builder()
                .baseUrl(config.baseUrl)
                .addCallAdapterFactory(PromiseCallAdapterFactory.create(config.deferredFactory))
                .addConverterFactory(SuccessConverterFactory.create())
                .addConverterFactory(config.converterFactory);
    }

    protected static OkHttpClient.Builder okHttpClientBuilder(final String accessToken, final String userAgent) {
        return new OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .supportsTlsExtensions(true)
                        .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_3)
                        .build()))
                .addInterceptor(new Interceptor() {
                    private final String authorization = accessToken == null ? null : String.format(Locale.US, "Bearer %s", accessToken);

                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request.Builder builder = originalRequest.newBuilder();
                        if (authorization != null) {
                            builder.addHeader("Authorization", authorization);
                        }
                        Request request = builder
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Accept", "application/json")
                                .addHeader("User-Agent", userAgent)
                                .method(originalRequest.method(), originalRequest.body())
                                .build();
                        return chain.proceed(request);
                    }
                });
    }

    public static class BuildConfig {
        public static final String AGENT_VERSION;
        public static final String URL_HOST_DEFAULT;

        static {
            Properties props = new Properties();
            try (InputStream in = MastodonApiAgent.class.getResourceAsStream("/version.properties")) {
                props.load(in);
            } catch (IOException e) {
                props.put("version", "error");
            }
            AGENT_VERSION = props.getProperty("version", "unknown");

            String country = Locale.getDefault().getCountry();
            if (country == null) {
                country = Locale.JAPAN.getCountry();
            }
            switch (country) {
                case "JP":
                    URL_HOST_DEFAULT = "mstdn.jp";
                    break;
                default:
                    URL_HOST_DEFAULT = "mastodon.social";
            }
        }
    }

    //region Apis
    private final Map<Class, Object> apis = new HashMap<>();

    @SuppressWarnings("unchecked")
    protected <T> T ensureApi(Class<T> apiClass) {
        synchronized (apis) {
            Object api = apis.get(apiClass);
            if (api == null) {
                api = retrofit.create(apiClass);
                apis.put(apiClass, api);
            }
            return (T) api;
        }
    }

    protected void clearApis() {
        synchronized (apis) {
            apis.clear();
        }
    }

    public AccountsApi accounts() {
        return ensureApi(AccountsApi.class);
    }

    public AppsApi apps() {
        return ensureApi(AppsApi.class);
    }

    public BlocksApi blocks() {
        return ensureApi(BlocksApi.class);
    }

    public FavouritesApi favourites() {
        return ensureApi(FavouritesApi.class);
    }

    public FollowRequestsApi followRequests() {
        return ensureApi(FollowRequestsApi.class);
    }

    public FollowsApi follows() {
        return ensureApi(FollowsApi.class);
    }

    public InstancesApi instances() {
        return ensureApi(InstancesApi.class);
    }

    public MediaApi media() {
        return ensureApi(MediaApi.class);
    }

    public MutesApi mutes() {
        return ensureApi(MutesApi.class);
    }

    public NotificationsApi notifications() {
        return ensureApi(NotificationsApi.class);
    }

    public ReportsApi reports() {
        return ensureApi(ReportsApi.class);
    }

    public SearchApi search() {
        return ensureApi(SearchApi.class);
    }

    public StatusesApi statuses() {
        return ensureApi(StatusesApi.class);
    }

    public TimelinesApi timelines() {
        return ensureApi(TimelinesApi.class);
    }
    //endregion
}
