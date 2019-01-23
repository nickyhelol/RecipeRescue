package com.nickhe.reciperescue.Models;

import com.nickhe.reciperescue.R;

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
    private ArrayList<String> breakfastsRepo = new ArrayList<>();
    private ArrayList<String> sidesRepo = new ArrayList<>();
    private ArrayList<String> dinnersRepo = new ArrayList<>();
    private ArrayList<String> drinksRepo = new ArrayList<>();
    private ArrayList<String> dessertsRepo = new ArrayList<>();
    private ArrayList<String> shoppingList = new ArrayList<>();

    public User(){
        this.profileImageUri = "android.resource://com.nickhe.reciperescue/drawable/"+R.drawable.blankproflie;
    }

    public User(String name, String description, String profileImageUri, ArrayList<String> breakfastsRepo,
                ArrayList<String> sidesRepo, ArrayList<String> dinnersRepo, ArrayList<String> drinksRepo,
                ArrayList<String> dessertsRepo, ArrayList<String> shoppingList) {
        this.name = name;
        this.description = description;
        this.profileImageUri = profileImageUri;
        this.breakfastsRepo = breakfastsRepo;
        this.sidesRepo = sidesRepo;
        this.dinnersRepo = dinnersRepo;
        this.drinksRepo = drinksRepo;
        this.dessertsRepo = dessertsRepo;
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

    public ArrayList<String> getBreakfastsRepo() {
        return breakfastsRepo;
    }

    public void setBreakfastsRepo(ArrayList<String> breakfastsRepo) {
        this.breakfastsRepo = breakfastsRepo;
    }

    public ArrayList<String> getSidesRepo() {
        return sidesRepo;
    }

    public void setSidesRepo(ArrayList<String> sidesRepo) {
        this.sidesRepo = sidesRepo;
    }

    public ArrayList<String> getDinnersRepo() {
        return dinnersRepo;
    }

    public void setDinnersRepo(ArrayList<String> dinnersRepo) {
        this.dinnersRepo = dinnersRepo;
    }

    public ArrayList<String> getDrinksRepo() {
        return drinksRepo;
    }

    public void setDrinksRepo(ArrayList<String> drinksRepo) {
        this.drinksRepo = drinksRepo;
    }

    public ArrayList<String> getDessertsRepo() {
        return dessertsRepo;
    }

    public void setDessertsRepo(ArrayList<String> dessertsRepo) {
        this.dessertsRepo = dessertsRepo;
    }

    public ArrayList<String> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ArrayList<String> shoppingList) {
        this.shoppingList = shoppingList;
    }
}
