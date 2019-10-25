package com.rit.tcs.retrofit;

import android.app.Activity;
import android.os.Environment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rit.tcs.util.ConfigPreference;
import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

public class Apiclientmeridian  {

   // preference.getPreference("String","token")


    public static final String BASE_URL = "https://edit.meridianapps.com/api/locations/5979255142612992/";
    private static Retrofit retrofit = null;
public static String Baseurl="";
    public static String host="";
    public static String port="";

    public static Authenticator proxy=null;
 /*   public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();


        return retrofit;
    }*/

public  static String setBaseurl(String url){
    Baseurl=url;
    return Baseurl;
}
    public  static Authenticator setproxy(Authenticator prx){
        proxy=prx;
        return proxy;
    }
    public  static String sethost(String et_host){
        host=et_host;
        return host;
    }
    public  static String setport(String et_port){
        port=et_port;
        return port;
    }
    public static Retrofit getClientanother() {
        File httpCacheDirectory = new File(Environment.getExternalStorageDirectory(), "HttpCache");// Here to facilitate the file directly on the SD Kagan catalog HttpCache in ， Generally put in context.getCacheDir() in
        int cacheSize = 10 * 1024 * 1024;// Set cache file size 10M
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        OkHttpClient client = new OkHttpClient
                .Builder().retryOnConnectionFailure(true)
                .cache(cache) // 10 MB
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                       /* if (ConnectivityReceiver.isConnected()) {
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                        } else {
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                        }*/
                        request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();

                        return chain.proceed(request);
                    }
                }).connectTimeout(7000, TimeUnit.SECONDS)
                .readTimeout(7000, TimeUnit.SECONDS)

                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Baseurl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }



}
/*
 .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, Integer.parseInt(port))))
         .proxyAuthenticator(proxy)*/
