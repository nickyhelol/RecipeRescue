package com.nickhe.reciperescue.Controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.nickhe.reciperescue.Models.ImageProcessor;
import com.nickhe.reciperescue.Models.IngredientListAdapter;
import com.nickhe.reciperescue.Models.ListViewProcessor;
import com.nickhe.reciperescue.R;
import com.nickhe.reciperescue.Models.Recipe;
import com.nickhe.reciperescue.Models.UserDataManager;

import java.util.ArrayList;


public class RecipeViewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Recipe recipe;
    ImageView recipeImage, shoppingCartImageView;
    TextView recipeTitle, publisherTextView, ingredientsTextView;
    TextView caloriesTextView, timeTextView, instructionTextView;
    ListView ingredientsListView;
    static TextView numberOfOrderTextView;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        receiveData();
        initializeView();

        updateView();
        initializeIngredientListView();
        initializeInstructionTextView();
        setShoppingCartImageViewOnClickListener();
    }

    /**
     * Update view based on the info of the recipe selected
     */
    private void updateView()
    {
        Bitmap image = ImageProcessor.convertUriToBitmap(this, recipe.getRecipeImage());
        recipeImage.setImageBitmap(image);
        recipeTitle.setText(recipe.getRecipeTitle());
        publisherTextView.setText(recipe.getRecipePublisher());
        ingredientsTextView.setText(String.valueOf(recipe.getRecipeIngredients().length));
        caloriesTextView.setText(recipe.getCalories());
        timeTextView.setText(recipe.getTime());
        numberOfOrderTextView.setText(String.valueOf(UserDataManager.getUser().getShoppingList().size()));
    }

    /**
     * Initialize views
     */
    private void initializeView(){
        recipeImage = findViewById(R.id.recipeImage_viewRecipeActivity);
        recipeTitle = findViewById(R.id.recipeTitleView);
        publisherTextView = findViewById(R.id.publisherTextView);
        ingredientsTextView = findViewById(R.id.ingredients_recipeViewActivity);
        caloriesTextView = findViewById(R.id.calorieTextView);
        timeTextView = findViewById(R.id.timeTextView);
        ingredientsListView = findViewById(R.id.ingredientListView);
        instructionTextView = findViewById(R.id.instructionsTextView);
        shoppingCartImageView = findViewById(R.id.shoppingCartImageView);
        numberOfOrderTextView = findViewById(R.id.numberOfOrdersTextView);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navDrawerView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Update numberOfOrderTextView state
     * @param list
     */
    public static void updateNumOfOrderTextView(ArrayList<String> list){
        numberOfOrderTextView.setText(String.valueOf(list.size()));
    }

    /**
     * Update user database
     */
    private void updateUserDb(){
        UserDataManager.updateUserToFirebase(FirebaseAuth.getInstance());
        mDrawerLayout.closeDrawer(navigationView);
    }

    /**
     * Initialize ingredientListView
     */
    private void initializeIngredientListView(){

        IngredientListAdapter ingredientListAdapter =
                new IngredientListAdapter(RecipeViewActivity.this, recipe.getRecipeIngredients(), recipe);
        ingredientsListView.setAdapter(ingredientListAdapter);
        ListViewProcessor.setListViewHeightBasedOnChildren(ingredientsListView);
    }

    /**
     * Initialize instructionTextView
     */
    private void initializeInstructionTextView(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<recipe.getRecipeInstruction().length;i++)
        {
            stringBuilder.append("Step"+(i+1)+": "+recipe.getRecipeInstruction()[i]+"\n\n");
        }

        instructionTextView.setText(stringBuilder.toString());
    }

    /**
     * RECEIVE DATA FROM FRAGMENT
     */
    private void receiveData() {
        Intent i = getIntent();
        recipe = i.getParcelableExtra("recipe");
    }

    /**
     * Set onClickListener for shoppingCartImageView
     */
    private void setShoppingCartImageViewOnClickListener(){
        shoppingCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
                i.putExtra("startShoppingListFragment", true);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.breakfastMenu: {
                saveRecipeToCollection(1);
                return true;
            }
            case R.id.sidesMenu: {
                saveRecipeToCollection(2);
                return true;
            }
            case R.id.dinnerMenu: {
                saveRecipeToCollection(3);
                return true;
            }
            case R.id.drinkMenu: {
                saveRecipeToCollection(4);
                return true;
            }
            case R.id.dessertMenu: {
                saveRecipeToCollection(5);
                return true;
            }
        }
        return true;
    }

    /**
     * Save recipe to the specific user's collection
     * @param i
     */
    private void saveRecipeToCollection(int i){
        switch (i){
            case 1:
                saveToBreakfasts();
                break;
            case 2:
                saveToSides();
                break;
            case 3:
                saveToDinners();
                break;
            case 4:
                saveToDrinks();
                break;
            case 5:
                saveToDesserts();
                break;
        }
        updateUserDb();
    }

    /**
     * Save recipe to breakfast collection
     */
    private void saveToBreakfasts(){
        if(UserDataManager.getUser().getBreakfastsRepo().contains(recipe.getRecipeTitle())){
            Toast.makeText(getApplicationContext(), "Already added in collection", Toast.LENGTH_SHORT).show();
        }else {
            UserDataManager.getUser().getBreakfastsRepo().add(recipe.getRecipeTitle());
            Toast.makeText(getApplicationContext(), "Successfully added!", Toast.LENGTH_SHORT).show();
        }
        UserDataManager.updateUserToFirebase(FirebaseAuth.getInstance());
    }

    /**
     * Save recipe to sides collection
     */
    private void saveToSides(){
        if(UserDataManager.getUser().getSidesRepo().contains(recipe.getRecipeTitle())){
            Toast.makeText(getApplicationContext(), "Already added in collection", Toast.LENGTH_SHORT).show();
        }else {
            UserDataManager.getUser().getSidesRepo().add(recipe.getRecipeTitle());
            Toast.makeText(getApplicationContext(), "Successfully added!", Toast.LENGTH_SHORT).show();
        }
        UserDataManager.updateUserToFirebase(FirebaseAuth.getInstance());
    }


    /**
     * Save recipe to dinners collection
     */
    private void saveToDinners(){
        if(UserDataManager.getUser().getDinnersRepo().contains(recipe.getRecipeTitle())){
            Toast.makeText(getApplicationContext(), "Already added in collection", Toast.LENGTH_SHORT).show();
        }else {
            UserDataManager.getUser().getDinnersRepo().add(recipe.getRecipeTitle());
            Toast.makeText(getApplicationContext(), "Successfully added!", Toast.LENGTH_SHORT).show();
        }
        UserDataManager.updateUserToFirebase(FirebaseAuth.getInstance());
    }

    /**
     * Save recipe to drinks collection
     */
    private void saveToDrinks(){
        if(UserDataManager.getUser().getDrinksRepo().contains(recipe.getRecipeTitle())){
            Toast.makeText(getApplicationContext(), "Already added in collection", Toast.LENGTH_SHORT).show();
        }else {
            UserDataManager.getUser().getDrinksRepo().add(recipe.getRecipeTitle());
            Toast.makeText(getApplicationContext(), "Successfully added!", Toast.LENGTH_SHORT).show();
        }
        UserDataManager.updateUserToFirebase(FirebaseAuth.getInstance());
    }

    /**
     * Save recipe to desserts collection
     */
    private void saveToDesserts(){
        if(UserDataManager.getUser().getDessertsRepo().contains(recipe.getRecipeTitle())){
            Toast.makeText(getApplicationContext(), "Already added in collection", Toast.LENGTH_SHORT).show();
        }else {
            UserDataManager.getUser().getDessertsRepo().add(recipe.getRecipeTitle());
            Toast.makeText(getApplicationContext(), "Successfully added!", Toast.LENGTH_SHORT).show();
        }
    }
}

