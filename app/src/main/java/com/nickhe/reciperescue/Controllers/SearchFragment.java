package com.nickhe.reciperescue.Controllers;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.nickhe.reciperescue.Models.ListViewProcessor;
import com.nickhe.reciperescue.R;
import com.nickhe.reciperescue.Models.Recipe;
import com.nickhe.reciperescue.Models.RecipeListAdapter;
import com.nickhe.reciperescue.Models.RecipeRepository;

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
    private RecipeRepository recipeRepository;
    private ArrayList<Recipe> results;
    private int numberOfResults;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        initialize();
        setRecipeSearchViewOnQueryTextListener();
        setResultListViewOnClickListener();
    }

    /**
     * Set OnQueryTextListener for recipeSearchView
     */
    private void setRecipeSearchViewOnQueryTextListener(){
        recipeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (searchRecipeName(query)) {
                    updateView();
                    recipeSearchView.clearFocus();
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
     * Initialize views and other fields
     */
    public void initialize()
    {
        recipeSearchView = searchView.findViewById(R.id.recipeSearchView);
        resultLayout = searchView.findViewById(R.id.resultLayout);
        resultTextView = searchView.findViewById(R.id.resultTextView);
        resultListView = searchView.findViewById(R.id.resultListView);
        recipeRepository = RecipeRepository.getRecipeRepository();
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

        for(Recipe recipe: recipeRepository.getRecipeRepo())
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

    /**
     * Update views when called
     */
    public void updateView(){
        resultLayout.setVisibility(View.VISIBLE);
        resultTextView.setText(numberOfResults+" RESULTS");
        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(getActivity(), results);
        resultListView.setAdapter(recipeListAdapter);
        ListViewProcessor.setListViewHeightBasedOnChildren(resultListView);
    }

    private void setResultListViewOnClickListener(){
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe recipe = results.get(position);

                startRecipeViewActivity(recipe);
            }
        });
    }


    /**
     *
     * @param recipe
     */
    private void startRecipeViewActivity(Recipe recipe)
    {
        Intent i = new Intent(getActivity(), RecipeViewActivity.class);
        i.putExtra("recipe", recipe);
        startActivity(i);
    }

}
