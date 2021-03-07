package com.example.cityshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private TextView inscrire;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        btnLogin= (Button)findViewById(R.id.btn);
        btnLogin.setOnClickListener(this);
        inscrire=(TextView)findViewById(R.id.inscrire);
        inscrire.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn:
                startActivity(new Intent(this, Login.class));break;
            case R.id.inscrire:
                startActivity(new Intent(this, signIn.class));break;


        }

    }
}