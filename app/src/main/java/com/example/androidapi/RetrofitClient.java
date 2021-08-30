package com.example.androidapi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.androidapi.API.BASE_URL;

public class RetrofitClient {

    private API api;
    private static RetrofitClient retrofitClient;

    private Call<List<User>> getUsers;

    private RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);

        getUsers = api.getUsers();
    }

    public static RetrofitClient getClient() {
        if (retrofitClient == null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public Call<List<User>> getUsersCall(){
        return getUsers;
    }

}
