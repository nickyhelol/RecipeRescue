package com.nickhe.reciperescue.Controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.nickhe.reciperescue.R;

public class MainMenuActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //new RecipeRepository();
        initialize();
        validate();
        setBottomNavigationViewListener();
    }

    /**
     * Set ActionListener for the BottomNavigationView
     */
    private void setBottomNavigationViewListener()
    {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_navigation: {
                        setFragment(new HomeFragment());
                        return true;
                    }
                    case R.id.search_navigation: {
                        setFragment(new SearchFragment());
                        return true;
                    }
                    case R.id.profile_navigation: {
                        setFragment(new ProfileFragment());
                        return true;
                    }
                    case R.id.shoppingList_navigation: {
                        setFragment(new ShoppingListFragment());
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * Initialize views and fragments
     */
    private void initialize(){
        firebaseAuth = FirebaseAuth.getInstance();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    /**
     * Check if the intent extra is true or false.
     * If it is true then set shopping list fragment,
     * otherwise set home fragment instead.
     */
    private void validate(){
        Intent i = getIntent();
        boolean check = i.getBooleanExtra("startShoppingListFragment", false);
        if(check){
            setFragment(new ShoppingListFragment());
            bottomNavigationView.setSelectedItemId(R.id.shoppingList_navigation);
        }else {
            setFragment(new HomeFragment());
            bottomNavigationView.setSelectedItemId(R.id.home_navigation);
        }
    }

    /**
     * Return the bottomNavigationView
     *
     * @return
     */
    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    /**
     * Replace the specific fragment with the contentLayout
     *
     * @param fragment
     */
    public void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout, fragment).commit();
    }

    /**
     * This method will log out the user from the firebase and finish the current activity which is main menu activity and
     * goes back to the main login activity once log out menu is clicked.
     */
    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MainMenuActivity.this, MainLoginActivity.class));
    }

    /**
     * Enable the option menu in MainMenuActivity
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * The action when option selected in the optionMenu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenu:
                Logout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
