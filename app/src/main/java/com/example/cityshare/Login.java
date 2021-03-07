package com.example.cityshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.regex.Pattern;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText email,password;

    private Button connexion;
    private ProgressBar pb;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        mAuth=FirebaseAuth.getInstance();
        email=(EditText)findViewById(R.id.editTextTextPersonName);
        password=(EditText) findViewById(R.id.editTextTextPassword3);
        pb=(ProgressBar)findViewById(R.id.progressBar2);

        connexion=(Button)findViewById(R.id.btn2);

        connexion.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn2:
                seConnecter();
                break;
            case R.id.textView:
                startActivity(new Intent(this,MainActivity.class));
                break;
        }

    }

    private void seConnecter() {
        String em=email.getText().toString().trim();
        String pass=password.getText().toString().trim();

        if(em.isEmpty())
        {
            email.setError("champs requis");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(em).matches())
        {
            email.setError("invalide format d'email");
            email.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            password.setError("champs requié");
            password.requestFocus();
            return;
        }
        Intent i=new Intent(this,Home.class);
        pb.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser us=FirebaseAuth.getInstance().getCurrentUser();
                    pb.setVisibility(View.GONE);
                    if(us.isEmailVerified())
                    {
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        us.sendEmailVerification();
                        Toast.makeText(Login.this,"consulter votre email pour vérifier votre compte",Toast.LENGTH_LONG).show();
                    }


                }
                else
                {

                    Toast.makeText(Login.this,"email ou mot de passe invalide",Toast.LENGTH_LONG).show();
                    pb.setVisibility(View.GONE);



                }
            }
        });


    }
}