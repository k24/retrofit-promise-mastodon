package com.github.k24.mastodon4j.model;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#attachment
 * <p>
 * Created by k24 on 2017/04/25.
 */
public class Attachment {
    /**
     * ID of the attachment
     */
    public long id;
    /**
     * One of: "image", "video", "gifv"
     */
    public String type;
    /**
     * URL of the locally hosted version of the image
     */
    public String url;
    /**
     * For remote images, the remote URL of the original image
     */
    public String remote_url;
    /**
     * URL of the preview image
     */
    public String preview_url;
    /**
     * Shorter URL for the image, for insertion into text (only present on local images)
     */
    public String text_url;
}
