package com.github.k24.mastodon4j.model;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#status
 * <p>
 * Created by k24 on 2017/04/20.
 */
public class Status {
    public enum Visibility {
        PUBLIC,
        UNLISTED,
        PRIVATE,
        DIRECT;

        @Override
        public String toString() {
            return name().toLowerCase(Locale.US);
        }
    }

    /**
     * The ID of the status
     */
    public long id;
    /**
     * A Fediverse-unique resource ID
     */
    public String uri;
    /**
     * URL to the status page (can be remote)
     */
    public String url;
    /**
     * The Account which posted the status
     */
    public Account account;
    /**
     * null or the ID of the status it replies to
     */
    @Nullable
    public Long in_reply_to_id;
    /**
     * null or the ID of the account it replies to
     */
    @Nullable
    public Long in_reply_to_account_id;
    /**
     * null or the reblogged Status
     */
    @Nullable
    public Status reblog;
    /**
     * Body of the status; this will contain HTML (remote HTML already sanitized)
     */
    public String content;
    /**
     * The time the status was created
     */
    public String created_at;
    /**
     * The number of reblogs for the status
     */
    public int reblogs_count;
    /**
     * The number of favourites for the status
     */
    public int favourites_count;
    /**
     * Whether the authenticated user has reblogged the status
     */
    public boolean reblogged;
    /**
     * Whether the authenticated user has favourited the status
     */
    public boolean favourited;
    /**
     * Whether media attachments should be hidden by default
     */
    public boolean sensitive;
    /**
     * If not empty, warning text that should be displayed before the actual content
     */
    public String spoiler_text;
    /**
     * One of: public, unlisted, private, direct
     */
    public String visibility;
    /**
     * An array of Attachments
     */
    public List<Attachment> media_attachments;
    /**
     * An array of Mentions
     */
    public List<Mention> mentions;
    /**
     * An array of Tags
     */
    public List<Tag> tags;
    /**
     * Application from which the status was posted
     */
    public Application application;
}
