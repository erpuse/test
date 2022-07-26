package com.erelmanagement.registerlogin.api;

import android.util.Base64;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class client {
    public static String  BASE_URL  = "http://192.168.137.1/test/registrasi/Api/";
    //public static String  BASE_URL  = "http://http://192.168.137.1//test/registrasi/Api/";
    //bisa di ganti dengan ip laptop

    public static Api getApi() {
        //Builder Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api apiService = retrofit.create(Api.class);

        return apiService;
    }


    public static Api getCredential(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(50, TimeUnit.SECONDS);
        httpClient.writeTimeout(50, TimeUnit.SECONDS);
        httpClient.readTimeout(50, TimeUnit.SECONDS);
        httpClient.addNetworkInterceptor(provideLoggingInterceptor());
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                String username = "Kaizen_management";
                String password = "Kaizen@RsbH2021**";
                String credentials = username + ":" + password;
                final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Authorization", basic)
                        .addHeader("X-Kaizen-Key", "4fsk74rhiwsD8");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api apiService = retrofit.create(Api.class);
        return apiService;
    }

    private static Interceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
