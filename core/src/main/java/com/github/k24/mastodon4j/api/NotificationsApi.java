package com.github.k24.mastodon4j.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.mastodon4j.model.Notification;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#notifications
 * <p>
 * Created by k24 on 2017/04/21.
 */
public interface NotificationsApi {
    @GET("notifications")
    Deferred.Promise<List<Notification>> notifications();

    @GET("notifications/{id}")
    Deferred.Promise<List<Notification>> notification(@Path("id") long id);

    @POST("notifications/clear")
    Deferred.Promise<List<Notification>> clearNotifications();
}
