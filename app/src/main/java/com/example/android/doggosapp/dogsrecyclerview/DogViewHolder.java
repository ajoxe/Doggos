package com.example.android.doggosapp.dogsrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.android.doggosapp.R;
import com.example.android.doggosapp.model.Dog;
import com.squareup.picasso.Picasso;

/**
 * Created by amirahoxendine on 2/25/18.
 */

public class DogViewHolder extends RecyclerView.ViewHolder {
    ImageView dogImage;

    public DogViewHolder(View itemView) {
        super(itemView);
        dogImage = (ImageView) itemView.findViewById(R.id.dog_item_iv);
    }

    public void onBind(Dog dog, Context context) {
        Picasso.with(context)
                .load(dog.getMessage())
                .into(dogImage);
        Log.d("imageUrl", dog.getMessage());
    }
}
