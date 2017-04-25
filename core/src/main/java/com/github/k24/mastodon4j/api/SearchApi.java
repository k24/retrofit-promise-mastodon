package com.github.k24.mastodon4j.api;

import com.github.k24.deferred.Deferred;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.GET;

import java.util.List;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#search
 * <p>
 * Created by k24 on 2017/04/25.
 */
public interface SearchApi {
    @GET("search")
    Deferred.Promise<Response<List<Deferred.Result>>> search(@Field("q") String q, @Field("resolve") boolean resolve);
}
