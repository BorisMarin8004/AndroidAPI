package com.example.androidapi.API;

import com.example.androidapi.UtilClasses.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("users/")
    Call<List<User>> getUsers();

}
