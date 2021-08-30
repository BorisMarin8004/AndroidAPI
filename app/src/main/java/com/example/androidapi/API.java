package com.example.androidapi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("users/")
    Call<List<User>> getUsers();

}
