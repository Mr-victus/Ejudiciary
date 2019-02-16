package dev.cavemen.ejudiciary;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyadvocatesProfileFragment extends Fragment {

    FirebaseAuth auth;
    public MyadvocatesProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myadvocates_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth=FirebaseAuth.getInstance();

        final TextView name,type,creditscore,yearsofex;


        name=view.findViewById(R.id.name);
        type=view.findViewById(R.id.type);
        creditscore=view.findViewById(R.id.creditscore);
        yearsofex=view.findViewById(R.id.yearsofex);



        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(auth.getCurrentUser().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String aid=dataSnapshot.child("advocateuid").getValue().toString();


                DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("users").child("advocate").child(aid);

                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        name.setText("Name :"+dataSnapshot.child("name").getValue().toString());
                        type.setText("Type :"+dataSnapshot.child("category").getValue().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
