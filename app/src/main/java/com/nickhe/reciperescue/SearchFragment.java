package com.nickhe.reciperescue;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;


/**
 * A Fragment subclass responsible for the searching methods of the Recipe Rescue application.
 * Links to RecipeListFragment for display while filtering data goes to RecipeListAdapterFilterable
 * for filtering.
 */
public class SearchFragment extends Fragment {

    private View searchView;
    private SearchView recipeSearchView;
    private ConstraintLayout resultLayout;
    private TextView resultTextView;
    private ListView resultListView;
    FakeRecipeRepository fakeRecipeRepository;
    private ArrayList<Recipe> results;
    private int numberOfResults;

    /**
     * The constructor for SearchFragment fragment. Initialises the variables, notably the numberOfIngredients
     * variable to use with the addIngredients method.
     */
    public SearchFragment() {

    }

    /**
     * Overrides the onCreateView method from superclass. Initialises searchView to the view of the
     * fragment.
     *
     * @param inflater The LayoutInflater of the search fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        super.onCreateView(inflater, container, savedInstanceState);
        this.searchView = view;
        return view;
    }

    /**
     * TODO
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        searchButton = searchView.findViewById(R.id.searchButton);
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                searchRecipeIngredients(v);
//            }
//        });
//        addIngredientsButton = searchView.findViewById(R.id.addIngredientsButton);
//        addIngredientsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addIngredientToList(v);
//            }
//        });
        recipeSearchView = searchView.findViewById(R.id.recipeSearchView);
        resultLayout = searchView.findViewById(R.id.resultLayout);
        resultTextView = searchView.findViewById(R.id.resultTextView);
        resultListView = searchView.findViewById(R.id.resultListView);
        fakeRecipeRepository = FakeRecipeRepository.getFakeRecipeRepository(getActivity());

//        RecyclerView recipeRecyclerView = searchView.findViewById(R.id.searchRecyclerView);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
//        recipeRecyclerView.setLayoutManager(staggeredGridLayoutManager);
//        recipeRecyclerView.setAdapter(ingredientTokensAdapter);

        recipeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (searchRecipeName(query)) {
                    updateView();
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    /**
     * The searchRecipeName method searches the repository for recipes whose recipe titles are similar
     * to the entered query.
     *
     * @param query The query for the recipe name
     */
    public boolean searchRecipeName(String query) {

        results = new ArrayList<>();
        numberOfResults = 0;

        for(Recipe recipe: fakeRecipeRepository.getFakeRepo())
        {
            System.out.println(recipe.getRecipeTitle());
            System.out.println(query);
            if(recipe.getRecipeTitle().toLowerCase().contains(query.toLowerCase()))
            {
                results.add(recipe);
                numberOfResults++;
            }
        }

        return true;
    }

    public void updateView(){
        resultLayout.setVisibility(View.VISIBLE);
        resultTextView.setText(numberOfResults+" RESULTS");
        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(getActivity(), results);
        resultListView.setAdapter(recipeListAdapter);
        HomeFragment.setListViewHeightBasedOnChildren(resultListView);
    }


//    /**
//     * The searchRecipeIngredients method searches the repository for recipes that contain all the
//     * ingredients specified.
//     * Implementation wise, it is linked to the Search button below the ingredients.
//     *
//     * @param view The view of the searching activity / fragment
//     */
//    public boolean searchRecipeIngredients(View view) {
//        Bundle recipeIngredientsBundle = new Bundle();
//        String ingredients = "";
//        recipeIngredientsBundle.putString("Type", "Ingredients");
//        FlowLayout layout = searchView.findViewById(R.id.searchFlowLayout);
//        for (int i = 0; i < layout.getChildCount(); ++i) {
////            LinearLayout ingredientLayout = (LinearLayout) layout.getChildAt(i);
//            ToggleButton ingredientInput = (ToggleButton) layout.getChildAt(i);
//            if (ingredientInput.isChecked()) {
//                ingredients += ingredientInput.getText();
//                ingredients += ",";
//            }
//        }
//        recipeIngredientsBundle.putString("Ingredients", ingredients);
//
//        RecipeListFragment recipeListFragment = new RecipeListFragment();
//        recipeListFragment.setArguments(recipeIngredientsBundle);
//        getFragmentManager().beginTransaction().replace(R.id.contentLayout, recipeListFragment).commit();
//
//        if (recipeIngredientsBundle.get("Ingredients").equals(ingredients)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * The addIngredients method adds a new EditText field under the last ingredient EditText field.
//     * This method takes the Layout of the ingredients field and adds another into it, with the id
//     * dynamically assigned.
//     *
//     * @param view The view of the search fragment
//     */
//    public boolean addIngredients(View view) {
//        LinearLayout layout = this.searchView.findViewById(R.id.ingredientsLinearLayout);
//        EditText nextIngredient = new EditText(getActivity());
//        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        nextIngredient.setLayoutParams(p);
//        nextIngredient.setTextSize(14);
//        nextIngredient.setText("Additional ingredient");
//        nextIngredient.setEms(10);
//        p.setMargins(0, 10, 0, 0);
//        p.setMarginStart(80);
//        p.setMarginEnd(80);
//        nextIngredient.setId(numberOfIngredients + 1);
//        layout.addView(nextIngredient);
//        ++numberOfIngredients;
//
//        if (nextIngredient != null) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public void addIngredientToList(View view) {
//        EditText ingredientText = this.searchView.findViewById(R.id.recipeIngredientsFilterInput1);
//        String buttonText = ingredientText.getText().toString();
////        ingredientTokensAdapter.addToken(buttonText);
//        ToggleButton ingredientToken = new ToggleButton(getActivity());
//        FlowLayout flowLayout = this.searchView.findViewById(R.id.searchFlowLayout);
//        ingredientToken.setId(numberOfIngredients);
//        ingredientToken.setText(buttonText);
//        ingredientToken.setTextOff(buttonText);
//        ingredientToken.setTextOn(buttonText);
//        ingredientToken.setBackgroundResource(R.drawable.token_states);
//        flowLayout.addView(ingredientToken);
//        ++numberOfIngredients;
//    }
//
//
}
