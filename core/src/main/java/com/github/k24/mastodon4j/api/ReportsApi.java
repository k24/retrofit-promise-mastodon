package com.github.k24.mastodon4j.api;

import com.github.k24.deferred.Deferred;
import com.github.k24.mastodon4j.model.Report;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import javax.annotation.Nonnull;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#reports
 * <p>
 * Created by k24 on 2017/04/21.
 */
public interface ReportsApi {
    @GET("reports")
    Deferred.Promise<List<Report>> reports();

    @GET("reports")
    Deferred.Promise<List<Report>> reports(@QueryMap Map<String, Object> queries);

    @FormUrlEncoded
    @POST("reports")
    Deferred.Promise<Report> report(@FieldMap Map<String, Object> fields);

    /**
     * https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#reporting-a-user
     */
    class ReportFieldMap extends AbstractMap<String, Object> {
        public static final ReportFieldMap EMPTY = new Builder().build();
        private final Map<String, Object> map;

        ReportFieldMap(Builder builder) {
            map = builder.toMap();
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public Set<Entry<String, Object>> entrySet() {
            return map.entrySet();
        }

        @SuppressWarnings("WeakerAccess")
        public static class Builder {
            Long account_id;
            List<Long> status_ids;
            String comment;

            public Builder accountId(Long accountId) {
                this.account_id = accountId;
                return this;
            }

            public Builder statusIds(List<Long> statusIds) {
                this.status_ids = statusIds;
                return this;
            }

            public Builder comment(String comment) {
                this.comment = comment;
                return this;
            }

            public ReportFieldMap build() {
                return new ReportFieldMap(this);
            }

            @Nonnull
            public Map<String, Object> toMap() {
                HashMap<String, Object> map = new HashMap<>();

                if (account_id != null) map.put("account_id", account_id);
                if (status_ids != null) map.put("status_ids", status_ids);
                if (comment != null) map.put("comment", comment);

                return map;
            }
        }
    }
}
