package com.raymaso.login1.Activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.raymaso.login1.R;
import com.raymaso.login1.sql.DatabaseHelper;
import com.raymaso.login1.sql.DbManager;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper database;
    private Button add;
    private Button login;
    private EditText email;
    private EditText pass;
    private DbManager dbManager;
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.pass);
        add = (Button)findViewById(R.id.agregar);
        login = (Button)findViewById(R.id.login);

        dbManager = new DbManager(this);
        dbManager.open();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.insert(email.getText().toString().trim(), pass.getText().toString().trim());
                Toast.makeText(LoginActivity.this, "Agregado", Toast.LENGTH_SHORT).show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(dbManager.checkUser(email.getText().toString().trim(), pass.getText().toString().trim())){
                        startAnimation();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent myIntent = new Intent(LoginActivity.this, SecondActivity.class);
                                startActivity(myIntent);
                            }
                        }, 1000);

                    }
                    else
                        Toast.makeText(LoginActivity.this, "No está", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void startAnimation() {
        progress = (ProgressBar) findViewById(R.id.progressBar);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progress, "progress", 0, 100);
        progressAnimator.setDuration(1000);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }
}
