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
public class LegalAdviceListFragment extends Fragment {


    public LegalAdviceListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_legal_advice_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList<String> advice=new ArrayList<>();
        final ArrayList<String> name=new ArrayList<>();
        final ArrayList<String> phone=new ArrayList<>();

        final RecyclerView recyclerView=view.findViewById(R.id.legaladvicerecyclerview);

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("legaladvice");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    final String n=snapshot.child("advice").getValue().toString();

                    DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(snapshot.getKey());
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            advice.add(n);
                            name.add(dataSnapshot.child("name").getValue().toString());
                            phone.add(dataSnapshot.child("phone").getValue().toString());


                            LegalAdviceAdapter adapter=new LegalAdviceAdapter(advice,name,phone,getContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            recyclerView.setAdapter(adapter);



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
