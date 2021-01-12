package com.example.partymaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class InventoryAdapter extends ArrayAdapter<UserDrink> {

    public InventoryAdapter(Context context, List<UserDrink> inventory) {
        super(context, R.layout.adapter_inventory, inventory);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position.
        UserDrink drink = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_inventory, parent, false);
        }

        if (position < getCount()) {
            TextView drinkIdView = convertView.findViewById(R.id.drink_id_view);
            String drinkId = drink == null ? "Id: null" : "Id: " + drink.getDrinkId();
            drinkIdView.setText(drinkId);

            TextView drinkNameView = convertView.findViewById(R.id.drink_name_view);
            String drinkName = drink == null ? "Name: null" : "Name: " + drink.getDrinkName();
            drinkNameView.setText(drinkName);

            TextView quantityView = convertView.findViewById(R.id.quantity_view);
            String quantity = drink == null ? "Quantity: null" : "Quantity: " + drink.getQuantity() + "";
            quantityView.setText(quantity);
        }

        return convertView;
    }
}
