package com.nickhe.reciperescue.Models;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nickhe.reciperescue.Models.Rating;
import com.nickhe.reciperescue.Models.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeDataManager {

    FirebaseFirestore recipeDb;
    Map<String, Object> recipeSubmit;
    List<Recipe> recipeRetrieved;

    public RecipeDataManager()
    {
        recipeDb = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        recipeDb.setFirestoreSettings(settings);
    }

    /**
     * Save recipe data to a map
     * @param recipe
     */
    public void saveRecipeToMap(Recipe recipe)
    {
        recipeSubmit = new HashMap<>();
        recipeSubmit.put("recipeTitle", recipe.getRecipeTitle());
        recipeSubmit.put("recipeIngredients", Arrays.asList(recipe.getRecipeIngredients()));
        recipeSubmit.put("recipePublisher", recipe.getRecipePublisher());
        recipeSubmit.put("time", recipe.getTime());
        recipeSubmit.put("calories", recipe.getCalories());
        recipeSubmit.put("recipeInstructions", Arrays.asList(recipe.getRecipeInstruction()));
        recipeSubmit.put("recipeRating", recipe.getRecipeRating().name());
        recipeSubmit.put("recipeImageUri", recipe.getRecipeImage().toString());
        sendData();

    }

    /**
     * Send recipe date to Firebase
     */
    public void sendData()
    {
        recipeDb.collection("Recipes")
                .add(recipeSubmit)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println("Send recipe data successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.err.println("Sending recipe data falied.");
                    }
                });
    }

    /**
     * Retrieve recipe data from Firebase and return a recipe repo
     *
     * @return
     */
    public List<Recipe> getData(){
        recipeRetrieved = new ArrayList<>();
        recipeDb.collection("Recipes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult())
                            {
                                Map<String, Object> recipeData = document.getData();
                                Recipe recipe = parseMapToRecipe(recipeData);
                                recipeRetrieved.add(recipe);
                                System.out.println("Recipe added!!!!!!!!!!");
                            }
                        }
                    }
                });

        return recipeRetrieved;
    }

    /**
     * Parse map to Recipe data.
     *
     * @param map
     * @return
     */
    public Recipe parseMapToRecipe(Map<String, Object> map)
    {
        String recipeTitle = (String) map.get("recipeTitle");
        ArrayList<String> recipeIngredientsList = (ArrayList<String>) map.get("recipeIngredients");
        String[] recipeIngredients = recipeIngredientsList.toArray(new String[recipeIngredientsList.size()]);
        String recipePublisher = (String)map.get("recipePublisher");
        String time = (String) map.get("time");
        String calories = (String) map.get("calories");
        ArrayList<String> recipeInstructionsList = (ArrayList<String>) map.get("recipeInstructions");
        String[] recipeInstructions = recipeInstructionsList.toArray(new String[recipeInstructionsList.size()]);
        Rating recipeRating = Rating.valueOf(map.get("recipeRating").toString());
        Uri recipeImageUri = Uri.parse(map.get("recipeImageUri").toString());

        Recipe recipe = new Recipe(recipeTitle, recipeIngredients, recipePublisher, time, calories,
                recipeInstructions,  recipeRating, recipeImageUri);

        return recipe;
    }

}
