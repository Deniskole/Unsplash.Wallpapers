package com.example.unsplashwallpapers.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.unsplashwallpapers.data.model.Result;
import com.example.unsplashwallpapers.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoAdapder extends RecyclerView.Adapter<PhotoAdapder.PhotoViewHolder> {

    private List<Result> photos;
    private OnPhotoClickListener onPhotoClickListener;
    private OnReachEndListener onReachEndListener;

    public interface OnPhotoClickListener {
        void onDayClick(int position);
    }

    public interface OnReachEndListener {
        void onReachEnd();
    }

    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setPhotos(List<Result> photos) {
        if (this.photos == null) {
            this.photos = photos;
        } else {
            this.photos.addAll(photos);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        photos.clear();
        notifyDataSetChanged();
    }

    public List<Result> getPhotos() {
        return photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_photo, viewGroup, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder photoViewHolder, int i) {
        if (i > photos.size() - 2 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }
        Result photo = photos.get(i);
        Picasso.get().load(photo.getUrls().getSmall()).into(photoViewHolder.imageViewPhoto);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view_photo)
        ImageView imageViewPhoto;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPhotoClickListener != null) {
                        onPhotoClickListener.onDayClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}