package com.example.partymaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button inventoryButton;
    private Button friendsButton;
    private Button partiesButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inventoryButton = findViewById(R.id.inventory_button);
        friendsButton = findViewById(R.id.friends_button);
        partiesButton = findViewById(R.id.parties_button);
        logoutButton = findViewById(R.id.logout_button);

        final int userId = getIntent().getIntExtra("userId", -1);

        inventoryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InventoryActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        friendsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FriendsActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        partiesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PartiesActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


}
