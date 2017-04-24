package com.github.k24.mastodon4j.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.mastodon4j.model.Status;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#timelines
 * <p>
 * Created by k24 on 2017/04/20.
 */
public interface TimelinesApi {
    @GET("timelines/public")
    Deferred.Promise<List<Status>> publicTimeLine();

    @GET("timelines/public")
    Deferred.Promise<List<Status>> publicTimeLine(@QueryMap Map<String, Object> queries);

    @GET("timelines/home")
    Deferred.Promise<List<Status>> homeTimeLine();

    @GET("timelines/home")
    Deferred.Promise<List<Status>> homeTimeLine(@QueryMap Map<String, Object> queries);

    @GET("timelines/tag/{hashtag}")
    Deferred.Promise<List<Status>> hashtagTimeline(@Path("hashtag") String hashtag);

    @GET("timelines/tag/{hashtag}")
    Deferred.Promise<List<Status>> hashtagTimeline(@Path("hashtag") String hashtag, @QueryMap Map<String, Object> queries);
}
