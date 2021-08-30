package com.example.androidapi.UtilClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface DefaultUsers {
    List<User> users = new ArrayList<>(Arrays.asList(
            new User("user1", "passuser1"),
            new User("user2", "passuser2"),
            new User("user3", "passuser3")
    ));
}
