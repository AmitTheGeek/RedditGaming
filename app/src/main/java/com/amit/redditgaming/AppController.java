package com.amit.redditgaming;

import android.app.Application;
import android.content.Context;

import com.amit.redditgaming.rest.RestApi;
import com.amit.redditgaming.rest.RestApiFactory;


public class AppController extends Application {

    private RestApi restApi;

    private static AppController get(Context context) {
        return (AppController) context.getApplicationContext();
    }

    public static AppController create(Context context) {
        return AppController.get(context);
    }

    public RestApi getRestApi() {
        if(restApi == null) {
            restApi = RestApiFactory.create();
        }
        return restApi;
    }

}
