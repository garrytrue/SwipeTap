package com.tiv.swipetap;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiv.swipetap.callback.OnItemViewClickListener;
import com.tiv.swipetap.callback.OnUpperViewGestureListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiv on 22.04.2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<ItemHolder> {
    private static final String TAG = CustomAdapter.class.getSimpleName();
    private List<String> data;
    private OnItemViewClickListener listener;
    private List<Integer> selectedItems = new ArrayList<>();
    private WeakReference<ItemHolder> itemHolderWeakReference;
    private int swipedPos = -15;
    private boolean isMultiSelect = false;

    private final OnUpperViewGestureListener onUpperViewGestureListener = new OnUpperViewGestureListener() {
        @Override
        public void OnLongClick(int position) {
            listener.onUpperViewLongTap(position);
            swipedPos = -15;
            Log.d(TAG, "OnLongClick() called with: " + "position = [" + position + "] isMultiSelect = " + isMultiSelect);
            if (isMultiSelect) {
                isMultiSelect = false;
                selectedItems.clear();
                notifyDataSetChanged();
            } else {
                isMultiSelect = true;
                selectedItems.add(position);
                notifyItemChanged(position);
            }
        }

        @Override
        public void onClick(int position) {
            if (isMultiSelect) {
                if (!selectedItems.contains(position)) {
                    selectedItems.add(position);
                } else {
                    selectedItems.remove((Integer) position);
                }
                notifyItemChanged(position);
            } else {listener.onUpperViewTap(position);}
        }

        @Override
        public void swipedAction(ItemHolder item) {
            if (itemHolderWeakReference == null || itemHolderWeakReference.get() == null) {
                itemHolderWeakReference = new WeakReference<>(item);
                return;
            }
            final ItemHolder previousOpenedPos = itemHolderWeakReference.get();
            if (previousOpenedPos != item) {
                previousOpenedPos.triggerSwipeAction(false);
                itemHolderWeakReference = new WeakReference<>(item);
            }
            swipedPos = item.getAdapterPosition();
        }

    };

    private CustomAdapter(List<String> data, OnItemViewClickListener listener) {
        this.data = data;
        this.listener = listener;

    }

    public static CustomAdapter makeAdapter(List<String> data, OnItemViewClickListener listener) {
        return new CustomAdapter(data, listener);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return ItemHolder.makeHolder(view, listener, onUpperViewGestureListener);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: SelectedItems " + selectedItems);
        holder.triggerSwipeAction(swipedPos == position);
        Log.d(TAG, "onBindViewHolder: pos = " + position + "Is SelectedItem contains pos " + String.valueOf(selectedItems.contains(position)));
        holder.makeSelected(selectedItems.contains(position));
        holder.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
