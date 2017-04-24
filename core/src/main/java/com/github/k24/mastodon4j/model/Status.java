package com.github.k24.mastodon4j.model;

import java.util.Locale;

/**
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
    /*
    id	The ID of the status
uri	A Fediverse-unique resource ID
url	URL to the status page (can be remote)
account	The Account which posted the status
in_reply_to_id	null or the ID of the status it replies to
in_reply_to_account_id	null or the ID of the account it replies to
reblog	null or the reblogged Status
content	Body of the status; this will contain HTML (remote HTML already sanitized)
created_at	The time the status was created
reblogs_count	The number of reblogs for the status
favourites_count	The number of favourites for the status
reblogged	Whether the authenticated user has reblogged the status
favourited	Whether the authenticated user has favourited the status
sensitive	Whether media attachments should be hidden by default
spoiler_text	If not empty, warning text that should be displayed before the actual content
visibility	One of: public, unlisted, private, direct
media_attachments	An array of Attachments
mentions	An array of Mentions
tags	An array of Tags
application	Application from which the status was posted
     */
}
