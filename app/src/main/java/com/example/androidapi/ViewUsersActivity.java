package com.example.androidapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.androidapi.API.RetrofitClient;
import com.example.androidapi.DB.UserDAO;
import com.example.androidapi.DB.UserDB;
import com.example.androidapi.UtilClasses.DefaultUsers;
import com.example.androidapi.UtilClasses.Post;
import com.example.androidapi.UtilClasses.User;
import com.example.androidapi.UtilClasses.UsersListViewAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUsersActivity extends AppCompatActivity {

    private static final String passwordChars = "abcdefghijklmnopqrstuvwxyz";
    private static final int passwordLength = 4;

    private ListView listViewUsers;
    private Button switchDataSourceBtn;

    private boolean APIDataSource;
    private UserDAO userDAO;
    private UserDB userDB;
    private RetrofitClient retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        APIDataSource = false;
        listViewUsers = findViewById(R.id.ListUsers);
        switchDataSourceBtn = findViewById(R.id.SwitchDataSourceBtn);
        setBtnText();

        userDB = Room.databaseBuilder(getApplicationContext(), UserDB.class, UserDB.DB_NAME)
                .allowMainThreadQueries()
                .build();
        userDAO = userDB.getUserDAO();
        setListView(userDAO.getAllUsers());

        retrofit = RetrofitClient.getClient();

        switchDataSourceBtn.setOnClickListener(view -> {
            if (APIDataSource){
                setDBToDataSource(DefaultUsers.users);
                setListView(userDAO.getAllUsers());
                APIDataSource = false;
            } else {
                retrofit.getUsersCall().enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            List<User> usersArrayList = new ArrayList<>(response.body());
                            for (User user : usersArrayList){
                                user.setPassword(getRandomStr(passwordChars, passwordLength));
                            }
                            setListView(usersArrayList);
                            setDBToDataSource(usersArrayList);
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
                APIDataSource = true;
            }
            setBtnText();
        });
    }

    private void setListView(List<User> dataSource) {
        UsersListViewAdapter userAdapter = new UsersListViewAdapter(getApplicationContext(), R.layout.list_item, dataSource);
        listViewUsers.setAdapter(userAdapter);
    }

    private void setBtnText(){
        if (APIDataSource){
            switchDataSourceBtn.setText(R.string.SwitchDBDataSourceBtn);
        } else {
            switchDataSourceBtn.setText(R.string.SwitchAPIDataSourceBtn);
        }
    }

    private String getRandomStr(String chars, int length){
        StringBuilder stringBuilder = new StringBuilder();
        Random rd = new Random();
        while (stringBuilder.length() < length) {
            int index = (int) (rd.nextFloat() * chars.length());
            stringBuilder.append(chars.charAt(index));
        }
        return stringBuilder.toString();
    }

    private void setDBToDataSource(List<User> usersList){
        userDB.clearAllTables();
        userDAO.insert(usersList);
    }

    public static Intent getIntent(Context context, User activeUser) {
        Intent intent = new Intent(context, ViewUsersActivity.class);
        intent.putExtra("acitveUser", activeUser);
        return intent;
    }
}