package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;


public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}