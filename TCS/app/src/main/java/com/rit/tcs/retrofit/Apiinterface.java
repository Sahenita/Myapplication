package com.rit.tcs.retrofit;

import com.google.gson.JsonElement;
import com.rit.tcs.bussinessobject.GenericModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Apiinterface {
    @POST("connect.php")
    Call<GenericModel> getTopRatedMovies();

    @POST("login.php")
    Call<GenericModel> getlogindata();
}
