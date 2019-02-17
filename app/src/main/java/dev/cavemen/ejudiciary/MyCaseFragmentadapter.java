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

public class MyCaseFragmentadapter extends RecyclerView.Adapter<MyCaseFragmentadapter.viewholder> {

    ArrayList<String> caseno,status,policestation,firno,court,epoch;


    Context context;
    View view;
    FirebaseAuth auth;
    FragmentManager fragmentManager;


    public MyCaseFragmentadapter(ArrayList<String> caseno, ArrayList<String> status, ArrayList<String> policestation, ArrayList<String> firno, ArrayList<String> court,ArrayList<String> epoch, Context context, FragmentManager fragmentManager) {
        this.caseno = caseno;
        this.status = status;
        this.policestation = policestation;
        this.firno = firno;
        this.court = court;
        this.epoch=epoch;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public MyCaseFragmentadapter() {

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custommycases, parent, false);


        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
        auth = FirebaseAuth.getInstance();

        holder.casenumber.setText("Case no. " + caseno.get(position));
        holder.firno.setText("Fir no :-" + firno.get(position));
        holder.status.setText("Status : " + status.get(position));
        holder.policestation.setText("Police Station :-" + policestation.get(position));
        Long timestamp = Long.valueOf(epoch.get(position)) * 1000;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH) + 1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        int hrs = calendar.get(Calendar.HOUR_OF_DAY);
        final int mins = calendar.get(Calendar.MINUTE);

//        holder.date.setText("Date :- " + "" + mYear + "-" + "" + mMonth + "-" + "" + mDay);
//        holder.time.setText("Time :- " + "" + hrs + ":" + "" + mins);








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
        return caseno.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView casenumber, status, firno, policestation, date, time;
        Dialog dialog;
        Layout layout;
        Button accept, reject;
        Button assigncsi;
        EditText editText;

        public viewholder(View itemView) {
            super(itemView);
            casenumber = (TextView) itemView.findViewById(R.id.caseno);
            status = (TextView) itemView.findViewById(R.id.status);
            firno = (TextView) itemView.findViewById(R.id.firno);
            policestation = itemView.findViewById(R.id.policestation);
//            date = itemView.findViewById(R.id.date);
//            time = itemView.findViewById(R.id.time);

//            assigncsi = itemView.findViewById(R.id.assigncsi);
//
//
//            accept = itemView.findViewById(R.id.accept);
//            reject = itemView.findViewById(R.id.reject);

            //layout=itemView.findViewById(R.id.custom);
            dialog = new Dialog(context);


            // customer_pic=(ImageView) itemView.findViewById(R.id.doctor_pic);

            Log.d("TAAAAG", "kk");

        }

    }
}
