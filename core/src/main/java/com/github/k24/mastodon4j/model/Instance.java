package com.github.k24.mastodon4j.model;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#instance
 * <p>
 * Created by k24 on 2017/04/25.
 */
public class Instance {
    /**
     * URI of the current instance
     */
    public String uri;
    /**
     * The instance's title
     */
    public String title;
    /**
     * A description for the instance
     */
    public String description;
    /**
     * An email address which can be used to contact the instance administrator
     */
    public String email;
}
