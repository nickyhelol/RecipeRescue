package com.nickhe.reciperescue;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListFragment extends Fragment {

    View view;
    ImageView addIconImageView;
    SearchView searchView;
    ConstraintLayout layout;
    TextView addItemTextView;
    static TextView numberOfItemsView;
    static ListView shoppingCartListView;

    public ShoppingListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize();
        setAddIconImageViewOnClickListener();
        setSearchViewOnQueryTextSubmit();
        setAddItemTextViewOnClickListener();
        initializeShoppingCarListView();
        setNumberOfItemsView();
    }

    /**
     * Initialize views
     */
    private void initialize() {
        addIconImageView = view.findViewById(R.id.addIconImageView);
        searchView = view.findViewById(R.id.addSearchView);
        layout = view.findViewById(R.id.addConstraintLayout);
        addItemTextView = view.findViewById(R.id.addItemTextView);
        shoppingCartListView = view.findViewById(R.id.shoppingCartListView);
        numberOfItemsView = view.findViewById(R.id.numberOfItemsView);
    }

    /**
     * Set correct number for numberOfItemsView
     */
    private void setNumberOfItemsView() {
        numberOfItemsView.setText(UserDataManager.getUser().getShoppingList().size() + " items");
    }

    /**
     * Initialize shopping cart list view
     */
    private void initializeShoppingCarListView() {
        ShoppingListAdapter shoppingListAdapter =
                new ShoppingListAdapter(getActivity(), UserDataManager.getUser().getShoppingList());
        shoppingCartListView.setAdapter(shoppingListAdapter);
        ListViewProcessor.setListViewHeightBasedOnChildren(shoppingCartListView);
    }

    /**
     * Reset arrayAdapter
     * @param shoppingListAdapter
     */
    public static void resetAdapter(ShoppingListAdapter shoppingListAdapter) {
        shoppingCartListView.setAdapter(shoppingListAdapter);
        ListViewProcessor.setListViewHeightBasedOnChildren(shoppingCartListView);
    }

    /**
     * set onClick listener for addIconImageView
     */
    private void setAddIconImageViewOnClickListener() {
        addIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setBackgroundColor(Color.TRANSPARENT);
                searchView.setVisibility(View.VISIBLE);
                searchView.setIconified(false);
            }
        });
    }

    /**
     * Set onQueryTextSubmit for searchView
     */
    private void setSearchViewOnQueryTextSubmit() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                addItemTextView.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * Set onClick listener for addItemTextView
     */
    private void setAddItemTextViewOnClickListener() {
        addItemTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.INVISIBLE);
                layout.setBackgroundColor(Color.BLACK);
                addItemTextView.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     * Update numberOfItemView
     * @param list
     */
    public static void updateNumberOfItemView(ArrayList<String> list) {
        numberOfItemsView.setText(list.size() + " items");
    }
}
