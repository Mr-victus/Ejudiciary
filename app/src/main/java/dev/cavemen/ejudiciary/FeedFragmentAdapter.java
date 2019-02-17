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
    int l=0;
    int d=0;


    public FeedFragmentAdapter(Context context, ArrayList<String> filename) {
        this.context = context;
        this.casedetail = filename;





    }




    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.customfeedrecyclerview, parent, false);


        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {

        holder.file.setText(casedetail.get(position));




        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.l==0)
                {
                    holder.like.setBackgroundResource(R.drawable.likered);
                    holder.l++;

                }
                else
                {
                    holder.like.setBackgroundResource(R.drawable.likewhite);
                    holder.l--;
                }

            }
        });

        holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.d==0)
                {
                    holder.dislike.setBackgroundResource(R.drawable.dislikered);
                    holder.d++;

                }
                else
                {
                    holder.dislike.setBackgroundResource(R.drawable.dislike);
                    holder.d--;
                }
            }
        });






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
        TextView like,dislike;
        EditText editText;
        int l,d;
        public viewholder(View itemView) {
            super(itemView);

            file=itemView.findViewById(R.id.casedetailfeed);
            like=itemView.findViewById(R.id.like);
            dislike=itemView.findViewById(R.id.dislike);
            l=0;
            d=0;


        }

    }

}


