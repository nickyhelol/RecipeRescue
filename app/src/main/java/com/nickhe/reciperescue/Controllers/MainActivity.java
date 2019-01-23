package com.nickhe.reciperescue.Controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nickhe.reciperescue.R;
import com.nickhe.reciperescue.Models.RecipeRepository;

/**
 * MainActivity is the menu the user would see after they started the application. It should have two
 * options:
 * a. Login button for logging in
 * b. Sign Up button for new users
 * <p>
 * Users are required to log in before accessing other features of the application.
 */
public class MainActivity extends AppCompatActivity {

    private RecipeRepository recipeRepository;
    private ProgressDialog progressDialog;

    /**
     * The login method starts the MainLoginActivity activity and switches to it.
     *
     * @param view
     */
    public void login(View view) {
        startActivity(new Intent(getApplicationContext(), MainLoginActivity.class));
    }

    /**
     * The signUp method starts the RegisterToFirebaseActivity and switches to it.
     *
     * @param view
     */
    public void signUp(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterToFirebaseActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize(){
        recipeRepository = RecipeRepository.getRecipeRepository();
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progressDialog.dismiss();
            }
        };

        Handler pbCanceller = new Handler();
        pbCanceller.postDelayed(progressRunnable, 3000);
    }

}
