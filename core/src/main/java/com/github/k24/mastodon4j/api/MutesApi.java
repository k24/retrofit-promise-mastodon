package com.github.k24.mastodon4j.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.mastodon4j.model.Account;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#mutes
 * <p>
 * Created by k24 on 2017/04/25.
 */
public interface MutesApi {
    @GET("mutes")
    Deferred.Promise<Response<List<Account>>> mutes();

    @GET("mutes")
    Deferred.Promise<Response<List<Account>>> mutes(@QueryMap Map<String, Object> queries);
}
