package com.github.k24.mastodon4j.model;

/**
 * Created by k24 on 2017/04/21.
 */
public class Relationship {
    /**
     * Target account id
     */
    public long id;
    /**
     * Whether the user is currently following the account
     */
    public boolean following;
    /**
     * Whether the user is currently being followed by the account
     */
    public boolean followed_by;
    /**
     * Whether the user is currently blocking the account
     */
    public boolean blocking;
    /**
     * Whether the user is currently muting the account
     */
    public boolean muting;
    /**
     * Whether the user has requested to follow the account
     */
    public boolean requested;
}
