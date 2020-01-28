package com.ifiveuv.indsmart.UI.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ifiveuv.indsmart.R;
import com.ifiveuv.indsmart.UI.Masters.Model.UomModel;

import java.util.List;

public class UOMAdapter extends ArrayAdapter<UomModel> {

    int productId;
    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private List<UomModel> values;
    private int selectedPostion = -1;

    public UOMAdapter(Context context, int textViewResourceId,
                      List<UomModel> values, int productId) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
        this.productId = productId;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public UomModel getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(context.getResources().getColor(R.color.black));
//        label.setTextSize(MukthaEngine.myInstance.getDPPixel(15,context));
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        if (values.get(position).getUom_id() == -1) {
            label.setText("--select--");
        } else {
            label.setText(values.get(position).getUom_name());
        }
        label.setSingleLine();
//        label.setTypeface(MukthaEngine.myInstance.getCommonTypeFace(context));
        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(context.getResources().getColor(R.color.black));
        label.setTextSize(15);
        label.setSingleLine(true);
        if (values.get(position).getUom_id() == -1) {
            label.setText("");
            label.setPadding(0, 0, 0, 0);
            label.setHeight(0);
        } else {
            label.setText(values.get(position).getUom_name());
            label.setPadding(15, 15, 15, 15);
        }

        if (selectedPostion == position) {
            label.setBackgroundColor(context.getResources().getColor(R.color.dark_green));
            label.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            label.setBackgroundColor(context.getResources().getColor(R.color.white));
            label.setTextColor(context.getResources().getColor(R.color.black));
        }
        return label;
    }

    public void setPosition(int position) {
        selectedPostion = position;
    }
}