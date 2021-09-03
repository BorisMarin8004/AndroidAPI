package com.example.androidapi.API;

import com.example.androidapi.DataClasses.Post;
import com.example.androidapi.DataClasses.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API interface provides BASE_URL and routes that will be used by RetrofitClient to make requests to API
 */
public interface API {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("users/")
    Call<List<User>> getUsers();

    @GET("posts/")
    Call<List<Post>> getPostsByUserId(
            @Query("userId") int userId
    );

}
