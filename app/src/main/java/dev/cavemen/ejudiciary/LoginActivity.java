package dev.cavemen.ejudiciary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();
        final EditText email,password;
        TextView signup;
        Spinner loginspinner;

        signup=findViewById(R.id.signup);
        email=findViewById(R.id.emaillogin);
        password=findViewById(R.id.passswordlogin);

        Button login=findViewById(R.id.login);

        ArrayList<String> arr=new ArrayList<>();
        arr.add("user");
        arr.add("police");
        arr.add("judge");
        arr.add("advocate");
        ArrayAdapter<String> adapterBloodGroup = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_spinner_item,arr);

        adapterBloodGroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {

                                DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(auth.getCurrentUser().getUid());
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Intent i=new Intent(LoginActivity.this,LandingPage.class);
                                            startActivity(i);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("users").child("advocate").child(auth.getCurrentUser().getUid());
                                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Intent i=new Intent(LoginActivity.this,LandingPage.class);
                                            startActivity(i);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                DatabaseReference reference2=FirebaseDatabase.getInstance().getReference().child("users").child("judges").child(auth.getCurrentUser().getUid());

                                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Intent i=new Intent(LoginActivity.this,LandingPage.class);
                                            startActivity(i);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                DatabaseReference reference3=FirebaseDatabase.getInstance().getReference().child("users").child("police").child(auth.getCurrentUser().getUid());
                                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Intent i=new Intent(LoginActivity.this,LandingPagePolice.class);
                                            startActivity(i);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                /*Intent i=new Intent(LoginActivity.this,LandingPage.class);
                                startActivity(i);*/
                            }
                    }
                });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(LoginActivity.this,SignupUpActivity.class);
                startActivity(i);
            }
        });



    }
}
