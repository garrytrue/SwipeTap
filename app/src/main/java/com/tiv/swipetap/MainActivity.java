package com.tiv.swipetap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tiv.swipetap.callback.OnDownViewClickListener;
import com.tiv.swipetap.callback.RecyclerItemTouchListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final OnDownViewClickListener itemsClickListener = new OnDownViewClickListener() {
        @Override
        public void onDownViewTap(int position) {
            Log.d(TAG, "onDownViewTap() called with: " + "position = [" + position + "]");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private List<String> generateData() {
        List<String> data = new ArrayList<>(50);
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            String item = "#Item " + random.nextInt();
            data.add(item);
        }
        return data;
    }

    private void initUI() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnItemTouchListener(new RecyclerItemTouchListener());
        List<String > data = generateData();
        Log.d(TAG, "initUI: " + data);
        recyclerView.setAdapter(CustomAdapter.makeAdapter(data, itemsClickListener));
    }

}
