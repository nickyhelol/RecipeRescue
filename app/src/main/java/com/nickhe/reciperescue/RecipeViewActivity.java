package com.nickhe.reciperescue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RecipeViewActivity extends AppCompatActivity {

    Recipe recipe;
    ImageView recipeImage, shoppingCartImageView, starImageView, greenStarImageView;
    TextView recipeTitle, publisherTextView, ingredientsTextView;
    TextView caloriesTextView, timeTextView, instructionTextView;
    ListView ingredientsListView;
    static TextView numberOfOrderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        receiveData();
        initializeView();
        updateView();
        initializeIngredientListView();
        initializeInstructionTextView();
        setStarImageViewOnClickListenner();
        setYellowStarImageViewOnClickListenner();
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
        starImageView = findViewById(R.id.starImageView);
        greenStarImageView = findViewById(R.id.greenStarImageView);
        numberOfOrderTextView = findViewById(R.id.numberOfOrdersTextView);
    }

    public static void updateNumOfOrderTextView(ArrayList<String> list){
        numberOfOrderTextView.setText(String.valueOf(list.size()));
    }

    /**
     * Set onClick listener for starImageView
     */
    private void setStarImageViewOnClickListenner(){
        starImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                starImageView.setVisibility(View.INVISIBLE);
                greenStarImageView.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Set onClick listener for starImageView
     */
    private void setYellowStarImageViewOnClickListenner(){
        greenStarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Unsaved", Toast.LENGTH_SHORT).show();
                greenStarImageView.setVisibility(View.INVISIBLE);
                starImageView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initializeIngredientListView(){

        IngredientListAdapter ingredientListAdapter =
                new IngredientListAdapter(RecipeViewActivity.this, recipe.getRecipeIngredients(), recipe);
        ingredientsListView.setAdapter(ingredientListAdapter);
        ListViewProcessor.setListViewHeightBasedOnChildren(ingredientsListView);
    }

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
}
