package com.example.partymaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        createAccount = findViewById(R.id.create_account_button);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim(); // TODO encrypt.

                int userId = UsersDatabase.get(LoginActivity.this).matchPassword(username, password);

                if (userId != -1) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong credentials!", Toast.LENGTH_LONG).show();
                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim(); // TODO encrypt.

                boolean success = UsersDatabase.get(LoginActivity.this).createUser(username, password);

                if (success) {
                    Toast.makeText(LoginActivity.this, "User created!Please login.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Username already exists!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
