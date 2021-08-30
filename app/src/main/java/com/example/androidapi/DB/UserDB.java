package com.example.androidapi.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.androidapi.UtilClasses.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDB extends RoomDatabase {
    public static final String DB_NAME = "UserDB";
    public static final String USER_TABLE = "USER_TABLE";

    public abstract UserDAO getUserDAO();
}
