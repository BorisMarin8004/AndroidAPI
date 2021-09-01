package com.example.androidapi.API;

import com.example.androidapi.DataClasses.Post;
import com.example.androidapi.DataClasses.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.androidapi.API.API.BASE_URL;

public class RetrofitClient {

    private API api;
    private static RetrofitClient retrofitClient;

    private RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }

    public static RetrofitClient getClient() {
        if (retrofitClient == null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public Call<List<User>> getUsersCall(){
        return api.getUsers();
    }
    public Call<List<Post>> getPostsByUserIdCall(int userId) {
        return api.getPostsByUserId(userId);
    }

}
