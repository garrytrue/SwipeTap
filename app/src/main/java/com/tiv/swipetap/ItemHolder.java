package com.tiv.swipetap;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.tiv.swipetap.callback.OnDownViewClickListener;
import com.tiv.swipetap.callback.OnUpperViewGestureListener;

/**
 * Created by tiv on 22.04.2016.
 */
public class ItemHolder extends RecyclerView.ViewHolder {
    private static final String TAG = ItemHolder.class.getSimpleName();
    private View swipedView, downView;
    private TextView swipedViewTextLabel;
    private SwipeHelper swipeHelper;

    private ItemHolder(View itemView, OnDownViewClickListener listener, OnUpperViewGestureListener onUpperViewGestureListener) {
        super(itemView);
        initView(itemView, listener, onUpperViewGestureListener);
    }

    public static ItemHolder makeHolder(View item, OnDownViewClickListener listener, OnUpperViewGestureListener onUpperViewGestureListener) {
        return new ItemHolder(item, listener, onUpperViewGestureListener);
    }

    private void initView(View itemView, final OnDownViewClickListener listener , OnUpperViewGestureListener onUpperViewGestureListener) {
        swipedView = itemView.findViewById(R.id.swiped_view);
        swipeHelper= SwipeHelper.makeSwipeHelper(swipedView, onUpperViewGestureListener);
        final GestureDetectorCompat gestureDetectorCompat = new GestureDetectorCompat(swipedView.getContext(), swipeHelper);
        swipedView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorCompat.onTouchEvent(event);
                swipeHelper.setTouchedPosition(getAdapterPosition());
                return true;
            }
        });
        downView = itemView.findViewById(R.id.down_view);
        downView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDownViewTap(getAdapterPosition());
            }
        });
        swipedViewTextLabel = (TextView) itemView.findViewById(R.id.text_view);
    }

    public void setText(String text) {
        swipedViewTextLabel.setText(text);
    }

    public void makeSelected(boolean isSelected) {
        if(isSelected){
            swipedView.setBackgroundColor(isSelected ? swipedView.getResources().getColor(R.color.colorAccent) :   swipedView.getResources().getColor(android.R.color.darker_gray));
        }else{

        }
    }
}
