package com.github.k24.mastodon4j.model;

import java.util.List;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#context
 * <p>
 * Created by k24 on 2017/04/21.
 */
public class Context {
    /**
     * The ancestors of the status in the conversation, as a list of Statuses
     */
    public List<Status> ancestors;
    /**
     * The descendants of the status in the conversation, as a list of Statuses
     */
    public List<Status> descendants;
}
