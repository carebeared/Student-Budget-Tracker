package com.magic.akash.magicbox;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AKASH on 2/15/2017.
 */
public class CustomAdapter_star extends ArrayAdapter<DataModel_star> implements View.OnClickListener{

    private ArrayList<DataModel_star> dataSet;
    Context mContext;

    @Override
    public void onClick(View view) {

    }


    // View lookup cache
    private static class ViewHolder {
        TextView Currency;
        TextView amount;
        TextView place;
        TextView date;
    }

    public CustomAdapter_star(ArrayList<DataModel_star> data, Context context) {
        super(context, R.layout.row_item_star, data);
        this.dataSet = data;
        this.mContext=context;

    }



    //private int lastPosition = 10000;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel_star dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_star, parent, false);
            viewHolder.Currency = (TextView) convertView.findViewById(R.id.currency);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.amount);
            viewHolder.place = (TextView) convertView.findViewById(R.id.place);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(mContext, (position == lastPosition) ? R.anim.bottom : R.anim.top);
        //result.startAnimation(animation);
        //lastPosition = position;

        viewHolder.Currency.setText(dataModel.getcurrency());
        viewHolder.amount.setText(dataModel.getamount());
        viewHolder.place.setText(dataModel.getplace());
        viewHolder.date.setText(dataModel.getdate());
        // Return the completed view to render on screen
        return convertView;
    }
}