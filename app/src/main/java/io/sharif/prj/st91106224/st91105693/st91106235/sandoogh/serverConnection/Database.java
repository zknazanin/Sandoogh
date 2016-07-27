package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.serverConnection;


import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.App;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.UserPayment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.Admin.ConfirmPayment;

public class Database {

    static Database database = new Database();

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    static final String SANDOOGH_ARRAY = "sandooghs";
    static final String USER_ARRAY = "Users";
    static final String PAYMENT_LIST = "paymentList";
    static final String USER_PAYMENT_LIST = "userPaymentList";
    static final String APPROVED = "approved";

    public static Database getInstance() {
        return database;
    }


    public FirebaseUser getCurrentFirebaseUser() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.getCurrentUser();
    }

    public String getCurrentUserID() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.getCurrentUser().getUid();
    }


    public void saveSandoogh(Sandoogh sandoogh) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(SANDOOGH_ARRAY);
        DatabaseReference sandooghReference = databaseReference.child(sandoogh.getName());
        sandooghReference.setValue(sandoogh);
    }

    public void updateSandooghPayments(Sandoogh sandoogh) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(SANDOOGH_ARRAY);
        DatabaseReference sandooghReference = databaseReference.child(sandoogh.getName()).child(PAYMENT_LIST);
        sandooghReference.setValue(sandoogh.getPaymentList());
    }

    public void saveUser(User user) {
        DatabaseReference databaseReference = firebaseDatabase.getReference(USER_ARRAY);
        DatabaseReference sandooghReference = databaseReference.child(user.getId());
        sandooghReference.setValue(user);
    }

    public void saveConfirmedPayment(Sandoogh sandoogh, ConfirmPayment confirmPayment) {
        sandoogh.getPaymentList().get(confirmPayment.getPaymentListIndex()).getUserPaymentList()
                .get(confirmPayment.getUserPaymentListIndex()).setApproved(true);
        updateSandooghPayments(sandoogh);
    }
}
