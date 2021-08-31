package com.example.androidapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidapi.API.RetrofitClient;
import com.example.androidapi.UtilClasses.Post;
import com.example.androidapi.UtilClasses.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {

    private ListView listPosts;

    private User activeUser;
    private RetrofitClient retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        listPosts = findViewById(R.id.ListPosts);

        activeUser = getIntent().getParcelableExtra("activeUser");
        retrofit = RetrofitClient.getClient();
        retrofit.getPostsByUserIdCall(activeUser.getId()).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    ArrayAdapter<Post> postAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_post_item, response.body());
                    listPosts.setAdapter(postAdapter);
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

    public static Intent getIntent(Context context, User activeUser) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra("activeUser", (Parcelable) activeUser);
        return intent;
    }
}