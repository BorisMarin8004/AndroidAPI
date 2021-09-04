package com.example.androidapi.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.androidapi.DataClasses.User;
import com.example.androidapi.UtilClasses.DefaultUsers;

/**
 * Metadata on Room DB
 */
@Database(version = 1, entities = {User.class})
public abstract class UserDB extends RoomDatabase {
    public static final String DB_NAME = "UserDB";
    public static final String USER_TABLE = "USER_TABLE";
    public static Class<?> dbDataSource = DefaultUsers.class;

    public abstract UserDAO getUserDAO();
}
