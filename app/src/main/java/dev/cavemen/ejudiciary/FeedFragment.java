package dev.cavemen.ejudiciary;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
public class FeedFragment extends Fragment {

    FirebaseAuth auth;
    ArrayList<String> casedetail;
    RecyclerView recyclerView;


    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        casedetail=new ArrayList<>();
        recyclerView=view.findViewById(R.id.feedrecyclerview);

        EditText searchBox=view.findViewById(R.id.feedsearch);




        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty())
                {
                    setadapter(editable.toString().toLowerCase());
                }
                else {
                    casedetail.clear();

                    recyclerView.removeAllViews();
                }
            }
        });


        /*DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("cases");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                casedetail.clear();
                recyclerView.removeAllViews();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {


                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(snapshot.child("uid").getValue().toString()).child("cases").child(snapshot.getKey());
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(!dataSnapshot.child("current status").getValue().toString().equals(null)) {
                                casedetail.add("" + dataSnapshot.child("current status").getValue().toString());

                                FeedFragmentAdapter adapter = new FeedFragmentAdapter(getContext(), casedetail);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                                recyclerView.setAdapter(adapter);
                            }
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
        });*/











    }

    private void setadapter(final String query)
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("cases");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                casedetail.clear();
                recyclerView.removeAllViews();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {


                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(snapshot.child("uid").getValue().toString()).child("cases").child(snapshot.getKey());
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(!dataSnapshot.child("current status").getValue().toString().equals(null) && dataSnapshot.child("court").getValue().toString().toLowerCase().contains(query.toLowerCase())) {
                                casedetail.add("" + dataSnapshot.child("current status").getValue().toString());

                                FeedFragmentAdapter adapter = new FeedFragmentAdapter(getContext(), casedetail);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                                recyclerView.setAdapter(adapter);
                            }
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
