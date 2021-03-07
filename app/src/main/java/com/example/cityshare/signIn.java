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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signIn extends AppCompatActivity implements View.OnClickListener {
    private TextView banner ;
    private EditText nom;
    private EditText prenom;
    private EditText email;
    private EditText num;
    private EditText password;
    private Button inscrire;
    private ProgressBar pb;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        nom=(EditText)findViewById(R.id.editTextTextPersonName3);
        prenom=(EditText)findViewById(R.id.editTextTextPersonName4);
        email=(EditText)findViewById(R.id.editTextTextEmailAddress);
        num=(EditText)findViewById(R.id.editTextPhone);
        password=(EditText)findViewById(R.id.editTextTextPassword);
        pb=(ProgressBar)findViewById(R.id.progressBar);
        banner = (TextView)findViewById(R.id.textView3);
        inscrire=(Button) findViewById(R.id.button2);
        inscrire.setOnClickListener(this);
        banner.setOnClickListener(this);
            }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.textView3:
                startActivity(new Intent(this,MainActivity.class));break;
            case R.id.button2:
                inscrireUser();break;

        }

    }

    private void inscrireUser() {
        String name=nom.getText().toString().trim();
        String lastName=prenom.getText().toString().trim();
        String em=email.getText().toString().trim();
        String nu=num.getText().toString().trim();
        String pass=password.getText().toString().trim();

        if(name.isEmpty())
        {
            nom.setError("champs requié");
            nom.requestFocus();
            return;
        }

        if(lastName.isEmpty())
        {
            prenom.setError("champs requié");
            prenom.requestFocus();
            return;
        }
        if(em.isEmpty())
        {
            email.setError("champs requié");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(em).matches())
        {
            email.setError("invalide format d'email");
            email.requestFocus();
            return;
        }
        if(nu.isEmpty())
        {
            num.setError("champs requié");
            num.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            password.setError("champs requié");
            password.requestFocus();
            return;
        }
        if(pass.length()<6)
        {
            password.setError("mot de passe trop court");
            password.requestFocus();
            return;
        }
        Intent i=new Intent(this,Home.class);
        pb.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(em,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            User us=new User(name,lastName,em,nu,pass);
                           FirebaseDatabase.getInstance().getReference("users")
                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(us)
                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful())
                                           {
                                               Toast.makeText(signIn.this,"utilisateur inscri avec succes!",Toast.LENGTH_LONG).show();
                                               pb.setVisibility(View.GONE);

                                               startActivity(i);
                                               finish();
                                           }
                                           else {
                                               Toast.makeText(signIn.this,"une erreur s'est produite!réesayer",Toast.LENGTH_LONG).show();
                                               pb.setVisibility(View.GONE);
                                           }
                                       }
                                   });

                        }
                        else {

                            pb.setVisibility(View.GONE);
                            email.setError("email déja existant");
                            email.requestFocus();
                        }
                    }
                });




    }
}