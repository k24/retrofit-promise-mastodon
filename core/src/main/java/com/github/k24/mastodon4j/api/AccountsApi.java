package com.github.k24.mastodon4j.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.mastodon4j.model.Account;
import com.github.k24.mastodon4j.model.Relationship;
import com.github.k24.mastodon4j.model.Status;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import javax.annotation.Nonnull;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#accounts
 * <p>
 * Created by k24 on 2017/04/21.
 */
public interface AccountsApi {
    @GET("accounts/{id}")
    Deferred.Promise<Account> account(@Path("id") long id);

    @GET("accounts/verify_credentials")
    Deferred.Promise<Account> verifyCredentials();

    @FormUrlEncoded
    @PATCH("accounts/update_credentials")
    Deferred.Promise<Account> updateCredentials(@FieldMap Map<String, Object> fields);

    @GET("accounts/{id}/followers")
    Deferred.Promise<List<Account>> followers(@Path("id") long id);

    @GET("accounts/{id}/followers")
    Deferred.Promise<List<Account>> followers(@Path("id") long id, @QueryMap Map<String, Object> queries);

    @GET("accounts/{id}/following")
    Deferred.Promise<List<Account>> following(@Path("id") long id);

    @GET("accounts/{id}/following")
    Deferred.Promise<List<Account>> following(@Path("id") long id, @QueryMap Map<String, Object> queries);

    @GET("accounts/{id}/statuses")
    Deferred.Promise<List<Status>> statuses(@Path("id") long id);

    @GET("accounts/{id}/statuses")
    Deferred.Promise<List<Status>> statuses(@Path("id") long id, @QueryMap Map<String, Object> queries);

    @POST("accounts/{id}/follow")
    Deferred.Promise<Relationship> follow(@Path("id") long id);

    @POST("accounts/{id}/unfollow")
    Deferred.Promise<Relationship> unfollow(@Path("id") long id);

    @POST("accounts/{id}/block")
    Deferred.Promise<Relationship> block(@Path("id") long id);

    @POST("accounts/{id}/unblock")
    Deferred.Promise<Relationship> unblock(@Path("id") long id);

    @POST("accounts/{id}/mute")
    Deferred.Promise<Relationship> mute(@Path("id") long id);

    @POST("accounts/{id}/unmute")
    Deferred.Promise<Relationship> unmute(@Path("id") long id);

    @GET("accounts/relationships")
    Deferred.Promise<List<Relationship>> relationships(@Query("id[]") List<Long> ids);

    @GET("accounts/search")
    Deferred.Promise<List<Account>> search(@Query("q") String q);

    @GET("accounts/search")
    Deferred.Promise<List<Account>> search(@QueryMap Map<String, Object> queries);

    /**
     * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#updating-the-current-user
     */
    class UpdateCredentialsFieldMap extends AbstractMap<String, Object> {

        private final Map<String, Object> map;

        UpdateCredentialsFieldMap(Builder builder) {
            map = builder.toMap();
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public Set<Entry<String, Object>> entrySet() {
            return map.entrySet();
        }

        public static class Builder {
            String display_name;
            String note;
            String avatar; // data:image/png;base64,...
            String header; // data:image/png;base64,...

            public Builder displayName(String displayName) {
                display_name = displayName;
                return this;
            }

            public Builder note(String note) {
                this.note = note;
                return this;
            }

            public Builder avatar(String avatar) {
                this.avatar = avatar;
                return this;
            }

            public Builder header(String header) {
                this.header = header;
                return this;
            }

            @Nonnull
            public Map<String, Object> toMap() {
                HashMap<String, Object> map = new HashMap<>();

                if (display_name != null) map.put("display_name", display_name);
                if (note != null) map.put("note", note);
                if (avatar != null) map.put("avatar", avatar);
                if (header != null) map.put("header", header);

                return map;
            }

            public UpdateCredentialsFieldMap build() {
                return new UpdateCredentialsFieldMap(this);
            }
        }
    }
}
