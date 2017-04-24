package com.github.k24.mastodon4j.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.mastodon4j.model.Account;
import com.github.k24.mastodon4j.model.Card;
import com.github.k24.mastodon4j.model.Context;
import com.github.k24.mastodon4j.model.Status;
import com.github.k24.retrofit2.converter.success.Success;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import javax.annotation.Nonnull;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#statuses
 * <p>
 * Created by k24 on 2017/04/21.
 */
public interface StatusesApi {
    @GET("statuses/{id}")
    Deferred.Promise<Status> status(@Path("id") Status statusId);

    @GET("statuses/{id}/context")
    Deferred.Promise<Context> context(@Path("id") long statusId);

    @GET("statuses/{id}/card")
    Deferred.Promise<Card> card(@Path("id") long statusId);

    @GET("statuses/{id}/reblogged_by")
    Deferred.Promise<List<Account>> rebloggedBy();

    @GET("statuses/{id}/reblogged_by")
    Deferred.Promise<List<Account>> rebloggedBy(@QueryMap Map<String, Object> queries);

    @GET("statuses/{id}/favourited_by")
    Deferred.Promise<List<Account>> favouritedBy();

    @GET("statuses/{id}/favourited_by")
    Deferred.Promise<List<Account>> favouritedBy(@QueryMap Map<String, Object> queries);

    @FormUrlEncoded
    @POST("/statuses")
    Deferred.Promise<Status> postStatus(@FieldMap Map<String, Object> fields);

    @DELETE("statuses/{id}")
    Deferred.Promise<Success> deleteStatus(@Path("id") Status statusId);

    @POST("statuses/{id}/reblog")
    Deferred.Promise<Status> reblog(@Path("id") long statusId);

    @POST("statuses/{id}/unreblog")
    Deferred.Promise<Status> unreblog(@Path("id") long statusId);

    @POST("statuses/{id}/favourite")
    Deferred.Promise<Status> favourite(@Path("id") long statusId);

    @POST("statuses/{id}/unfavourite")
    Deferred.Promise<Status> unfavourite(@Path("id") long statusId);

    /**
     * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#posting-a-new-status
     */
    class StatusFieldMap extends AbstractMap<String, Object> {
        private final Map<String, Object> map;

        StatusFieldMap(Builder builder) {
            map = builder.toMap();
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public Set<Entry<String, Object>> entrySet() {
            return map.entrySet();
        }

        @SuppressWarnings("WeakerAccess")
        public static class Builder {
            String status;
            Long in_reply_to_id;
            List<Long> media_ids;
            Boolean sensitive;
            Status.Visibility visibility;
            String spoiler_text;

            public Builder status(String status) {
                this.status = status;
                return this;
            }

            public Builder inReplyToId(Long inReplyToId) {
                in_reply_to_id = inReplyToId;
                return this;
            }

            public Builder mediaIds(List<Long> mediaIds) {
                media_ids = mediaIds;
                return this;
            }

            public Builder sensitive(Boolean sensitive) {
                this.sensitive = sensitive;
                return this;
            }

            public Builder visibility(Status.Visibility visibility) {
                this.visibility = visibility;
                return this;
            }

            public Builder spoilerText(String spoilerText) {
                spoiler_text = spoilerText;
                return this;
            }

            @Nonnull
            public Map<String, Object> toMap() {
                HashMap<String, Object> map = new HashMap<>();

                if (status != null) map.put("status", status);
                if (in_reply_to_id != null) map.put("in_reply_to_id", in_reply_to_id);
                if (media_ids != null) map.put("media_ids", media_ids);
                if (sensitive != null) map.put("sensitive", sensitive);
                if (visibility != null) map.put("visibility", visibility);
                if (spoiler_text != null) map.put("spoiler_text", spoiler_text);

                return map;
            }

            public StatusFieldMap build() {
                return new StatusFieldMap(this);
            }
        }
    }
}
