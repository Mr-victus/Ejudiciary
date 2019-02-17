package dev.cavemen.ejudiciary;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AllAdvocateFragmentAdapter extends RecyclerView.Adapter<AllAdvocateFragmentAdapter.viewholder> {

    ArrayList<String> name,type,creditscore,yearsofex;


    Context context;
    View view;
    int l=0;
    int d=0;


    public AllAdvocateFragmentAdapter(ArrayList<String> name, ArrayList<String> type, ArrayList<String> creditscore, ArrayList<String> yearsofex, Context context) {
        this.name = name;
        this.type = type;
        this.creditscore = creditscore;
        this.yearsofex = yearsofex;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.customalladvocates, parent, false);


        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {


        holder.type.setText("Type : "+type.get(position));
        holder.name.setText("Name :"+name.get(position));
        holder.creditscore.setText("Credit Score :"+creditscore.get(position));
        holder.yearsofex.setText("Years of Ex :"+yearsofex.get(position));





    }


    @Override
    public int getItemCount() {
        return name.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        TextView file;
        ImageView custom;
        Button callbtn;
        Dialog dialog;
        Layout layout;
        TextView name,type,creditscore,yearsofex;
        EditText editText;
        int l,d;
        public viewholder(View itemView) {
            super(itemView);

            type=itemView.findViewById(R.id.type);
            name=itemView.findViewById(R.id.name);
            creditscore=itemView.findViewById(R.id.creditscore);
            yearsofex=itemView.findViewById(R.id.yearsofex);
            l=0;
            d=0;


        }

    }

}


