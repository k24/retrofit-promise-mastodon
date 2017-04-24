package com.github.k24.mastodon4j.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.mastodon4j.model.App;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import javax.annotation.Nonnull;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#apps
 * <p>
 * Created by k24 on 2017/04/21.
 */
public interface AppsApi {
    @FormUrlEncoded
    @POST("apps")
    Deferred.Promise<App> postApp(@Field("client_name") String clientName,
                                  @Field("redirect_uris") String redirectUris,
                                  @Field("scopes") String scopes);

    @FormUrlEncoded
    @POST("apps")
    Deferred.Promise<App> postApp(@FieldMap Map<String, Object> fields);

    /**
     * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#registering-an-application
     */
    class AppFieldMap extends AbstractMap<String, Object> {
        public static final String REDIRECT_URI_NONE = "urn:ietf:wg:oauth:2.0:oob";
        private final Map<String, Object> map;

        AppFieldMap(Builder builder) {
            map = builder.toMap();
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public Set<Entry<String, Object>> entrySet() {
            return map.entrySet();
        }

        @SuppressWarnings("WeakerAccess")
        public static class Builder {
            String client_name;
            List<String> redirect_uris;
            String scopes;
            String website;

            public Builder clientName(String clientName) {
                client_name = clientName;
                return this;
            }

            // Multiple-URIs way is not supported actually.
            public Builder redirectUris(String redirectUris) {
                redirect_uris = Collections.singletonList(redirectUris);
                return this;
            }

            public Builder scopes(String scopes) {
                this.scopes = scopes;
                return this;
            }

            public Builder website(String website) {
                this.website = website;
                return this;
            }

            @Nonnull
            public Map<String, Object> toMap() {
                HashMap<String, Object> map = new HashMap<>();

                if (client_name != null) map.put("client_name", client_name);
                if (redirect_uris != null) map.put("redirect_uris", redirect_uris.get(0));
                if (scopes != null) map.put("scopes", scopes);
                if (website != null) map.put("website", website);

                return map;
            }

            public AppFieldMap build() {
                return new AppFieldMap(this);
            }
        }
    }
}
