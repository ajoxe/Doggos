package com.example.android.doggosapp.dogsrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.doggosapp.R;
import com.example.android.doggosapp.model.Dog;

import java.util.List;

/**
 * Created by amirahoxendine on 2/25/18.
 */

public class DogAdapter extends RecyclerView.Adapter<DogViewHolder>{
    List<Dog> dogList;
    Context context;
    View.OnClickListener dogClick;

    public DogAdapter(List<Dog> dogList, Context context, View.OnClickListener dogClick) {
        this.dogList = dogList;
        this.context = context;
        this.dogClick = dogClick;
    }

    @Override
    public DogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_item_view, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DogViewHolder holder, int position) {
        Dog dog = dogList.get(position);
        holder.onBind(dog, context);
        holder.itemView.setTag(dog.getMessage());
        holder.itemView.setOnClickListener(dogClick);
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }
}
