package com.nickhe.reciperescue;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.net.URI;

/**
 * Recipe object to use in the program, should be able to be stored in the database.
 * <p>
 * The ideal recipe object should contain the following attributes:
 * a. id - For identification purposes, e.g. viewing a recipe
 * b. recipeTitle - The name of the recipe
 * c. recipeIngredients - The list of ingredients used by the recipe
 * d. recipePublisher - The user that published the recipe
 * e. time - The cooking time of the recipe
 * f. calories - The amount of calories of the recipe
 * g. recipeInstruction - The list of instructions for the recipe
 * h. recipeRating - The rating of the recipe
 * i. recipeImage - The image of the recipe
 */
public class Recipe implements Serializable {

    private int id;
    private String recipeTitle;
    private String[] recipeIngredients;
    private String recipePublisher;
    private String time;
    private String calories;
    private String[] recipeInstruction;
    private Rating recipeRating;
    private Uri recipeImageUri;

    /**
     * Constructor for the recipe object, minimum fields for a recipe.
     *
     * @param recipeTitle     desired recipe name
     * @param recipePublisher the publisher of the recipe
     * @param time            time needed for cooking
     */
    public Recipe(String recipeTitle, String recipePublisher, String time) {
        this.time = time;
        this.recipeTitle = recipeTitle;
        this.recipePublisher = recipePublisher;
    }

    public Recipe(String recipeTitle, String[] recipeIngredients, String time, String calories, String[] recipeInstruction, Uri recipeImageUri) {
        this.time = time;
        this.recipeTitle = recipeTitle;
        this.recipeIngredients = recipeIngredients;
        this.calories = calories;
        this.recipeInstruction = recipeInstruction;
        this.recipeImageUri = recipeImageUri;
        this.recipeRating = Rating.THREE;
    }

    /**
     * Proper constructor for the recipe object, contains all the required fields for the recipe.
     * Should be used in all cases.
     *
     * @param id                id of the recipe
     * @param recipeTitle       title of the recipe
     * @param recipeIngredients ingredients required for the recipe
     * @param recipePublisher   publisher of the recipe
     * @param time              time required for cooking
     * @param calories          calories contained
     * @param recipeInstruction instructions for cooking
     * @param recipeImageUri       image of the recipe
     */
    public Recipe(int id, String recipeTitle, String[] recipeIngredients, String recipePublisher, String time, String calories, String[] recipeInstruction, Uri recipeImageUri) {
        this.id = id;
        this.recipeTitle = recipeTitle;
        this.recipeIngredients = recipeIngredients;
        this.recipePublisher = recipePublisher;
        this.time = time;
        this.calories = calories;
        this.recipeInstruction = recipeInstruction;
        this.recipeImageUri = recipeImageUri;
    }

    public Uri getRecipeImage() {
        return recipeImageUri;
    }

    public void setRecipeImage(Uri recipeImageUri) {
        this.recipeImageUri = recipeImageUri;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String[] getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(String[] recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String getRecipePublisher() {
        return recipePublisher;
    }

    public void setRecipePublisher(String recipePublisher) {
        this.recipePublisher = recipePublisher;
    }

    public String[] getRecipeInstruction() {
        return recipeInstruction;
    }

    public void setRecipeInstruction(String[] recipeInstruction) {
        this.recipeInstruction = recipeInstruction;
    }

    public Rating getRecipeRating() {
        return recipeRating;
    }

    public void setRecipeRating(Rating recipeRating) {
        this.recipeRating = recipeRating;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}