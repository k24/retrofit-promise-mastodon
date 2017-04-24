package com.github.k24.mastodon4j.model;

import java.util.List;
import java.util.Locale;

/**
 * Created by k24 on 2017/04/21.
 */
public class App {
    public enum Scope {
        READ,
        WEITE,
        FOLLOW;

        public static String toString(List<Scope> scopes) {
            if (scopes == null || scopes.isEmpty()) return "";
            StringBuilder buf = new StringBuilder();
            for (Scope scope : scopes) {
                buf.append(" ");
                buf.append(scope.name().toLowerCase(Locale.US));
            }
            return buf.substring(1);
        }
    }

    public long id;
    public String client_id;
    public String client_secret;
    public String redirect_uri;
}
