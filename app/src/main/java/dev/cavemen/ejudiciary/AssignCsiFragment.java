package dev.cavemen.ejudiciary;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AssignCsiFragment extends Fragment {


    public AssignCsiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assign_csi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText des,med;

        final String casno = getArguments().getString("casenumber");
        final String uid = getArguments().getString("uid");

        des=view.findViewById(R.id.csidescription);
        med=view.findViewById(R.id.subhejtcsi);
        Button submit=view.findViewById(R.id.submitcsi);
        Button upload=view.findViewById(R.id.uploadimagecsi);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference reference4=FirebaseDatabase.getInstance().getReference().child("cases").child(casno);
                Map map4=new HashMap();
                map4.put("status","csi assigned");
                reference4.updateChildren(map4);
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("firs").child(casno);
                Map map=new HashMap();
                map.put("status","csi assigned");
                reference.updateChildren(map);

                DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(uid).child("cases").child(casno).child("events");
                Map map1=new HashMap();
                map1.put(""+System.currentTimeMillis()/1000,"csi");
                reference1.updateChildren(map1);

                DatabaseReference reference22=FirebaseDatabase.getInstance().getReference().child("users").child("csi").child("rmPWzbkBDFNgKSBOBBXgblvCHYu2").child("cases").child(casno);
                Map map11=new HashMap();

                map11.put(uid,0);
                reference22.updateChildren(map11);

                DatabaseReference reference2=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(uid).child("cases").child(casno);
                Map map2=new HashMap();
                map2.put("seizure list",des.getText().toString());
                map2.put("medical report",med.getText().toString());
                map2.put("epoch",System.currentTimeMillis()/1000);
                reference2.updateChildren(map2);


            }
        });
    }
}
