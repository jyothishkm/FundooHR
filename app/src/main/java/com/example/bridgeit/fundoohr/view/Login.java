package com.example.bridgeit.fundoohr.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bridgeit.fundoohr.R;

public class Login extends AppCompatActivity {
    private EditText mEmail, mPassword;
    private Button mLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = (EditText) findViewById(R.id.edit_txt_log_email);
        mPassword = (EditText)findViewById(R.id.edit_txt_log_pass);
        mLogin = (Button) findViewById(R.id.button_login);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

    }

    private void validation() {
        String email = mEmail.getText().toString();
        String pass = mPassword.getText().toString();
        Intent intent = new Intent(Login.this, Dashboard.class);
        startActivity(intent);


    }
}
