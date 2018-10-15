package com.nickhe.reciperescue;

import android.net.Uri;

import com.nickhe.reciperescue.Recipe;

import java.util.ArrayList;

/**
 * This is the model for user with different attributes.
 *
 * TODO add new feature to the database including recipes. Probably need to extend Json tree.
 */
public class User {

    private String name;
    private String description;
    private String profileImageUri;
    private ArrayList<Recipe> personalRepo = new ArrayList<>();
    private ArrayList<String> shoppingList = new ArrayList<>();

    public User(){
        this.profileImageUri = "android.resource://com.nickhe.reciperescue/drawable/"+R.drawable.blankproflie;
    }

    public User(String name, String description, String profileImageUri, ArrayList<Recipe> personalRepo, ArrayList<String> shoppingList) {
        this.name = name;
        this.description = description;
        this.profileImageUri = profileImageUri;
        this.personalRepo = personalRepo;
        this.shoppingList = shoppingList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileImage() {
        return profileImageUri;
    }

    public void setProfileImage(String profileImageUri) {
        this.profileImageUri = profileImageUri;
    }

    public ArrayList<Recipe> getPersonalRepo() {
        return personalRepo;
    }

    public void setPersonalRepo(ArrayList<Recipe> personalRepo) {
        this.personalRepo = personalRepo;
    }

    public ArrayList<String> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ArrayList<String> shoppingList) {
        this.shoppingList = shoppingList;
    }
}
