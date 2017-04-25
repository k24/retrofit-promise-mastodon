package com.github.k24.mastodon4j.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.mastodon4j.model.Account;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#follows
 * <p>
 * Created by k24 on 2017/04/25.
 */
public interface FollowsApi {
    @FormUrlEncoded
    @POST("follows")
    Deferred.Promise<Account> follow(@Field("uri") String uri);
}
