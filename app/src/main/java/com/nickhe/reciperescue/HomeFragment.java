package com.nickhe.reciperescue;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class HomeFragment extends Fragment {

    private View view;
    private ListView listView;
    RecipeRepository recipeRepository;
    FakeRecipeRepository fakeRecipeRepository;
    RecipeDataManager recipeDataManager;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize();
        setListViewOnClickListener();
//        fakeRecipeRepository = FakeRecipeRepository.getFakeRecipeRepository(getActivity());
//        recipeDataManager = new RecipeDataManager();
//        for(Recipe recipe: fakeRecipeRepository.getFakeRepo()){
//            recipeDataManager.saveRecipeToMap(recipe);
//        }

    }

    private void setListViewOnClickListener()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Recipe recipe = recipeRepository.getRecipeRepo().get(position);

                startRecipeViewActivity(recipe);
            }
        });
    }

    /**
     * Initialize views and other fields
     */
    private void initialize(){
        recipeRepository = RecipeRepository.getRecipeRepository();
        listView = view.findViewById(R.id.home_recipeList);
        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(getActivity(), recipeRepository.getRecipeRepo());
        listView.setAdapter(recipeListAdapter);
        ListViewProcessor.setListViewHeightBasedOnChildren(listView);
    }

    /**
     *
     * @param recipe
     */
    private void startRecipeViewActivity(Recipe recipe)
    {
        Intent i = new Intent(getActivity().getBaseContext(), RecipeViewActivity.class);
        i.putExtra("recipe", recipe);
        startActivity(i);
    }
}
