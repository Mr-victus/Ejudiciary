package dev.cavemen.ejudiciary;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FileFirFragment extends Fragment {

    ArrayList<String> arr;

    FirebaseAuth auth;
    Bitmap photo;
    private static final int RESULT_LOAD_IMAGE=1;
    Uri filepath;
    StorageReference storageReference;


    public FileFirFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file_fir, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final EditText subjectfir,firdescription;
        Button submitfir,uploadfir;
        final Spinner policestationspinner;

        arr=new ArrayList<>();

        auth=FirebaseAuth.getInstance();


        subjectfir=view.findViewById(R.id.subhejtfir);
        firdescription=view.findViewById(R.id.firdescription);
        policestationspinner=view.findViewById(R.id.policestationspinner);
        submitfir=view.findViewById(R.id.submitfir);
        uploadfir=view.findViewById(R.id.uploadimage);


        /*DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("police");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (dataSnapshot )
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        arr.add("patia police station");



        ArrayAdapter<String> adapterBloodGroup = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,arr);

        adapterBloodGroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        policestationspinner.setAdapter(adapterBloodGroup);


        uploadfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                // set title
                alertDialogBuilder.setTitle("Choose");
                alertDialogBuilder.setIcon(R.drawable.ic_launcher_foreground);
                // set dialog message
                alertDialogBuilder
                        .setMessage("Camera or Gallery?")
                        .setCancelable(true)

                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, 0);
                                dialog.dismiss();




                            }
                        })
                        .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto , 1);

                                dialog.dismiss();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

            }
        });





//

        submitfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random random=new Random();

                int cid=random.nextInt(9999)+100000;
                int fir=random.nextInt(9999)+100000;
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(auth.getCurrentUser().getUid()).child("cases").child(""+cid);
                Map map=new HashMap();
                map.put("firno",fir);
                map.put("firsubject",subjectfir.getText().toString());
                map.put("firdescription",firdescription.getText().toString());
                map.put("firtimestamp",System.currentTimeMillis());
                map.put("epoch",System.currentTimeMillis()/1000);
                map.put("policestation",policestationspinner.getSelectedItem().toString());

                reference.updateChildren(map);


                DatabaseReference reference11=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(auth.getCurrentUser().getUid()).child("cases").child(""+cid).child("events");
                Map map2=new HashMap();
                map2.put(String.valueOf(System.currentTimeMillis()/1000),"police");
                reference11.updateChildren(map2);

                Map nmap=new HashMap();
                DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("cases").child(""+cid);
                nmap.put("uid",auth.getCurrentUser().getUid());
                nmap.put("pid",policestationspinner.getSelectedItem().toString());
                reference1.updateChildren(nmap);


                DatabaseReference reference2=FirebaseDatabase.getInstance().getReference().child("firs").child(""+cid);
                Map map1=new HashMap();
                map1.put("pid",policestationspinner.getSelectedItem().toString());
                map1.put("uid",auth.getCurrentUser().getUid());
                reference2.updateChildren(map1);



                if(filepath!=null)
                {
                    uploadImage(String.valueOf(fir));
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {

            case 0:
                if(resultCode == RESULT_OK){
                    photo = (Bitmap) data.getExtras().get("data");
                    filepath=getImageUri(getContext(),photo);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    filepath = data.getData();
                }

                break;

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public void uploadImage(String fir) {

        if(filepath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            storageReference = FirebaseStorage.getInstance().getReference();


            StorageReference ref = storageReference.child("fir").child("users").child(fir).child("fir");
            ref.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();



                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }

    }
}
