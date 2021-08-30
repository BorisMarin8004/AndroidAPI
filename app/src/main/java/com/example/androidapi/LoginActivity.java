package com.example.androidapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidapi.API.RetrofitClient;
import com.example.androidapi.DB.UserDAO;
import com.example.androidapi.DB.UserDB;
import com.example.androidapi.UtilClasses.DefaultUsers;
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
        setupDB(true);

        loginBtn.setOnClickListener(view -> {
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();

            Toast.makeText(getApplicationContext(), userDAO.getAllUsers().toString(), Toast.LENGTH_LONG).show();

            List<User> usernameMatches = userDAO.getUsersByUsername(username);
            List<User> passwordMatches = userDAO.getUsersByPassword(password);

            System.out.println(usernameMatches);
            System.out.println(passwordMatches);

            for (User user : usernameMatches) {
                if (passwordMatches.contains(user)) {
                    activeUser = user;
//                  TODO: go to intent
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
//          TODO: go to intent
        });
    }

    private void setupDB(boolean resetToDefault){
        if (resetToDefault){
            userDB.clearAllTables();
        }
        if (userDAO.getAllUsers().size() == 0){
            userDAO.insert(DefaultUsers.users);
        }
    }
}