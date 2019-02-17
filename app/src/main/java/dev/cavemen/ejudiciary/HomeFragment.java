package dev.cavemen.ejudiciary;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    CustomPhotoAdapter customPhotoAdapter;
    Dialog dialog;
    FirebaseAuth auth;
    Button myadvocates;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager;
        viewPager = view.findViewById(R.id.viewpager);
        customPhotoAdapter=new CustomPhotoAdapter(getContext());
        viewPager.setAdapter(customPhotoAdapter);
        auth=FirebaseAuth.getInstance();
        Button initiatecase=view.findViewById(R.id.initiatecase);

        Button legaladvice=view.findViewById(R.id.legaladvice);

        final Button addadvocate=view.findViewById(R.id.addadvocate);

        myadvocates=view.findViewById(R.id.myadvocates);

        Button findadvocate=view.findViewById(R.id.findadvocate);



        findadvocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new AllAdvocatesFragment();

                getFragmentManager().beginTransaction().addToBackStack("HomeFragment").replace(R.id.fragment_container,fragment).commit();
            }
        });


        Button mycases=view.findViewById(R.id.mycases);


        legaladvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new LegalAdviceFragment();

                getFragmentManager().beginTransaction().addToBackStack("HomeFragment").replace(R.id.fragment_container,fragment).commit();
            }
        });




        mycases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new MyCasesFragment();

                getFragmentManager().beginTransaction().addToBackStack("HomeFragment").replace(R.id.fragment_container,fragment).commit();
            }
        });




        myadvocates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new MyadvocatesProfileFragment();

                getFragmentManager().beginTransaction().addToBackStack("HomeFragment").replace(R.id.fragment_container,fragment).commit();
            }
        });
        addadvocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.addmoderatorpopup);

                final EditText code;
                Button submit;
                code=dialog.findViewById(R.id.codeedtittext);
                submit=dialog.findViewById(R.id.addbtnsubmit);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("advocate");
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                                {
                                    Log.d("Refferaal",snapshot.child("referral").getValue().toString());
                                    if(snapshot.child("referral").getValue().toString().equals(code.getText().toString()))
                                    {

                                        Map map=new HashMap();
                                        map.put(snapshot.getKey(),snapshot.child("name").getValue().toString());

                                        DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child("users").child("patients").child(auth.getCurrentUser().getUid()).child("moderators");
                                        databaseReference2.updateChildren(map);


                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("users").child("advocate").child(snapshot.getKey()).child("users");
                                        Map kmap=new HashMap();
                                        kmap.put(auth.getCurrentUser().getUid(),0);
                                        databaseReference1.updateChildren(kmap);


                                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(auth.getCurrentUser().getUid()).child("events");
                                        Map map1=new HashMap();
                                        map1.put(""+System.currentTimeMillis()/1000,"Advocate added");
                                        databaseReference.updateChildren(map1);

                                        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(auth.getCurrentUser().getUid());
                                        Map map2=new HashMap();
                                        map2.put("advocate",snapshot.child("name").getValue().toString());
                                        map2.put("advocateuid",snapshot.getKey());
                                        reference1.updateChildren(map2);

                                       // DatabaseReference reference2=FirebaseDatabase.getInstance().getReference().child("users").child("advocate")


                                        dialog.dismiss();

                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        });


        initiatecase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new FileFirFragment();

                getFragmentManager().beginTransaction().addToBackStack("HomeFragment").replace(R.id.fragment_container,fragment).commit();
            }
        });



    }
}
