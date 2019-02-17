package dev.cavemen.ejudiciary;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
public class LegalAdviceFragment extends Fragment {


    FirebaseAuth auth;
    EditText prescription;
    Button submit;

    public LegalAdviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        auth=FirebaseAuth.getInstance();

        prescription=view.findViewById(R.id.prescriptionedittext);
        submit=view.findViewById(R.id.prescriptionsubmitbtn);







        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medicines = ""+prescription.getText().toString();


                DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("legaladvice").child(auth.getCurrentUser().getUid());
                Map map=new HashMap();
                map.put("advice",medicines);
                reference1.updateChildren(map);
                Fragment fragment=new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }
}
