package com.example.rohit.messmanager;

/**
 * Created by Rohit on 5/09/19.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class MealArrayList extends ArrayAdapter<Meal> {

    private Activity context;
    private List<Meal> appoList;

    public MealArrayList(Activity context, List<Meal> appoList){
        super(context, R.layout.meal_list_layout, appoList);
        this.context=context;
        this.appoList=appoList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.meal_list_layout, null, true);

        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.textViewTilte);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDesc);

        Meal meal = appoList.get(position);
        textViewTitle.setText(meal.title );
        textViewDate.setText(meal.description+ "\n" + meal.getDate());

        return listViewItem;
    }
}

