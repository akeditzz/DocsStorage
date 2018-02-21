package com.example.akshaymanagooli.docsstorage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Akshay.Managooli on 11/8/2017.
 */

public class DocumentListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<File> list;
    Context context;

    public DocumentListAdapter(ArrayList<File> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {

            case 1:
                View v3 = inflater.inflate(R.layout.document_list_row, parent, false);
                viewHolder = new CommentViewHolder(v3);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {


            case 1:
                CommentViewHolder vh3 = (CommentViewHolder) holder;

                try {

                    String filenameArray[] = list.get(position).getName().split("\\.");
                    String extension = filenameArray[filenameArray.length - 1];

                    switch (extension.toLowerCase()){
                        case "pdf":
                            vh3.icon.setImageResource(R.drawable.download);
                            break;
                        case "doc":
                            vh3.icon.setImageResource(R.drawable.download_2);
                            break;
                        case "docx":
                            vh3.icon.setImageResource(R.drawable.download_3);
                            break;
                    }

                    if (extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase("docx")) {
                        vh3.filename.setText(list.get(position).getName());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView filename;
        ImageView icon;



        public CommentViewHolder(View itemView) {
            super(itemView);

            filename = itemView.findViewById(R.id.tv_filename);
            icon = itemView.findViewById(R.id.iv_icon);



        }


    }


}
