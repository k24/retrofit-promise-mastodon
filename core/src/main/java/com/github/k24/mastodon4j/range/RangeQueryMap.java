package com.github.k24.mastodon4j.range;

import javax.annotation.Nonnull;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#selecting-ranges
 * <p>
 * Created by k24 on 2017/04/21.
 */
public class RangeQueryMap extends AbstractMap<String, Object> {
    public static final RangeQueryMap NULL = new Builder().build();
    private final Map<String, Object> map;

    RangeQueryMap(Builder builder) {
        map = builder.toMap();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Set<Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    @SuppressWarnings("WeakerAccess")
    public static class Builder {
        Long max_id;
        Long since_id;

        public Builder maxId(Long maxId) {
            max_id = maxId;
            return this;
        }

        public Builder sinceId(Long sinceId) {
            since_id = sinceId;
            return this;
        }

        @Nonnull
        public Map<String, Object> toMap() {
            HashMap<String, Object> map = new HashMap<>();

            if (max_id != null) map.put("max_id", max_id);
            if (since_id != null) map.put("since_id", since_id);

            return map;
        }

        public RangeQueryMap build() {
            return new RangeQueryMap(this);
        }
    }

}
