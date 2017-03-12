package io.mindjet.jetgear.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jet on 2/17/17.
 */

public class ServiceGen {
    private static Retrofit retrofit;
    private static String baseUrl = "";
    private static OkHttpClient client;
    private static String buildType = "";

    public static void init(String _baseUrl, String _buildType) {
        baseUrl = _baseUrl;
        buildType = _buildType;
        initClient();
        initRetrofit(_baseUrl);
    }

    private static void initClient() {
        JLoggerInterceptor interceptor = new JLoggerInterceptor(buildType.equals("debug") ? JLoggerInterceptor.BODY : JLoggerInterceptor.NONE);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    private static void initRetrofit(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static <T> T create(Class<T> clz) {
        if (baseUrl.equals("")) {
            throw new IllegalArgumentException("BaseUrl is null, do you forget to init the ServiceGen?");
        }
        return retrofit.create(clz);
    }
}
