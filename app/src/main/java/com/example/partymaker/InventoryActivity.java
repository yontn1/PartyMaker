package com.example.partymaker;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class InventoryActivity extends AppCompatActivity implements Observer {

    private EditText drinkIdInput;
    private EditText quantityInput;
    private Button changeDrinkButton;
    private ListView drinksView;

    private InventoryAdapter adapter;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        InventoryDatabase inventoryDatabase = InventoryDatabase.get(this);
        inventoryDatabase.addObserver(this);

        drinkIdInput = findViewById(R.id.drink_id_input);
        quantityInput = findViewById(R.id.quantity_input);
        changeDrinkButton = findViewById(R.id.change_drink_button);
        drinksView = findViewById(R.id.drinks_view);

        userId = getIntent().getIntExtra("userId", -1);

        refreshAdapter();

        changeDrinkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    int drinkId = Integer.parseInt(drinkIdInput.getText().toString().trim());
                    double quantity = Double.parseDouble(quantityInput.getText().toString().trim());

                    if (drinkId < 1 || drinkId > 3) {
                        Toast.makeText(InventoryActivity.this, "1 < drinkId < 3", Toast.LENGTH_LONG).show();
                        return;
                    }

                    // TODO: add check for negative quantity.

                    String message = InventoryDatabase.get(InventoryActivity.this).changeDrinkQuantity(userId, drinkId, quantity);
                    Toast.makeText(InventoryActivity.this, message, Toast.LENGTH_LONG).show();

                    adapter.notifyDataSetChanged();
                } catch (NumberFormatException exception) {
                    Toast.makeText(InventoryActivity.this, "Invalid input: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void refreshAdapter() {
        List<UserDrink> drinks = InventoryDatabase.get(this).getDrinksForUser(userId);

        adapter = new InventoryAdapter(this, drinks);
        drinksView.setAdapter(adapter);

        drinksView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                UserDrink drink = (UserDrink) parent.getItemAtPosition(position);
                String drinkName = drink.getDrinkName();

                // Perform a google search.
                String url = "https://www.google.com/search?source=hp&ei=EvjKXP27MIuFmwXz2ZlQ&q=" + drinkName
                        + "&btnK=Google+Search&oq=something&gs_l=psy-ab.3...0.0..5181...0.0..0.0.0.......0......gws-wiz.";

                FetchHttpTask fetchHttp = new FetchHttpTask();
                fetchHttp.execute(url);

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private class FetchHttpTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return new NetworkFetcher().fetchItems(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(InventoryActivity.this, "success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InventoryActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        refreshAdapter();
    }
}
