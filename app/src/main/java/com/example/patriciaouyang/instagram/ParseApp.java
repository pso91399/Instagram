package com.example.patriciaouyang.instagram;

import android.app.Application;

import com.example.patriciaouyang.instagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();


        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("patricia-ouyang")
                .clientKey("zerxis-indigoblue-798031")
                .server("http://patriciaouyang-fbu-instagram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
