package com.github.k24.mastodon4j.model;

/**
 * Created by k24 on 2017/04/25.
 */
public class Mention {
    /**
     * URL of user's profile (can be remote)
     */
    public String url;
    /**
     * The username of the account
     */
    public String username;
    /**
     * Equals username for local users, includes @domain for remote ones
     */
    public String acct;
    /**
     * Account ID
     */
    public long id;
}
