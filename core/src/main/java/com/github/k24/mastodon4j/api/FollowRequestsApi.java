package com.github.k24.mastodon4j.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.mastodon4j.model.Account;
import com.github.k24.retrofit2.converter.success.Success;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#follow-requests
 * <p>
 * Created by k24 on 2017/04/25.
 */
public interface FollowRequestsApi {
    @GET("follow_requests")
    Deferred.Promise<Response<List<Account>>> followRequests();

    @GET("follow_requests")
    Deferred.Promise<Response<List<Account>>> followRequests(@QueryMap Map<String, Object> queries);

    @POST("follow_requests/{id}/authorize")
    Deferred.Promise<Success> authorize(@Path("id") long id);

    @POST("follow_requests/{id}/reject")
    Deferred.Promise<Success> reject(@Path("id") long id);
}
