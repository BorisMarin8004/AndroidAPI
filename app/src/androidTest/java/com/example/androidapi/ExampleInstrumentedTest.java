package com.example.androidapi;

import android.content.Context;
import android.content.Intent;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.androidapi.DB.UserDAO;
import com.example.androidapi.DB.UserDB;
import com.example.androidapi.UtilClasses.DefaultUsers;
import com.example.androidapi.DataClasses.User;
import com.example.androidapi.UtilClasses.IntentFactory;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    /**
     * Tests if data on Room DB can be accessed and modified, by using DAO object and relevant methods
     */
    @Test
    public void testDB() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserDB userDB = Room.databaseBuilder(appContext, UserDB.class, UserDB.DB_NAME)
                .allowMainThreadQueries()
                .build();
        UserDAO userDAO = userDB.getUserDAO();

        userDB.clearAllTables();

        userDAO.insert(DefaultUsers.users);
        List<User> allUsers = userDAO.getAllUsers();
        assertEquals(allUsers, DefaultUsers.users);

        userDB.clearAllTables();

        allUsers = userDAO.getAllUsers();
        assertEquals(0, allUsers.size());
    }

    /**
     * Tests if login works correctly and returns correct massage
     */
    @Test
    public void testLogin() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserDB userDB = Room.databaseBuilder(appContext, UserDB.class, UserDB.DB_NAME)
                .allowMainThreadQueries()
                .build();
        UserDAO userDAO = userDB.getUserDAO();

        userDB.clearAllTables();
        userDAO.insert(DefaultUsers.users);

        User badUserAll = new User(1, "badUser", "badUser");
        User badUserUsername = new User(2, "badUser", "passuser1");
        User badUserPassword = new User(3, "user1", "badUser");

        assertEquals(1, loginUser(badUserAll, userDAO));
        assertEquals(2, loginUser(badUserUsername, userDAO));
        assertEquals(3, loginUser(badUserPassword, userDAO));

        for (User user : DefaultUsers.users) {
            assertEquals(0, loginUser(user, userDAO));
        }

        userDB.clearAllTables();
    }

    @Test
    public void testFactoryIntent(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = IntentFactory.getIntent(PostActivity.class, appContext, DefaultUsers.users.get(0));
        assertEquals(DefaultUsers.users.get(0), intent.getParcelableExtra("activeUser"));

        IntentFactory.getIntent(LoginActivity.class, appContext, DefaultUsers.users.get(0));
        assertEquals(DefaultUsers.users.get(0), intent.getParcelableExtra("activeUser"));

        IntentFactory.getIntent(ViewUsersActivity.class, appContext, DefaultUsers.users.get(0));
        assertEquals(DefaultUsers.users.get(0), intent.getParcelableExtra("activeUser"));
    }

    private static int loginUser(User user, UserDAO userDAO){
        List<User> usernameMatches = userDAO.getUsersByUsername(user.getUsername());
        List<User> passwordMatches = userDAO.getUsersByPassword(user.getPassword());
        for (User usernameMatch : usernameMatches) {
            if (passwordMatches.contains(usernameMatch)) {
//              SUCCESS: User Logged in
                return 0;
            }
        }
        if (usernameMatches.size() == 0 && passwordMatches.size() == 0){
//          ERROR: Wrong username and password
            return 1;
        } else if (usernameMatches.size() == 0) {
//          ERROR: Wrong username
            return 2;
        } else if (passwordMatches.size() == 0){
//          ERROR: Wrong password
            return 3;
        }
        return -1;
    }


}