package dev.cavemen.ejudiciary;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllAdvocatesFragment extends Fragment {
    ArrayList<String> name,type,creditscore,yearsofex;
    RecyclerView recyclerView;

    public AllAdvocatesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_advocates, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.alladvocatesrecyclerview);

        name=new ArrayList<>();
        type=new ArrayList<>();
        creditscore=new ArrayList<>();
        yearsofex=new ArrayList<>();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("advocate");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name.clear();
                type.clear();
                creditscore.clear();
                yearsofex.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    name.add(""+snapshot.child("name").getValue().toString());
                    type.add(""+snapshot.child("category").getValue().toString());
                    creditscore.add(""+snapshot.child("credit score").getValue().toString());
                    yearsofex.add("5 yrs");



                }
                String tempc,tempn,tempt,tempy;

                for (int i = 0; i < creditscore.size(); i++)
                {
                    for (int j = i + 1; j < creditscore.size(); j++)
                    {
                        if (Double.parseDouble(creditscore.get(i)) < Double.parseDouble(creditscore.get(j)))
                        {
                            tempc =creditscore.get(i) ;
                            tempn=name.get(i);
                            tempt=type.get(i);
                            tempy=yearsofex.get(i);

                            creditscore.set(i,creditscore.get(j));
                            name.set(i,name.get(j));
                            type.set(i,type.get(j));
                            yearsofex.set(i,yearsofex.get(j));



                            creditscore.set(j,tempc);
                            name.set(j,tempn);
                            type.set(j,tempt);
                            yearsofex.set(j,tempy);

                        }
                    }
                }
                AllAdvocateFragmentAdapter adapter = new AllAdvocateFragmentAdapter(name,type,creditscore,yearsofex,getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
