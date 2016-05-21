package org.familysearch.sampleapp.activity;

import org.familysearch.sampleapp.R;
import org.familysearch.sampleapp.listener.LoginListener;
import org.familysearch.sampleapp.model.User;
import org.familysearch.sampleapp.service.LoginServices;
import org.familysearch.sampleapp.utils.Utilities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = (EditText) findViewById(R.id.login_username);
        passwordEditText = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(usernameEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(passwordEditText.getWindowToken(), 0);

                if ( !username.isEmpty() && !password.isEmpty())
                {
                    LoginServices loginTask = new LoginServices(LoginActivity.this);
                    loginTask.setOnLoginListener(LoginActivity.this);
                    loginTask.execute(username, password);
                }
                else
                {
                    // TODO: error message for empty username or password
                }
            }
        });
    }

    @Override
    public void onLoginSucceeded(User user) {

        if(user != null)
        {
            Intent intent = new Intent(this, TreeActivity.class);
            intent.putExtra(Utilities.KEY_USER, user);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, getString(R.string.login_error_token), Toast.LENGTH_LONG).show();
        }
    }
}
