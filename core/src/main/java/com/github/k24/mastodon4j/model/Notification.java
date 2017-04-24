package com.github.k24.mastodon4j.model;

/**
 * Created by k24 on 2017/04/21.
 */
public class Notification {
    /**
     * The notification ID
     */
    public long id;
    /**
     * One of: "mention", "reblog", "favourite", "follow"
     */
    public String type;
    /**
     * The time the notification was created
     */
    public String created_at;
    /**
     * The Account sending the notification to the user
     */
    public Account account;
    /**
     * The Status associated with the notification, if applicable
     */
    public Status status;
}
