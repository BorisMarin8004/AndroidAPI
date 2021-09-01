package com.example.androidapi.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidapi.DataClasses.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);

    @Insert
    void insert(List<User> users);

    @Update
    void update(List<User> users);

    @Query("SELECT * FROM " + UserDB.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + UserDB.USER_TABLE + " WHERE id = :id")
    User getUserById(int id);

    @Query("SELECT * FROM " + UserDB.USER_TABLE + " WHERE username = :username")
    List<User> getUsersByUsername(String username);

    @Query("SELECT * FROM " + UserDB.USER_TABLE + " WHERE password = :password")
    List<User> getUsersByPassword(String password);
}
