package com.rit.tcs.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    public static String Baseurl = "";
    private static RetrofitClient mInstance;
    private static RetrofitClient mInstanceWithHeader;
    private static Retrofit mRetrofit;
    final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
    OkHttpClient okHttpClient = null;
    Gson gson;

    private RetrofitClient() {
        okHttpClient = okHttpClientBuilder
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build();
        gson = new GsonBuilder()
                .setLenient()
                .create();
        if (Baseurl.equalsIgnoreCase("")) {
//            mRetrofit = new Retrofit.Builder()
//                    .baseUrl(NetworkUtility.BASEURL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
        } else {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Baseurl + "API/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }


    }

    private RetrofitClient(final String value) {
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Token", value).build();
                return chain.proceed(request);
            }
        });
        okHttpClient = okHttpClientBuilder
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build();
        gson = new GsonBuilder()
                .setLenient()
                .create();
        if (Baseurl.equalsIgnoreCase("")) {
//            mRetrofit = new Retrofit.Builder()
//                    .baseUrl(NetworkUtility.BASEURL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
        } else {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Baseurl + "API/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

    }

    public static String setBaseurl(String url) {
        Baseurl = url;
        return Baseurl;
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public static synchronized RetrofitClient getInstance(String value) {
//        if (mInstanceWithHeader == null) {
        mInstanceWithHeader = new RetrofitClient(value);
//        }
        return mInstanceWithHeader;
    }

    public Api getApi() {
        return mRetrofit.create(Api.class);
    }
}
