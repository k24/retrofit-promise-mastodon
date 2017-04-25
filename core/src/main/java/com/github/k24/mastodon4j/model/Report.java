package com.github.k24.mastodon4j.model;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#report
 * <p>
 * Created by k24 on 2017/04/21.
 */
public class Report {
    /**
     * The ID of the report
     */
    public long id;
    /**
     * The action taken in response to the report
     */
    public String action_taken;
}
