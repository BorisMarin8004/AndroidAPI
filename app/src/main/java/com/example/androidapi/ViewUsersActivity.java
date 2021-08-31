package com.example.androidapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidapi.DB.UserDAO;
import com.example.androidapi.DB.UserDB;
import com.example.androidapi.UtilClasses.Post;
import com.example.androidapi.UtilClasses.User;

public class ViewUsersActivity extends AppCompatActivity {

    private ListView listViewUsers;

    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        listViewUsers = findViewById(R.id.ListUsers);

        userDAO = Room.databaseBuilder(getApplicationContext(), UserDB.class, UserDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();

        ArrayAdapter<User> userAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, userDAO.getAllUsers());
        listViewUsers.setAdapter(userAdapter);
    }

    public static Intent getIntent(Context context, User activeUser) {
        Intent intent = new Intent(context, ViewUsersActivity.class);
        intent.putExtra("acitveUser", (Parcelable) activeUser);
        return intent;
    }
}