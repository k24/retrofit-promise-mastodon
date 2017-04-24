package com.github.k24.mastodon4j;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Created by k24 on 2017/04/24.
 */
class SslForLetsEncrypt {
    public final SSLSocketFactory sslSocketFactory;
    public final X509TrustManager trustManager;

    private SslForLetsEncrypt(SSLSocketFactory sslSocketFactory, X509TrustManager trustManager) {
        this.sslSocketFactory = sslSocketFactory;
        this.trustManager = trustManager;
    }

    public static SslForLetsEncrypt create() throws Exception {
        // http://stackoverflow.com/a/34111150
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        Path ksPath = Paths.get(System.getProperty("java.home"),
                "lib", "security", "cacerts");
        keyStore.load(Files.newInputStream(ksPath),
                "changeit".toCharArray());

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        try (InputStream caInput = new BufferedInputStream(
                // this files is shipped with the application
                MastodonLoggingApiAgent.class.getResourceAsStream("/DSTRootCAX3.cer"))) {
            Certificate crt = cf.generateCertificate(caInput);
            System.out.println("Added Cert for " + ((X509Certificate) crt)
                    .getSubjectDN());

            keyStore.setCertificateEntry("DSTRootCAX3", crt);
        }

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        SSLContext.setDefault(sslContext);

        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        for (TrustManager trustManager : trustManagerFactory.getTrustManagers()) {
            if (trustManager instanceof X509TrustManager) {
                return new SslForLetsEncrypt(sslSocketFactory, (X509TrustManager) trustManager);
            }
        }
        throw new IllegalStateException();
    }
}
