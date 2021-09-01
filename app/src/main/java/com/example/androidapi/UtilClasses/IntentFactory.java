package com.example.androidapi.UtilClasses;

import android.content.Context;
import android.content.Intent;

import com.example.androidapi.DataClasses.User;
import com.example.androidapi.LoginActivity;
import com.example.androidapi.PostActivity;
import com.example.androidapi.ViewUsersActivity;

import java.util.HashMap;

public class IntentFactory {

    public static Intent getIntent(Class<?> className, Context context, User activeUser) {
        HashMap<Class<?>, Intent> intentMap =  new HashMap<Class<?>, Intent>() {{
            put(PostActivity.class, PostActivity.getIntent(context, activeUser));
            put(LoginActivity.class, LoginActivity.getIntent(context, activeUser));
            put(ViewUsersActivity.class, ViewUsersActivity.getIntent(context, activeUser));
        }};
        return intentMap.get(className);
    }
}
