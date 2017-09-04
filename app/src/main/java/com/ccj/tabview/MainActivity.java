package com.ccj.tabview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    public void simpleFilter(View view){
        startActivity(new Intent(this,SimpleFilterActivity.class));
    }



    public void myFilterResult(View view){
        startActivity(new Intent(this,MyFilterResultActivity.class));

    }



    public void myFilterStyle(View view){
        startActivity(new Intent(this,MyFilterStyleActivity.class));

    }
}
