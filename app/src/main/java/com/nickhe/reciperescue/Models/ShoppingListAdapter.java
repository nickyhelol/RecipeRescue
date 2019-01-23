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

import com.google.firebase.auth.FirebaseAuth;
import com.nickhe.reciperescue.Controllers.ShoppingListFragment;
import com.nickhe.reciperescue.R;

import java.util.ArrayList;

public class ShoppingListAdapter extends ArrayAdapter<String> {

    private static ShoppingListAdapter shoppingListAdapter;
    private ArrayList<String> list;
    private Activity context;

    public ShoppingListAdapter(Activity context, ArrayList<String> list) {
        super(context, R.layout.shopping_list_row, R.id.shoppingListTextView, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        ShoppingListAdapter.ViewHolder viewHolder = null;

        if (row == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            row = layoutInflater.inflate(R.layout.shopping_list_row, parent, false);
            viewHolder = new ShoppingListAdapter.ViewHolder(row);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ShoppingListAdapter.ViewHolder) row.getTag();
        }

        if (position % 2 == 1) {
            // Set a background color for ListView regular row/item
            row.setBackgroundColor(Color.parseColor("#d9e0de"));
        }
        viewHolder.textView.setText(list.get(position));

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDataManager.getUser().getShoppingList().remove(position);
                UserDataManager.updateUserToFirebase(FirebaseAuth.getInstance());
                ShoppingListAdapter shoppingListAdapter =
                        new ShoppingListAdapter(context, UserDataManager.getUser().getShoppingList());
                ShoppingListFragment.resetAdapter(shoppingListAdapter);
                ShoppingListFragment.updateNumberOfItemView(UserDataManager.getUser().getShoppingList());
            }
        });

        return row;
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View view) {
            textView = view.findViewById(R.id.shoppingListTextView);
            imageView = view.findViewById(R.id.removeItemIcon);
        }
    }

}
