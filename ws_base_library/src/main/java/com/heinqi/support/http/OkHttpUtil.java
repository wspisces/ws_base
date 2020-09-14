package com.heinqi.support.http;


import com.heinqi.support.base.BaseApplication;
import com.heinqi.support.utils.SharePreferUtil;
import com.orhanobut.logger.Logger;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtil {
    public static final X509TrustManager trustAllCert = new X509TrustManager() {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    };
    private static final int DEFAULT_TIMEOUT = 30;

    /**
     * 拦截器,添加头部
     */
    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)        //设置超时时间
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                //添加日志拦截器
                .addInterceptor(new LogInterceptor()
                        .setLevel(LogInterceptor.Level.BASIC))
                .addInterceptor(chain -> {
                    String token = SharePreferUtil.getAccessToken();
//                    if (!token.isEmpty()) {
//                        token = "Bearer " + token;
//                    }
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Accept", "*/*")
                            .addHeader("Authorization", token)
                            //.addHeader(".AspNetCore.Culture",SharePreferUtil.getLanguage(BaseApplication.getInstance()))
                            .build();
                    return chain.proceed(request);
                })
                //               .sslSocketFactory(new SSLSocketFactoryCompat(trustAllCert), trustAllCert)
                .build();
        RxJavaPlugins.setErrorHandler(throwable -> Logger.e(throwable,"RxJavaError"));
        return httpClient;
    }


}
