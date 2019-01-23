package com.nickhe.reciperescue.Models;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.nickhe.reciperescue.Controllers.RecipeViewActivity;
import com.nickhe.reciperescue.R;

public class IngredientListAdapter extends ArrayAdapter<String> {

    private String[] ingredients;
    private Activity context;

    public IngredientListAdapter(Activity context, String[] ingredients, Recipe recipe) {
        super(context, R.layout.ingredient_row, R.id.ingredientTextView_row, ingredients);
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        IngredientListAdapter.ViewHolder viewHolder = null;

        if(row == null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            row = layoutInflater.inflate(R.layout.ingredient_row, parent, false);
            viewHolder = new IngredientListAdapter.ViewHolder(row);
            row.setTag(viewHolder);
        }else {
            viewHolder = (IngredientListAdapter.ViewHolder)row.getTag();
        }

        if (position % 2 == 1) {
            // Set a background color for ListView regular row/item
            row.setBackgroundColor(Color.parseColor("#d9e0de"));
        }
        viewHolder.textView.setText(ingredients[position]);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserDataManager.getUser().getShoppingList() == null)
                {
                    Toast.makeText(context, "null object", Toast.LENGTH_SHORT).show();
                }else {
                    UserDataManager.getUser().getShoppingList().add(ingredients[position]);
                    UserDataManager.updateUserToFirebase(FirebaseAuth.getInstance());
                    RecipeViewActivity.updateNumOfOrderTextView(UserDataManager.getUser().getShoppingList());
                    Toast.makeText(context, "Ingredient added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return row;
    }

    class ViewHolder
    {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View view)
        {
            textView = view.findViewById(R.id.ingredientTextView_row);
            imageView = view.findViewById(R.id.addIngredientIcon);
        }
    }

}
