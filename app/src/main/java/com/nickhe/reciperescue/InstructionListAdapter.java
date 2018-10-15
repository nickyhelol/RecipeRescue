package com.nickhe.reciperescue;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InstructionListAdapter extends ArrayAdapter<String> {

    private String[] instructions;
    private Activity context;

    public InstructionListAdapter(Activity context, String[] instructions) {
        super(context, R.layout.instruction_row, R.id.instructionsTextView, instructions);
        this.instructions = instructions;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        InstructionListAdapter.ViewHolder viewHolder = null;

        if(row == null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            row = layoutInflater.inflate(R.layout.instruction_row, parent, false);
            viewHolder = new InstructionListAdapter.ViewHolder(row);
            row.setTag(viewHolder);
        }else {
            viewHolder = (InstructionListAdapter.ViewHolder)row.getTag();
        }

        if (position % 2 == 1) {
            // Set a background color for ListView regular row/item
            row.setBackgroundColor(Color.parseColor("#d9e0de"));
        }
        viewHolder.textView.setText(instructions[position]);

        return row;
    }

    class ViewHolder
    {
        TextView textView;

        public ViewHolder(View view)
        {
            textView = view.findViewById(R.id.instructionsTextView);
        }
    }
}
