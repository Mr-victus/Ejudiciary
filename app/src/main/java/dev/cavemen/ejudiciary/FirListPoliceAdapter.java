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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FirListPoliceAdapter extends RecyclerView.Adapter<FirListPoliceAdapter.viewholder> {

    ArrayList<String> casenumberr, firno, firdescripton,name,status,epoch,uid;


    Context context;
    View view;
    FirebaseAuth auth;
    FragmentManager fragmentManager;

    public FirListPoliceAdapter(ArrayList<String> casenumberr, ArrayList<String> firno, ArrayList<String> firdescripton, ArrayList<String> name, ArrayList<String> epoch,ArrayList<String>uid,ArrayList<String>status, Context context, FragmentManager fragmentManager) {
        this.casenumberr = casenumberr;
        this.firno = firno;
        this.firdescripton = firdescripton;
        this.name = name;
        this.epoch = epoch;
        this.uid=uid;
        this.status=status;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public FirListPoliceAdapter() {

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.customnewrecyclerview, parent, false);


        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
        auth=FirebaseAuth.getInstance();

        holder.casenumber.setText("Case no. "+casenumberr.get(position));
        holder.fir.setText("Fir no :-"+firno.get(position));
        holder.description.setText("Fir Description"+firdescripton.get(position));
        holder.name.setText("Name :-"+name.get(position));
        Long timestamp = Long.valueOf(epoch.get(position))*1000;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH) + 1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        int hrs = calendar.get(Calendar.HOUR_OF_DAY);
        final int mins = calendar.get(Calendar.MINUTE);

        holder.date.setText("Date :- "+"" + mYear + "-" + "" + mMonth + "-" + "" + mDay);
        holder.time.setText("Time :- "+"" + hrs + ":" + "" + mins);


        if(status.get(position).equals("police"))
        {
            holder.accept.setVisibility(View.VISIBLE);
            holder.reject.setVisibility(View.VISIBLE);
        }
        else  {
            holder.accept.setVisibility(View.INVISIBLE);
            holder.reject.setVisibility(View.INVISIBLE);

            holder.assigncsi.setVisibility(View.VISIBLE);

        }


        holder.assigncsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle();
                bundle.putString("casenumber", casenumberr.get(position));
                bundle.putString("uid",uid.get(position));
                //bundle.putString("instanceid",instanceidd.get(position));

                Fragment fragment=new AssignCsiFragment();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.fragment_container_police,fragment).commit();
               // fragmentManager.beginTransaction().replace()
            }
        });



        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(uid.get(position)).child("cases").child(casenumberr.get(position)).child("events");
                Map map1=new HashMap();
                map1.put(String.valueOf(System.currentTimeMillis()/1000),"police accepted");
                reference1.updateChildren(map1);

                DatabaseReference reference2=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(uid.get(position)).child("cases").child(casenumberr.get(position));
                Map map2=new HashMap();
                map2.put("epoch",System.currentTimeMillis()/1000);
                reference2.updateChildren(map2);


                DatabaseReference reference3=FirebaseDatabase.getInstance().getReference().child("firs").child(casenumberr.get(position));
                Map map3=new HashMap();
                map3.put("status","police accepted");
                reference3.updateChildren(map3);

            }
        });



        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(uid.get(position)).child("cases").child(casenumberr.get(position)).child("events");
                Map map1=new HashMap();
                map1.put(String.valueOf(System.currentTimeMillis()/1000),"police rejected");
                reference1.updateChildren(map1);

                DatabaseReference reference2=FirebaseDatabase.getInstance().getReference().child("users").child("users").child(uid.get(position)).child("cases").child(casenumberr.get(position));
                Map map2=new HashMap();
                map2.put("epoch",System.currentTimeMillis()/1000);
                reference2.updateChildren(map2);

                DatabaseReference reference3=FirebaseDatabase.getInstance().getReference().child("firs").child(casenumberr.get(position));
                Map map3=new HashMap();
                map3.put("status","police rejected");
                reference3.updateChildren(map3);
            }
        });







        // holder.customer_pic.setImageResource(customerpic.get(position));


       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                c.setText(casenumberr.get(pos));
                am.setText(amountt.get(pos));
                re.setText(refidd.get(pos));

                a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"Accept button clicked",Toast.LENGTH_SHORT).show();
                    }
                });

                r.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"Reject button clicked",Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });*/
    }


    @Override
    public int getItemCount() {
        return casenumberr.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        TextView casenumber,name,fir,description,date,time;
        Dialog dialog;
        Layout layout;
        Button accept,reject;
        Button assigncsi;
        EditText editText;
        public viewholder(View itemView) {
            super(itemView);
            casenumber=(TextView)itemView.findViewById(R.id.caseno);
            name=(TextView)itemView.findViewById(R.id.name);
            fir=(TextView)itemView.findViewById(R.id.firno);
            description=itemView.findViewById(R.id.description);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);

            assigncsi=itemView.findViewById(R.id.assigncsi);


            accept=itemView.findViewById(R.id.accept);
            reject=itemView.findViewById(R.id.reject);

            //layout=itemView.findViewById(R.id.custom);
            dialog=new Dialog(context);




            // customer_pic=(ImageView) itemView.findViewById(R.id.doctor_pic);

            Log.d("TAAAAG","kk");

        }

    }

}

