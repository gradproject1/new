package com.example.user1.urnextapp;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by AndroidJSon.com on 6/18/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<entertainment_List_Information> MainImageUploadInfoList;

    public RecyclerViewAdapter(Context context, List<entertainment_List_Information> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        entertainment_List_Information UploadInfo = MainImageUploadInfoList.get(position);

        //Loading image from Glide library.
        Glide.with(context).load(UploadInfo.getImageURL()).into(holder.imageView);
        //Loading image from Glide library.
        holder.article.setText(UploadInfo.getImageArticle());
    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView article;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            article= (TextView) itemView.findViewById(R.id.article);

        }
    }
}