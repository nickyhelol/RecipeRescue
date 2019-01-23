package com.nickhe.reciperescue.Models;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {

    private static RecipeRepository recipeRepository;
    private List<Recipe> recipeRepo;
    RecipeDataManager recipeDataManager;

    public RecipeRepository()
    {
        recipeRepo = new ArrayList<>();
        recipeDataManager = new RecipeDataManager();
        initializeRepo();
    }

    /**
     * Return and initialize a FakeRecipeRepository and make sure
     * that there will be only one instance of FakeRecipeRepository.
     *
     * @param
     * @return
     */
    public static synchronized RecipeRepository getRecipeRepository()
    {
        if (recipeRepository == null) {
            recipeRepository = new RecipeRepository();
        }

        return recipeRepository;
    }

    private void initializeRepo() {
        recipeDataManager = new RecipeDataManager();
        recipeRepo = recipeDataManager.getData();
    }

    public List<Recipe> getRecipeRepo(){
        return this.recipeRepo;
    }

}
