package com.nickhe.reciperescue;

import android.app.Activity;
import android.graphics.Bitmap;
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

public class IngredientListAdapter extends ArrayAdapter<String> {

    private String[] ingredients;
    private Activity context;
    private User user;

    public IngredientListAdapter(Activity context, String[] ingredients) {
        super(context, R.layout.ingredient_row, R.id.ingredientTextView_row, ingredients);
        this.ingredients = ingredients;
        this.context = context;
        this.user = UserDataManager.getUser();
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
                if(user.getShoppingList() == null)
                {
                    Toast.makeText(context, "null object", Toast.LENGTH_SHORT).show();
                }else {
                    user.getShoppingList().add(ingredients[position]);
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
