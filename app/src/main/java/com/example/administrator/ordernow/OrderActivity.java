package com.example.administrator.ordernow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new TableAdapter(this));
    }
}
