package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.serverConnection;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;

public class Database {

    static Database database = new Database();

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    static final String SANDOOGH_ARRAY = "sandooghs";
    static final String USER_ARRAY = "Users";

    public static Database getInstance() {
        return database;
    }

    public void saveSandoogh(Sandoogh sandoogh) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(SANDOOGH_ARRAY);
        DatabaseReference sandooghReference = databaseReference.child(sandoogh.getName());
        sandooghReference.setValue(sandoogh);
    }

    public void saveUser(User user) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(USER_ARRAY);
        DatabaseReference sandooghReference = databaseReference.child(user.getId());
        sandooghReference.setValue(user);
    }
}
