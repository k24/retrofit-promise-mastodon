package com.github.k24.mastodon4j.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.mastodon4j.model.Instance;
import retrofit2.http.GET;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#instances
 * <p>
 * Created by k24 on 2017/04/25.
 */
public interface InstancesApi {
    @GET("instance")
    Deferred.Promise<Instance> instance();
}
