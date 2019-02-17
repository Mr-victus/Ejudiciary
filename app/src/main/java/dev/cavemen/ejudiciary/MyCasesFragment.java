package dev.cavemen.ejudiciary;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCasesFragment extends Fragment {

    FirebaseAuth auth;

    ArrayList<String> caseno,status,policestation,firno,court,epoch;
    public MyCasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_cases, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth=FirebaseAuth.getInstance();

        caseno=new ArrayList<>();
        status=new ArrayList<>();
        policestation=new ArrayList<>();
        firno=new ArrayList<>();
        court=new ArrayList<>();
        epoch=new ArrayList<>();
        final RecyclerView recyclerView=view.findViewById(R.id.mycasesrecyclerview);

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(auth.getCurrentUser().getUid()).child("cases");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("cases").child(snapshot.getKey());
                    //status.add("status");
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            status.add(""+dataSnapshot.child("status").getValue().toString());
                            policestation.add(snapshot.child("policestation").getValue().toString());
                            firno.add(snapshot.child("firno").getValue().toString());
                            court.add("");
                            epoch.add(snapshot.child("epoch").getValue().toString());
                            caseno.add(snapshot.getKey());

                            FragmentManager fragmentManager=getFragmentManager();

                            MyCaseFragmentadapter adapter=new MyCaseFragmentadapter(caseno,status,policestation,firno,court,epoch,getContext(),fragmentManager);
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
