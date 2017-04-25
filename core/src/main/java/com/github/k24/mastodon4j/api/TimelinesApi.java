package com.github.k24.mastodon4j.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.mastodon4j.model.Status;
import com.github.k24.mastodon4j.range.RangeQueryMap;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import javax.annotation.Nonnull;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#timelines
 * <p>
 * Created by k24 on 2017/04/20.
 */
public interface TimelinesApi {
    @GET("timelines/public")
    Deferred.Promise<Response<List<Status>>> publicTimeLine();

    @GET("timelines/public")
    Deferred.Promise<Response<List<Status>>> publicTimeLine(@QueryMap Map<String, Object> queries);

    @GET("timelines/home")
    Deferred.Promise<Response<List<Status>>> homeTimeLine();

    @GET("timelines/home")
    Deferred.Promise<Response<List<Status>>> homeTimeLine(@QueryMap Map<String, Object> queries);

    @GET("timelines/tag/{hashtag}")
    Deferred.Promise<Response<List<Status>>> hashtagTimeline(@Path("hashtag") String hashtag);

    @GET("timelines/tag/{hashtag}")
    Deferred.Promise<Response<List<Status>>> hashtagTimeline(@Path("hashtag") String hashtag, @QueryMap Map<String, Object> queries);

    /**
     * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#searching-for-accounts
     */
    class TimelineQueryMap extends AbstractMap<String, Object> {
        private final Map<String, Object> map;

        TimelineQueryMap(Builder builder) {
            map = builder.toMap();
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public Set<Entry<String, Object>> entrySet() {
            return map.entrySet();
        }

        public static class Builder extends RangeQueryMap.MapBuilder {
            Boolean local;

            public Builder local(Boolean local) {
                this.local = local;
                return this;
            }

            @Nonnull
            public Map<String, Object> toMap() {
                Map<String, Object> map = super.toMap();

                if (local != null) map.put("local", local);

                return map;
            }

            public TimelineQueryMap build() {
                return new TimelineQueryMap(this);
            }
        }
    }
}
