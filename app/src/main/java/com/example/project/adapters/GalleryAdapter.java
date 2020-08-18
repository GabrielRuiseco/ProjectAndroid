package com.example.project.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response.Listener;
import com.example.project.R;
import com.example.project.classes.FileImage;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> implements View.OnClickListener {

    private ArrayList<FileImage> fileArray;
    private Context context;
    private View.OnClickListener listener;

    public GalleryAdapter(ArrayList<FileImage> arrayList, Context inc_context) {
        this.fileArray = arrayList;
        this.context = inc_context;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new GalleryAdapter.GalleryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        String url = "http://127.0.0.1:3000/api/downloadit/" + fileArray.get(position).getFileName();
        holder.imageView.setImageDrawable(LoadImageFromWebOperations(url));
        holder.position = position;
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return fileArray.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Button open, dissmis;
        int position;


        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_item);
            dissmis = itemView.findViewById(R.id.btn_diss);
            open = itemView.findViewById(R.id.btn_open);

            open.setOnClickListener(listener);
            imageView.setOnClickListener(listener);

            dissmis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeItem(position);
                }
            });

        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    public void removeItem(int pos) {
        fileArray.remove(pos);
        notifyItemRemoved(pos);
    }
}
