package com.nickhe.reciperescue.Controllers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nickhe.reciperescue.R;
import com.nickhe.reciperescue.Models.Recipe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateRecipeActivity extends AppCompatActivity {

    FirebaseFirestore recipeDB;
    Map<String, Object> recipeSubmit = new HashMap<>();
    Recipe recipe;

    public void submitRecipe(View view) {

        getRecipeFields(view);


        //TODO IMAGE

        recipeDB.collection("Recipes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document: task.getResult()){
                                document.getData();
                            }
                        }
                    }
                });
        recipeDB.collection("Recipes")
                .add(recipeSubmit)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "DocumentSnapshot written with ID: " + documentReference.getId(),Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error adding document" +e,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getRecipeFields (View view) {
        EditText recipeTitleEditText = findViewById(R.id.recipeTitleInput);
        String recipeTitle = recipeTitleEditText.getText().toString();

        EditText caloriesEditText = findViewById(R.id.caloriesInput);
        String calories = caloriesEditText.getText().toString();

        EditText timeEditText = findViewById(R.id.timeInput);
        String time = timeEditText.getText().toString();

        LinearLayout ingredientsLayout = findViewById(R.id.recipeIngredientsInputLayout);
        String[] ingredientsArray = new String[ingredientsLayout.getChildCount()];
        for (int i = 0; i < ingredientsLayout.getChildCount(); ++i) {
            EditText ingredientText = (EditText) ingredientsLayout.getChildAt(i);
            ingredientsArray[i] = ingredientText.getText().toString();
        }

        LinearLayout instructionsLayout = findViewById(R.id.recipeInstructionsInputLayout);
        String[] instructionsArray = new String[instructionsLayout.getChildCount()];
        for (int i = 0; i < instructionsLayout.getChildCount(); ++i) {
            EditText instructionText = (EditText) instructionsLayout.getChildAt(i);
            instructionsArray[i] = instructionText.getText().toString();
        }

        List ingredientsList;
        List instructionsList;

        ingredientsList = Arrays.asList(ingredientsArray);
        instructionsList = Arrays.asList(instructionsArray);

        recipeSubmit.put("recipeTitle",recipeTitle);
        recipeSubmit.put("recipeIngredients", ingredientsList);
        recipeSubmit.put("recipeInstruction", instructionsList);
        recipeSubmit.put("calories",calories);
        recipeSubmit.put("time",time);
    }

    public void addIngredient(View view) {
        LinearLayout layout = findViewById(R.id.recipeIngredientsInputLayout);
        EditText text = new EditText(getApplicationContext());
        text.setHint("Recipe Ingredient");
        layout.addView(text);
    }

    public void addInstruction(View view) {
        LinearLayout layout = findViewById(R.id.recipeInstructionsInputLayout);
        EditText text = new EditText(getApplicationContext());
        text.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        text.setHint("Recipe Instruction");
        layout.addView(text);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        recipeDB = FirebaseFirestore.getInstance();
    }
}
