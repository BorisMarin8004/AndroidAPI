package com.example.androidapi;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.androidapi.DB.UserDAO;
import com.example.androidapi.DB.UserDB;
import com.example.androidapi.UtilClasses.DefaultUsers;
import com.example.androidapi.DataClasses.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

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

    @Test
    public void testLogin() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserDB userDB = Room.databaseBuilder(appContext, UserDB.class, UserDB.DB_NAME)
                .allowMainThreadQueries()
                .build();
        UserDAO userDAO = userDB.getUserDAO();

        userDB.clearAllTables();
        userDAO.insert(DefaultUsers.users);

        User badUserAll = new User("badUser", "badUser");
        User badUserUsername = new User("badUser", "passuser1");
        User badUserPassword = new User("user1", "badUser");

        assertEquals(1, loginUser(badUserAll, userDAO));
        assertEquals(2, loginUser(badUserUsername, userDAO));
        assertEquals(3, loginUser(badUserPassword, userDAO));

        for (User user : DefaultUsers.users) {
            assertEquals(0, loginUser(user, userDAO));
        }

        userDB.clearAllTables();
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