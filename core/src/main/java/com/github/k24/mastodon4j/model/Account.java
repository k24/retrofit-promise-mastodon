package com.github.k24.mastodon4j.model;

/**
 * Created by k24 on 2017/04/21.
 */
public class Account {
    /**
     * The ID of the account
     */
    public long id;
    /**
     * The username of the account
     */
    public String username;
    /**
     * Equals username for local users, includes @domain for remote ones
     */
    public String acct;
    /**
     * The account's display name
     */
    public String display_name;
    /**
     * Boolean for when the account cannot be followed without waiting for approval first
     */
    public boolean locked;
    /**
     * The time the account was created
     */
    public String created_at;
    /**
     * The number of followers for the account
     */
    public int followers_count;
    /**
     * The number of accounts the given account is following
     */
    public int following_count;
    /**
     * The number of statuses the account has made
     */
    public int statuses_count;
    /**
     * Biography of user
     */
    public String note;
    /**
     * URL of the user's profile page (can be remote)
     */
    public String url;
    /**
     * URL to the avatar image
     */
    public String avatar;
    /**
     * URL to the avatar static image (gif)
     */
    public String avatar_static;
    /**
     * URL to the header image
     */
    public String header;
    /**
     * URL to the header static image (gif)
     */
    public String header_static;
}
