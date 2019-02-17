package dev.cavemen.ejudiciary;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LegalAdviceAdapter extends RecyclerView.Adapter<LegalAdviceAdapter.viewholder> {

    ArrayList<String> advice,name,phone;


    Context context;
    View view;
    FirebaseAuth auth;
    FragmentManager fragmentManager;

    public LegalAdviceAdapter(ArrayList<String> advice, ArrayList<String> name, ArrayList<String> phone, Context context) {
        this.advice = advice;
        this.name = name;
        this.phone = phone;
        this.context = context;
    }

    public LegalAdviceAdapter() {

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.customlegaladvice, parent, false);


        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
        auth=FirebaseAuth.getInstance();


        holder.advice.setText(""+advice.get(position));
        holder.phone.setText("Phone No. : "+phone.get(position));
        holder.name.setText("Name :"+name.get(position));


    }


    @Override
    public int getItemCount() {
        return advice.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        TextView advice,name,phone;
        Dialog dialog;
        Layout layout;
        Button contact;
        EditText editText;
        public viewholder(View itemView) {
            super(itemView);
            advice=(TextView)itemView.findViewById(R.id.advice);
            name=(TextView)itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.phonenumber);

            contact=itemView.findViewById(R.id.contact);

            //layout=itemView.findViewById(R.id.custom);
            dialog=new Dialog(context);




            // customer_pic=(ImageView) itemView.findViewById(R.id.doctor_pic);

            Log.d("TAAAAG","kk");

        }

    }

}

