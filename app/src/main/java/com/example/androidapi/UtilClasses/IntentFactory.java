package com.example.androidapi.UtilClasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.androidapi.LoginActivity;
import com.example.androidapi.PostActivity;

import java.util.HashMap;

public class IntentFactory {

    public static Intent getIntent(Class<?> className, Context context, User activeUser) {
        HashMap<Class<?>, Intent> intentMap =  new HashMap<Class<?>, Intent>() {{
            put(PostActivity.class, PostActivity.getIntent(context, activeUser));
            put(LoginActivity.class, LoginActivity.getIntent(context, activeUser));
        }};
        return intentMap.get(className);
    }
}
