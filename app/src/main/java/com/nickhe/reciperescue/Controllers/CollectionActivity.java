package com.nickhe.reciperescue.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nickhe.reciperescue.Models.ListViewProcessor;
import com.nickhe.reciperescue.R;
import com.nickhe.reciperescue.Models.Recipe;
import com.nickhe.reciperescue.Models.RecipeListAdapter;
import com.nickhe.reciperescue.Models.RecipeRepository;
import com.nickhe.reciperescue.Models.UserDataManager;

import java.util.ArrayList;

public class CollectionActivity extends AppCompatActivity {

    private ImageView collectionImageView;
    private TextView collectionTitleTextView, collectionDescriptionTextView, collectionNumTextView;
    private ListView collectionListView;
    private int index;
    private ArrayList<Recipe> collections = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        initializeViews();

        retrieveIndexFromIntent();

        setCollectionListViewOnClickListener();
    }

    private void retrieveIndexFromIntent() {
        Intent intent = getIntent();
        index = intent.getIntExtra("collection", 0);
        updateViews(index);
    }


    private void updateViews(int index) {
        switch (index) {
            case 1:
                setBreakfastCollection();
                break;
            case 2:
                setSidesCollection();
                break;
            case 3:
                setDinnerCollection();
                break;
            case 4:
                setDrinkCollection();
                break;
            case 5:
                setDessertCollection();
                break;
        }
    }

    private void setBreakfastCollection() {
        if (UserDataManager.getUser().getBreakfastsRepo() != null) {
            for (Recipe recipe : RecipeRepository.getRecipeRepository().getRecipeRepo()) {
                if (UserDataManager.getUser().getBreakfastsRepo().contains(recipe.getRecipeTitle())) {
                    collections.add(recipe);
                }
            }
            collectionNumTextView.setText(collections.size() + " items");
            initializeListView(collections);
        }
    }

    private void setSidesCollection() {
        collectionImageView.setImageResource(R.drawable.side);
        collectionTitleTextView.setText("Sides");
        collectionDescriptionTextView.setText("Only the pure in heart can make a good soup");

        if (UserDataManager.getUser().getSidesRepo() != null) {
            for (Recipe recipe : RecipeRepository.getRecipeRepository().getRecipeRepo()) {
                if (UserDataManager.getUser().getSidesRepo().contains(recipe.getRecipeTitle())) {
                    collections.add(recipe);
                }
            }
            collectionNumTextView.setText(collections.size() + " items");
            initializeListView(collections);
        }
    }

    private void setDinnerCollection() {
        collectionImageView.setImageResource(R.drawable.dinner);
        collectionTitleTextView.setText("Dinners");
        collectionDescriptionTextView
                .setText("After a good dinner one can forgive anybody, even one's own relations");

        if (UserDataManager.getUser().getDinnersRepo() != null) {
            for (Recipe recipe : RecipeRepository.getRecipeRepository().getRecipeRepo()) {
                if (UserDataManager.getUser().getDinnersRepo().contains(recipe.getRecipeTitle())) {
                    collections.add(recipe);
                }
            }
            collectionNumTextView.setText(collections.size() + " items");
            initializeListView(collections);
        }
    }

    private void setDrinkCollection() {
        collectionImageView.setImageResource(R.drawable.drink);
        collectionTitleTextView.setText("Drinks");
        collectionDescriptionTextView.setText("When life gives you lemons, make lemonade");

        if (UserDataManager.getUser().getDrinksRepo() != null) {

            for (Recipe recipe : RecipeRepository.getRecipeRepository().getRecipeRepo()) {
                if (UserDataManager.getUser().getDrinksRepo().contains(recipe.getRecipeTitle())) {
                    collections.add(recipe);
                }
            }
            collectionNumTextView.setText(collections.size() + " items");
            initializeListView(collections);
        }
    }

    private void setDessertCollection() {
        collectionImageView.setImageResource(R.drawable.dessert);
        collectionTitleTextView.setText("Desserts");
        collectionDescriptionTextView.setText("Life is uncertain. Eat dessert first.");
        if (UserDataManager.getUser().getDessertsRepo() != null) {

            for (Recipe recipe : RecipeRepository.getRecipeRepository().getRecipeRepo()) {
                if (UserDataManager.getUser().getDessertsRepo().contains(recipe.getRecipeTitle())) {
                    collections.add(recipe);
                }
            }
            collectionNumTextView.setText(collections.size() + " items");
            initializeListView(collections);
        }
    }

    private void initializeListView(ArrayList<Recipe> repo) {
        RecipeListAdapter adapter = new RecipeListAdapter(CollectionActivity.this, repo);
        collectionListView.setAdapter(adapter);
        ListViewProcessor.setListViewHeightBasedOnChildren(collectionListView);
    }


    private void initializeViews() {
        collectionImageView = findViewById(R.id.collectionImageView);
        collectionTitleTextView = findViewById(R.id.collectionTitleTextView);
        collectionDescriptionTextView = findViewById(R.id.collectionDescriptionTextView);
        collectionNumTextView = findViewById(R.id.collectionNumTextView);
        collectionListView = findViewById(R.id.collectionListView);
    }

    private void setCollectionListViewOnClickListener() {
        collectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe recipe = collections.get(position);

                startRecipeViewActivity(recipe);
            }
        });
    }

    /**
     * @param recipe
     */
    private void startRecipeViewActivity(Recipe recipe) {
        Intent i = new Intent(CollectionActivity.this, RecipeViewActivity.class);
        i.putExtra("recipe", recipe);
        startActivity(i);
    }

}
