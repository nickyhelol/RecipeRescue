package com.nickhe.reciperescue.Models;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nickhe.reciperescue.Models.ImageProcessor;
import com.nickhe.reciperescue.Models.Recipe;
import com.nickhe.reciperescue.R;

import java.util.List;

public class RecipeListAdapter extends ArrayAdapter<Recipe>
{
    private List<Recipe> recipes;
    private Activity context;

    public RecipeListAdapter(Activity context, List<Recipe> recipes) {
        super(context, R.layout.recipe_row, R.id.recipeTextView, recipes);
        this.recipes = recipes;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        ViewHolder viewHolder = null;

        if(row == null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            row = layoutInflater.inflate(R.layout.recipe_row, parent, false);
            viewHolder = new ViewHolder(row);
            row.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)row.getTag();
        }

        Bitmap recipeImage = ImageProcessor.convertUriToBitmap(this.context, recipes.get(position).getRecipeImage());
        viewHolder.imageView.setImageBitmap(recipeImage);
        viewHolder.textView.setText(recipes.get(position).getRecipeTitle());

        return row;
    }

    class ViewHolder
    {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View view)
        {
            textView = view.findViewById(R.id.recipeTextView);
            imageView = view.findViewById(R.id.recipeImageView);
        }
    }

}
