package com.tiv.swipetap;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiv.swipetap.callback.OnDownViewClickListener;
import com.tiv.swipetap.callback.OnUpperViewGestureListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiv on 22.04.2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<ItemHolder> {
    private List<String> data;
    private OnDownViewClickListener listener;
    private List<Integer> selectedItems = new ArrayList<>();
    private SwipeHelper swi;

    private final OnUpperViewGestureListener onUpperViewGestureListener = new OnUpperViewGestureListener() {
        @Override
        public void OnLongClick(int position) {

        }

        @Override
        public void onClick(int position, boolean isInSelectedMode) {
            if(isInSelectedMode) {
                selectedItems.add(position);
            }else{
                selectedItems.remove((Integer) position);
            }
            notifyItemChanged(position);
        }
    };

    private CustomAdapter(List<String> data, OnDownViewClickListener listener) {
        this.data = data;
        this.listener = listener;

    }

    public static CustomAdapter makeAdapter(List<String> data, OnDownViewClickListener listener) {
        return new CustomAdapter(data, listener);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return ItemHolder.makeHolder(view, listener, onUpperViewGestureListener);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
    holder.makeSelected(selectedItems.contains(position));
        holder.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
