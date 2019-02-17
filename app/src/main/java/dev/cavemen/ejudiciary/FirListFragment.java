package dev.cavemen.ejudiciary;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirListFragment extends Fragment {

    FirebaseAuth auth;
    RecyclerView recyclerView;
    ArrayList<String> casenumberr, firno, firdescripton,name,epoch,uid,statuss;
    FirListPoliceAdapter adapter;

    public FirListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fir_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        casenumberr=new ArrayList<>();
        firno=new ArrayList<>();
        firdescripton=new ArrayList<>();
        name=new ArrayList<>();
        epoch=new ArrayList<>();
        uid=new ArrayList<>();
        statuss=new ArrayList<>();

         recyclerView=view.findViewById(R.id.recyclerview);


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("firs");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.clear();
                firdescripton.clear();
                firno.clear();
                epoch.clear();
                casenumberr.clear();
                uid.clear();
                statuss.clear();
                for(final DataSnapshot snapshot:dataSnapshot.getChildren()) {

                    final String caseno=snapshot.getKey();
                    final String status=snapshot.child("status").getValue().toString();
                    Toast.makeText(getContext(),""+caseno,Toast.LENGTH_SHORT).show();
                    DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(snapshot.child("uid").getValue().toString());
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            recyclerView.removeAllViews();
                            name.add(""+dataSnapshot.child("name").getValue().toString());
                            firno.add(""+dataSnapshot.child("cases").child(caseno).child("firno").getValue().toString());
                            firdescripton.add(""+dataSnapshot.child("cases").child(caseno).child("firdescription").getValue().toString());
                            epoch.add(""+dataSnapshot.child("cases").child(caseno).child("epoch").getValue().toString());
                            casenumberr.add(caseno);
                            statuss.add(status);
                            uid.add(snapshot.child("uid").getValue().toString());

                            FragmentManager fragmentManager=getFragmentManager();

                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter = new FirListPoliceAdapter( casenumberr,firno,firdescripton,name,epoch,uid,statuss,getContext(),fragmentManager);
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
