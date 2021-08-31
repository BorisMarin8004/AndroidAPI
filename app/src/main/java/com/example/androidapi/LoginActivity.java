package com.example.androidapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidapi.API.RetrofitClient;
import com.example.androidapi.DB.UserDAO;
import com.example.androidapi.DB.UserDB;
import com.example.androidapi.UtilClasses.DefaultUsers;
import com.example.androidapi.UtilClasses.IntentFactory;
import com.example.androidapi.UtilClasses.Post;
import com.example.androidapi.UtilClasses.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private Button loginBtn;
    private Button showUsersBtn;

    private UserDAO userDAO;
    private UserDB userDB;
    private RetrofitClient retrofit;
    private User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.UsernameField);
        passwordField = findViewById(R.id.PasswordField);
        loginBtn = findViewById(R.id.LoginBtn);
        showUsersBtn = findViewById(R.id.ShowUsersBtn);

        userDB = Room.databaseBuilder(getApplicationContext(), UserDB.class, UserDB.DB_NAME)
                .allowMainThreadQueries()
                .build();
        userDAO = userDB.getUserDAO();
        retrofit = RetrofitClient.getClient();

        setupDB(true, false);



        loginBtn.setOnClickListener(view -> {
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();

            List<User> usernameMatches = userDAO.getUsersByUsername(username);
            List<User> passwordMatches = userDAO.getUsersByPassword(password);

            for (User user : usernameMatches) {
                if (passwordMatches.contains(user)) {
                    activeUser = user;
                    startActivity(IntentFactory.getIntent(PostActivity.class, getApplicationContext(), activeUser));
                    break;
                }
            }
            if (usernameMatches.size() == 0 && passwordMatches.size() == 0){
                Toast.makeText(getApplicationContext(), "ERROR: Wrong username and password", Toast.LENGTH_LONG).show();
            } else if (usernameMatches.size() == 0) {
                Toast.makeText(getApplicationContext(), "ERROR: Wrong username", Toast.LENGTH_LONG).show();
            } else if (passwordMatches.size() == 0) {
                Toast.makeText(getApplicationContext(), "ERROR: Wrong password", Toast.LENGTH_LONG).show();
            }

        });

        showUsersBtn.setOnClickListener(view -> {
            startActivity(IntentFactory.getIntent(ViewUsersActivity.class, getApplicationContext(), activeUser));
        });
    }

    private void setupDB(boolean resetToDefault, boolean resetToAPI){
        if (resetToDefault || resetToAPI) {
            userDB.clearAllTables();
        }
        if (resetToDefault) {
            userDAO.insert(DefaultUsers.users);
            System.out.println(userDAO.getAllUsers());
        } else if (resetToAPI) {
            retrofit.getUsersCall().enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        userDAO.insert(response.body());
                        System.out.println(userDAO.getAllUsers());
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
    }

    public static Intent getIntent(Context context, User activeUser) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("acitveUser", activeUser);
        return intent;
    }
}