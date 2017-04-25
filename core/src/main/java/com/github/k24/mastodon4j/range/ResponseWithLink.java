package com.github.k24.mastodon4j.range;

import com.github.k24.deferred.Deferred;
import okhttp3.Headers;
import retrofit2.Response;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by k24 on 2017/04/22.
 */
public class ResponseWithLink<T> {
    //    Link: <https://mstdn.hokkaido.jp/api/v1/timelines/home?max_id=100794>; rel="next", <https://mstdn.hokkaido.jp/api/v1/timelines/home?since_id=121039>; rel="prev"
    private final Response<T> response;
    @Nonnull
    private final Map<String, String> links;

    public ResponseWithLink(Response<T> response, @Nonnull Map<String, String> links) {
        this.response = response;
        this.links = links;
    }

    public Response<T> response() {
        return response;
    }

    @Nonnull
    public Map<String, String> links() {
        return links;
    }

    public String anchor(LinkRel rel) {
        return links.get(rel.toString());
    }

    @Nonnull
    public static <T> ResponseWithLink<T> from(Response<T> response) {
        Headers headers = response.headers();
        String link = headers.get("Link");
        return new ResponseWithLink<>(response, parseLink(link));
    }

    @Nonnull
    public static <T> Deferred.OnResolved<Response<T>, ResponseWithLink<T>> map() {
        return new Deferred.OnResolved<Response<T>, ResponseWithLink<T>>() {
            @Override
            public ResponseWithLink<T> onResolved(Response<T> tResponse) throws Exception {
                return from(tResponse);
            }
        };
    }

    @Nonnull
    public static Map<String, String> parseLink(String link) {
        try {
            String[] splits = link.split(",");
            Pattern pattern = Pattern.compile("<([^>]+)>; rel=\"(\\w+)\"");
            HashMap<String, String> map = new HashMap<>();

            for (String split : splits) {
                Matcher matcher = pattern.matcher(split.trim());
                if (matcher.matches()) {
                    map.put(matcher.group(2), matcher.group(1));
                }
            }

            return map;
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }

    public enum LinkRel {
        FIRST,
        PREV,
        NEXT,
        LAST;

        @Override
        public String toString() {
            return name().toLowerCase(Locale.US);
        }
    }
}
