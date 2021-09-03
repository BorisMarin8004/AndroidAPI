package com.example.androidapi.UtilClasses;

import com.example.androidapi.DataClasses.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Default users that can be used other users from API
 */
public interface DefaultUsers {
    List<User> users = new ArrayList<>(Arrays.asList(
            new User(1, "user1", "passuser1"),
            new User(2, "user2", "passuser2"),
            new User(3, "user3", "passuser3")
    ));
}
