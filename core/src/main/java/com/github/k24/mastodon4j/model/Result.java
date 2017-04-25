package com.github.k24.mastodon4j.model;

import java.util.List;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#results
 * <p>
 * Created by k24 on 2017/04/25.
 */
public class Result {
    /**
     * An array of matched Accounts
     */
    public List<Account> accounts;
    /**
     * An array of matchhed Statuses
     */
    public List<Status> statuses;
    /**
     * An array of matched hashtags, as strings
     */
    public List<String> hashtags;
}
