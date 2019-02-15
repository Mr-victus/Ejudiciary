package dev.cavemen.ejudiciary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupUpActivity extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_up);


        final EditText name,email,password,confirmpass,phonenumber,aadhar;
        Button signup;

        auth=FirebaseAuth.getInstance();

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.passsword);
        confirmpass=findViewById(R.id.confirmpassword);
        phonenumber=findViewById(R.id.phonenumber);
        aadhar=findViewById(R.id.aadharnumber);

        signup=findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child("users").child(auth.getCurrentUser().getUid());
                            Map map = new HashMap();
                            map.put("name", name.getText().toString());
                            map.put("email", email.getText().toString());
                            map.put("address", "BBSR");
                            map.put("phone", phonenumber.getText().toString());
                            map.put("aadhar", aadhar.getText().toString());

                            reference.updateChildren(map);

                            Intent i=new Intent(SignupUpActivity.this,LandingPage.class);
                            startActivity(i);


                        }
                    }
                });
            }
        });



    }
}
