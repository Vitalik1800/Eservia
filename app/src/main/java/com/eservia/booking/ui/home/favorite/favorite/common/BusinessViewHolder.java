package com.eservia.booking.ui.home.favorite.favorite.common;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eservia.booking.R;
import com.eservia.simpleratingbar.SimpleRatingBar;

public class BusinessViewHolder extends RecyclerView.ViewHolder {

    public View view;
    public RelativeLayout rlCardHolder;
    public CardView cvContainer;
    public ImageView ivBusinessImage;
    public TextView tvBusinessName;
    public SimpleRatingBar rbRating;
    public TextView tvRating;
    public TextView tvAddress;
    public TextView tvDistance;
    public RelativeLayout rlBusinessInfoContainer;
    public RelativeLayout rlBook;
    public ImageView ivBook;
    public ImageView ivIconNext;
    public Button btnBooking;
    public Button btnFavoriteOn;
    public Button btnFavoriteOff;

    public BusinessViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        rlCardHolder = itemView.findViewById(R.id.rlCardHolder);
        cvContainer = itemView.findViewById(R.id.cvContainer);
        ivBusinessImage = itemView.findViewById(R.id.ivBusinessImage);
        tvBusinessName = itemView.findViewById(R.id.tvBusinessName);
        rbRating = itemView.findViewById(R.id.rbRating);
        tvRating = itemView.findViewById(R.id.tvRating);
        tvAddress = itemView.findViewById(R.id.tvAddress);
        tvDistance = itemView.findViewById(R.id.tvDistance);
        rlBusinessInfoContainer = itemView.findViewById(R.id.rlBusinessInfoContainer);
        rlBook = itemView.findViewById(R.id.rlBook);
        ivBook = itemView.findViewById(R.id.ivBook);
        ivIconNext = itemView.findViewById(R.id.ivIconNext);
        btnBooking = itemView.findViewById(R.id.btnBooking);
        btnFavoriteOn = itemView.findViewById(R.id.btnFavoriteOn);
        btnFavoriteOff = itemView.findViewById(R.id.btnFavoriteOff);
    }
}
