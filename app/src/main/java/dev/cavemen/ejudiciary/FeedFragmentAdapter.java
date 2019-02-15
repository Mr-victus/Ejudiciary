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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FeedFragmentAdapter extends RecyclerView.Adapter<FeedFragmentAdapter.viewholder> {

    ArrayList<String> casedetail;


    Context context;
    View view;


    public FeedFragmentAdapter(Context context, ArrayList<String> filename) {
        this.context = context;
        this.casedetail = filename;





    }




    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.customfeedrecyclerviewlayout, parent, false);


        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {

        holder.file.setText(casedetail.get(position));






    }


    @Override
    public int getItemCount() {
        return casedetail.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        TextView file;
        ImageView custom;
        Button callbtn;
        Dialog dialog;
        Layout layout;
        Button submitbtn;
        EditText editText;
        public viewholder(View itemView) {
            super(itemView);

            file=itemView.findViewById(R.id.casedetailfeed);


        }

    }

}


