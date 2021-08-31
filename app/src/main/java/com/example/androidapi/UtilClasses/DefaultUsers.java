package com.example.androidapi.UtilClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface DefaultUsers {
    List<User> users = new ArrayList<>(Arrays.asList(
            new User(1, "user1", "passuser1"),
            new User(2, "user2", "passuser2"),
            new User(3, "user3", "passuser3")
    ));
}
