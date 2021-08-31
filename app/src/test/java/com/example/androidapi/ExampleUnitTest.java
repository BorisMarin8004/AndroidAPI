package com.example.androidapi;

import androidx.room.Room;

import com.example.androidapi.API.RetrofitClient;
import com.example.androidapi.DB.UserDAO;
import com.example.androidapi.DB.UserDB;
import com.example.androidapi.UtilClasses.Post;
import com.example.androidapi.UtilClasses.User;

import org.junit.Test;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private List<User> api_response_body_users;
    private List<Post> api_response_body_posts;

    @Test
    public void testAPIGetAllUsers() {
        RetrofitClient retrofit = RetrofitClient.getClient();
        retrofit.getUsersCall().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    api_response_body_users = response.body();
                    assertTrue(api_response_body_users.size() > 0);
                } else {
                    new Exception("Request failed, code: " + response.code()).printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    @Test
    public void testAPIGetPostsByUserId() {
        Random random = new Random();
        int min = 1;
        int max = 10;
        int userId = random.nextInt((max - min) + 1) + min;
        RetrofitClient retrofit = RetrofitClient.getClient();
        retrofit.getPostsByUserIdCall(userId).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    api_response_body_posts = response.body();
                    assertTrue(api_response_body_posts.size() > 0);
                } else {
                    new Exception("Request failed, code: " + response.code()).printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}