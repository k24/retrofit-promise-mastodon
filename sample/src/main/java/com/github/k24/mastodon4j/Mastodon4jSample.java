package com.github.k24.mastodon4j;

import com.github.k24.deferred.Deferred;
import com.github.k24.deferred.RxJava2DeferredFactory;
import com.github.k24.mastodon4j.api.AppsApi;
import com.github.k24.mastodon4j.model.App;
import com.github.k24.mastodon4j.model.Status;
import com.github.k24.mastodon4j.range.ResponseWithLink;
import com.github.k24.retrofit2.converter.jsonic.JsonicConverterFactory;
import com.github.k24.retrofit2.converter.success.Success;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthAsyncRequestCallback;
import io.reactivex.schedulers.Schedulers;
import jline.console.ConsoleReader;
import net.arnx.jsonic.JSON;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by k24 on 2017/04/22.
 */
public class Mastodon4jSample {
    private final String host;
    private final RxJava2DeferredFactory deferredFactory = new RxJava2DeferredFactory(Schedulers.io());
    private final boolean dryrun;

    public Mastodon4jSample(String host, boolean dryrun) {
        this.host = host == null ? MastodonApiAgent.BuildConfig.URL_HOST_DEFAULT : host;
        this.dryrun = dryrun;
    }

    public static void main(String[] args) {
        String host = System.getenv("MSTDN_API_HOST");
        if (args.length < 1) {
            usage();
            return;
        }
        int argIndex = 0;
        String action;
        String first = args[argIndex++];
        if (first.startsWith("--")) {
            String[] splits = first.split("=");
            switch (splits[0].substring(2)) {
                case "host":
                    if (args.length < 2) {
                        usage();
                        return;
                    }
                    host = splits[argIndex++];
                    action = args[1];
                    break;
                default:
                    usage();
                    return;
            }
        } else {
            action = first;
        }

        Mastodon4jSample sample = new Mastodon4jSample(host, false);
        try {
            switch (action) {
                case "register":
                    sample.registerApp(getArgOrEnv(args, argIndex++, "MSTDN_API_CLIENT_NAME"),
                            getArgOrEnv(args, argIndex++, "MSTDN_API_USERNAME"),
                            getArgOrEnv(args, argIndex, "MSTDN_API_PASSWORD"));
                    break;
                case "authorize":
                    String username = getArgOrEnv(args, argIndex++, "MSTDN_API_USERNAME");
                    String password = getArgOrEnv(args, argIndex++, "MSTDN_API_PASSWORD");
                    sample.authorize(createApp(getArgOrEnv(args, argIndex++, "MSTDN_API_CLIENT_ID"),
                            getArgOrEnv(args, argIndex++, "MSTDN_API_CLIENT_SECRET"),
                            getArgOrEnv(args, argIndex, "MSTDN_API_REDIRECT_URI")),
                            username, password);
                    break;
                case "timeline":
                    sample.timeline(getArgOrEnv(args, argIndex, "MSTDN_API_ACCESS_TOKEN"));
                    break;
                default:
                    usage();
            }
        } catch (IllegalArgumentException e) {
            usage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerApp(String clientName, final String username, String password) throws Exception {
        if (clientName == null) throw new IllegalArgumentException();

        final AtomicReference<String> passwordActual = new AtomicReference<>();
        if (username != null && !username.isEmpty()) {
            if (password == null) {
                try (ConsoleReader consoleReader = new ConsoleReader()) {
                    password = consoleReader.readLine("Password:", ' ');
                    if (password == null || password.isEmpty()) throw new IllegalArgumentException();
                }
            }
            passwordActual.set(password);
        }

        if (dryrun) {
            log("registerApp(" + clientName + "," + username + "," + password + ")");
            return;
        }

        MastodonLoggingApiAgent apiAgent = apiAgent(null);

        apiAgent.apps()
                .postApp(clientName, AppsApi.AppFieldMap.REDIRECT_URI_NONE, App.Scope.toString(Collections.singletonList(App.Scope.READ)))
                .then(new Deferred.OnResolvedPromise<App, Success>() {
                    @Override
                    public Deferred.Promise<Success> onResolved(App app) throws Exception {
                        log(JSON.encode(app, true));
                        if (username != null && passwordActual.get() != null) {
                            return deferredFactory.deferred().resolved(Success.SUCCESS);
                        }
                        return getAccessToken(app, username, passwordActual.get())
                                .then(new Deferred.OnResolved<OAuth2AccessToken, Success>() {
                                    @Override
                                    public Success onResolved(OAuth2AccessToken oAuth2AccessToken) throws Exception {
                                        log(JSON.encode(oAuth2AccessToken, true));
                                        return Success.SUCCESS;
                                    }
                                });
                    }
                })
                .waitAndGet();
    }

    public static App createApp(String clientId, String clientSecret, String redirectUri) {
        if (clientId == null) throw new IllegalArgumentException();
        if (clientSecret == null) throw new IllegalArgumentException();

        App app = new App();
        app.client_id = clientId;
        app.client_secret = clientSecret;
        app.redirect_uri = redirectUri == null ? AppsApi.AppFieldMap.REDIRECT_URI_NONE : redirectUri;

        return app;
    }

    public void authorize(App app, String username, String password) throws Exception {
        if (username == null || username.isEmpty()) throw new IllegalArgumentException();
        if (password == null) {
            try (ConsoleReader consoleReader = new ConsoleReader()) {
                password = consoleReader.readLine("Password: ", ' ');
                if (password == null || password.isEmpty()) throw new IllegalArgumentException();
            }
        }

        if (dryrun) {
            log("authorize(" + JSON.encode(app, true) + "," + username + "," + password + ")");
            return;
        }

        getAccessToken(app, username, password)
                .then(new Deferred.OnResolved<OAuth2AccessToken, Success>() {
                    @Override
                    public Success onResolved(OAuth2AccessToken oAuth2AccessToken) throws Exception {
                        log(JSON.encode(oAuth2AccessToken, true));
                        return Success.SUCCESS;
                    }
                })
                .waitAndGet();
    }

    private Deferred.Promise<OAuth2AccessToken> getAccessToken(App app, String username, String password) {
        final Deferred.DeferredPromise<OAuth2AccessToken> deferred = deferredFactory.deferred().promise();
        new MastodonOAuthLoggingServiceFactory(host, HttpLoggingInterceptor.Level.BASIC)
                .create(app)
                .getAccessTokenPasswordGrantAsync(username, password, new OAuthAsyncRequestCallback<OAuth2AccessToken>() {
                    @Override
                    public void onCompleted(OAuth2AccessToken oAuth2AccessToken) {
                        deferred.resolve(oAuth2AccessToken);
                    }

                    @Override
                    public void onThrowable(Throwable throwable) {
                        deferred.reject(throwable);
                    }
                });
        return deferred;
    }

    public void timeline(String accessToken) throws Exception {
        if (accessToken == null) throw new IllegalArgumentException();

        if (dryrun) {
            log("timeline(" + accessToken + ")");
            return;
        }

        MastodonLoggingApiAgent apiAgent = apiAgent(accessToken);

        apiAgent.timelines()
                .homeTimeLine()
                .then(ResponseWithLink.<List<Status>>map())
                .then(new Deferred.OnResolved<ResponseWithLink<List<Status>>, Success>() {
                    @Override
                    public Success onResolved(ResponseWithLink<List<Status>> listResponseWithLink) throws Exception {
                        log("links: " + JSON.encode(listResponseWithLink.links(), true));
                        log("--------");
                        log(JSON.encode(listResponseWithLink.response().body(), true));
                        return Success.SUCCESS;
                    }
                })
                .waitAndGet();
    }

    private MastodonLoggingApiAgent apiAgent(String accessToken) {
        return MastodonLoggingApiAgent.create(accessToken, new MastodonApiAgent.Config.Builder()
                .converterFactory(JsonicConverterFactory.create())
                .deferredFactory(deferredFactory)
                .baseUrl(host == null ? null : MastodonLoggingApiAgent.buildBaseUrl(host))
                .build(), HttpLoggingInterceptor.Level.HEADERS);
    }

    private static String getArgOrEnv(String[] args, int index, String key) {
        return args.length <= index ? System.getenv(key) : args[index];
    }

    private static void usage() {
        log("./gradlew sample:run [--offline] -Pargs=\"[--host=<MSTDN_API_HOST> ]register|authorize|timeline[ <MSTDN_API_CLIENT_NAME|MSTDN_API_ACCESS_TOKEN>][ <MSTDN_API_USERNAME> [<MSTDN_API_PASSWORD>]][ <MSTDN_API_CLIENT_ID> <MSTDN_API_CLIENT_SECRET>[ <MSTDN_API_REDIRECT_URI>]]\"");
    }

    private static void log(String message) {
        System.out.println(message);
    }
}
