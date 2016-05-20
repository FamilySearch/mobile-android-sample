package org.familysearch.sampleapp;

import org.familysearch.sampleapp.service.LoginServices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView usernameTextView, passwordTextView;
    private Button loginButton;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameTextView = (TextView) findViewById(R.id.login_username);
        passwordTextView = (TextView) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameTextView.getText().toString();
                password = passwordTextView.getText().toString();

                if ( !username.isEmpty() && !password.isEmpty())
                {
                    LoginServices loginTask = new LoginServices();
                    loginTask.execute(username, password);
                }
                else
                {
                    // TODO: error message for empty username or password
                }
            }
        });
    }
}
