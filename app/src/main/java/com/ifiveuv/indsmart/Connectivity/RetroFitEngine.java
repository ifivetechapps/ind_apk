package com.ifiveuv.indsmart.Connectivity;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitEngine {

    // public static String baseUrl = "http://ifive.sytes.net/";
    public static String baseUrl = "http://192.168.1.249/indsmart/";
    //public static String baseUrl = "http://ifiveapps.com/";
//   public static String baseUrl = "http://i5tech.sytes.net/";


    public static OkHttpClient getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        client.connectionPool().evictAll();
        return client;
    }

    public static Retrofit getRetrofit() {
        OkHttpClient client = getClient();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
