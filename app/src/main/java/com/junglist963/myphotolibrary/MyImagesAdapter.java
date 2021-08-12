package com.junglist963.myphotolibrary;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyImagesAdapter extends RecyclerView.Adapter<MyImagesAdapter.MyImagesHolder> {

    private List<MyImages>myImages = new ArrayList<>();
    private onImageClickListener listener;

    public void setListener(onImageClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public MyImagesHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_card, parent, false);
        return new MyImagesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyImagesHolder holder, int position) {

        MyImages myImagesList = myImages.get(position);
        holder.textViewTitle.setText(myImagesList.getImage_title());
        holder.textViewDescription.setText(myImagesList.getImage_description());
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(myImagesList.getImage()
                , 0, myImagesList.getImage().length));
    }

    public MyImages getPosition(int position){
        return myImages.get(position);
    }
    public interface onImageClickListener{
        void onImageClicked (MyImages myImages);
    }

    @Override
    public int getItemCount() {
        return myImages.size();
    }

    public void setMyImages(List<MyImages> myImages) {
        this.myImages = myImages;
        notifyDataSetChanged();
    }
    public class MyImagesHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textViewTitle, textViewDescription;

        public MyImagesHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int  position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onImageClicked(myImages.get(position));
                    }
                }
            });
        }
    }
}
