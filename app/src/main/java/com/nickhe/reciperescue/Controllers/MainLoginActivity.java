package com.nickhe.reciperescue.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nickhe.reciperescue.R;
import com.nickhe.reciperescue.Models.UserDataManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This activity is the main login activity where user can provide email and password to login into the application
 */
public class MainLoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button signInBtn;
    private TextView signUpTextView, forgotPasswordTextView, errorTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_login);
        initializeViews();
        setSignInBtnOnClickListener();
        setSignUpTextViewOnClickListener();
        setForgotPasswordTextViewOnClickListener();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(MainLoginActivity.this);

        checkCurrentUser();

    }

    /**
     * Set onclick function for forgot password text view
     */
    private void setForgotPasswordTextViewOnClickListener(){
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainLoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    /**
     * Set onclick listener for signUpTextView
     */
    private void setSignUpTextViewOnClickListener()
    {
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainLoginActivity.this, RegisterToFirebaseActivity.class));
            }
        });
    }

    /**
     * Providing onclick function for login button
     */
    private void setSignInBtnOnClickListener(){
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(emailEditText.getText().toString(), passwordEditText.getText().toString());
            }

        });
    }

    /**
     * This method will initialize all the variables and upon its call,
     * it will show all the views to the screen
     */
    private void initializeViews() {
        emailEditText = findViewById(R.id.emailEditText_signIn);
        passwordEditText = findViewById(R.id.passwordEditText_singIn);
        signInBtn = findViewById(R.id.signInBtn);
        signUpTextView = findViewById(R.id.signUpTextView);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        errorTextView = findViewById(R.id.errorTextView_signIn);
    }

    /**
     * Check if there is a user who already signed in
     */
    private void checkCurrentUser()
    {
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            //UserDataManager.sendUserDataToDatabase(firebaseAuth);
            UserDataManager.retrieveUser(firebaseAuth);
            System.out.println("Succesfully retrieved user info");
        }
    }

    /**
     * This method will validate if the given name and the password are valid and if they are valid then it will let
     * user to enter to new window otherwise it will send error message
     *
     * @param userEmail
     * @param userPassword
     */

    private void validate(String userEmail, String userPassword) {

        if (userEmail.isEmpty()) {
            errorTextView.setText("Email address required!");
        } else if (!isEmailValid(userEmail)) {
            errorTextView.setText("Please enter valid email address!");
        } else if (userPassword.isEmpty()) {
            errorTextView.setText("Password required!");
        } else if (!isPasswordValid(userPassword)) {
            errorTextView.setText("Password must be at least 6 character");
        } else {
            errorTextView.setText("");
            progressDialog.setMessage("Logging in");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        checkEmailVerification();
                    } else {
                        progressDialog.cancel();
                        errorTextView.setText("Wrong email address or password!");
                        Toast.makeText(MainLoginActivity.this, "Wrong user name or password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * This method will check the email provided by the user during loging in and if the email
     * is correct then it will let user enter to new window otherwise it will sign out from the firebase
     * and ask user to provide correct email address.
     */
    private void checkEmailVerification() {
        firebaseUser = firebaseAuth.getCurrentUser();
        Boolean flag = firebaseUser.isEmailVerified();

        if (flag) {
            //UserDataManager.sendUserDataToDatabase(firebaseAuth);
            UserDataManager.retrieveUser(firebaseAuth);
            startActivity(new Intent(MainLoginActivity.this, MainMenuActivity.class));
        } else {
            Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

    /**
     * This method check if the given email address is valid or not.
     *
     * @param email email address
     * @return true if the email is valid and false otherwise
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * This method checks if the given password is valid or not.
     *
     * @param password password
     * @return true if the password is valid and false otherwise
     */
    public static boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }


}
