package com.nickhe.reciperescue.Models;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDataManager {

    private static User user;

    /**
     * This method creates the database reference per user and sends it to the firebase database.
     */
    public static void sendUserDataToDatabase(FirebaseAuth firebaseAuth) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        User user = new User();
        databaseReference.setValue(user);
    }

    /**
     * Retrieve the user from the database
     */
    public static void retrieveUser(FirebaseAuth firebaseAuth)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Update user Info when the data of the user changed
     *
     * @param firebaseAuth
     */
    public static void updateUserToFirebase(FirebaseAuth firebaseAuth){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.setValue(user);
    }

    public synchronized static User getUser() {
        if(user == null){

        }
        return user;
    }

    public static void setUser(User user) {
        UserDataManager.user = user;
    }
}
